package com.targa.labs.dev.demokafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send("helloworld", payload);
    }

    public void sendTo(String payload, String topic) {
        LOGGER.info("sending payload='{}' to '{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
