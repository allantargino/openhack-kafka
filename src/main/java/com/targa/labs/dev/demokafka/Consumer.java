package com.targa.labs.dev.demokafka;

import badgeevent.BadgeEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private final KafkaListenerEndpointRegistry registry;

    public Consumer(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
        LOGGER.info("{}", registry.getAllListenerContainers());
    }

    @KafkaListener(topics = {"newesttopic"})
    public void receive(ConsumerRecord<String, BadgeEvent> consumerRecord) {
        String key = consumerRecord.key();
        BadgeEvent value = consumerRecord.value();
        LOGGER.info("consuming {}, {}", key, value);
    }

    @KafkaListener(topics = "newesttopic")
    public void receiveIt(ConsumerRecord<String, BadgeEvent> consumerRecord) {
        String key = consumerRecord.key();
        BadgeEvent value = consumerRecord.value();
        LOGGER.info("consuming {}, {}", key, value);
    }
}
