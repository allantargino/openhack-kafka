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
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.io.IOException;
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

            String val = value.toString();

            // validate is json
            Post p = g.fromJson(val, Post.class);

            // post to function
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost("https://ohkl-kp0.azurewebsites.net/api/KeyPhraseFunc");
                StringEntity entity = new StringEntity(val);
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                try (CloseableHttpResponse res = httpclient.execute(httpPost)) {
                    if (res.getStatusLine().getStatusCode() > 299) {
                        throw new Exception("error response from function");
                    }

                    HttpEntity body = res.getEntity();
                    String jsonString = EntityUtils.toString(body);

                    System.out.println("Enriched: " + jsonString);

                    return Arrays.asList(jsonString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Arrays.asList("");
        }).to("events2");
        
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
