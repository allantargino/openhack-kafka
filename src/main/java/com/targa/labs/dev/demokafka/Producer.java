package com.targa.labs.dev.demokafka;

import badgeevent.BadgeEvent;
import io.codearte.jfairy.Fairy;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class Producer implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private final KafkaTemplate<String, BadgeEvent> kafka;
    private final Fairy fairy = Fairy.create(Locale.GERMANY);

    private String topic = "newesttopic";
    private Integer numRecords = 5;

    private AtomicInteger counter = new AtomicInteger(0);

    public Producer(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaTemplate<String, BadgeEvent> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.rangeClosed(1, numRecords).boxed()
                .map(i -> fairy.person())
                .forEach(f -> {
                    BadgeEvent badgeEvent = BadgeEvent.newBuilder()
                            .setName("Teacher")
                            .setId(String.valueOf(counter.incrementAndGet()))
                            .setUserId("164")
                            .setDisplayName("dragonmantank")
                            .setReputation("7653")
                            .setUpVotes(53)
                            .setDownVotes(9)
                            .setProcessedDate("2019-06-04T12:42:25.2648973Z")
                            .build();

                    LOGGER.info("producing {}", badgeEvent);
                    ProducerRecord<String, BadgeEvent> record = new ProducerRecord<>(topic, null, badgeEvent);
                    kafka.send(record);
                });
    }
}
