package myapps;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.apache.kafka.streams.state.SessionStore;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;

public class Pipe {

    public static void main(String[] args) throws Exception, IOException {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ConnectionHelper.getBrokerConnection());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        Gson g = new Gson();

        builder.stream("post").flatMapValues(value -> {
            System.out.println("Got event");
            return Arrays.asList(g.fromJson(value.toString(), Post.class).getOwnerUserId());
        }).groupBy((key, value) -> {
            System.out.println("Grouped event");
            return value;
        }).windowedBy(SessionWindows.with(Duration.ofSeconds(15)))
        .count(Materialized.<String, Long, SessionStore<Bytes, byte[]>>as("PLAY_EVENTS_PER_SESSION")
                .withKeySerde(Serdes.String())
                .withValueSerde(Serdes.Long()))
        .toStream()
        .map((key, value) -> {
            System.out.println(key + " " +value);
            return new KeyValue<>(key.key() + "@" + key.window().start() + "->" + key.window().end(), value);
        });

        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
