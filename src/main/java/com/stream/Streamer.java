package com.stream;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public class Streamer<T> {
    final StreamProvider provider;
    final String from;
    final String to;
    final Class<T> entityClass;

    KafkaStreams streams;

    public Streamer(StreamProvider provider, String from, String to, Class<T> entityClass) {
        this.provider = provider;
        this.from = from;
        this.to = to;
        this.entityClass = entityClass;
        this.streams = null;
    }

    public void start(){
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(this.from);

        source
        .flatMap(
            (key, value) -> {
                List<KeyValue<String, String>> result = new LinkedList<>();
                
                // Verify message schema
                Gson gson = new Gson();
                T entity = gson.fromJson(value, entityClass);
                
                // Do something
                String output  = value.toUpperCase();
                
                // return result
                result.add(KeyValue.pair(key, output));
                return result;
            })
        .to(this.to);

        streams = new KafkaStreams(builder.build(), this.provider.getProperties());

        streams.start();
    }

    public void close(){
        streams.close();
    }
}