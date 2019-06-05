package com.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DemoKafkaApplication {

    public static void main(String[] args) {
        StreamProvider provider = new StreamProvider("40.114.45.150:31090");

        List<Streamer<?>> streamers = Arrays.asList(
            new Streamer<>(provider, "post", "post-output", Post.class),
            new Streamer<>(provider, "vote", "vote-output", Vote.class),
            new Streamer<>(provider, "comment", "comment-output", Comment.class)
            );
 
        start(streamers);
    }

    public static void start(List<Streamer<?>> streamers){
        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("streamer-shutdown-hook") {
            @Override
            public void run() {
                streamers.forEach(s -> s.close());
                latch.countDown();
            }
        });

        try {
            streamers.forEach(s -> s.start());
            latch.await();
        } catch (final Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
