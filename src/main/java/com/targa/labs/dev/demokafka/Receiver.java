package com.targa.labs.dev.demokafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private Sender sender;

    @Autowired
    private RestTemplate restTemplate;

    @KafkaListener(topics = "post")
    public void receiveFromPost(String payload) throws IOException {
        JsonNode jsonNode = this.sendToFunction(getJsonNode(payload));
        LOGGER.info(jsonNode.toString());
        this.sender.sendTo(jsonNode.toString(), "eventz");
    }


    @KafkaListener(topics = "vote")
    public void receiveFromVote(String payload) {
        //LOGGER.info(payload);
        this.sender.sendTo(payload, "eventz");
    }

    @KafkaListener(topics = "comment")
    public void receiveFromComment(String payload) throws IOException {
        JsonNode jsonNode = this.sendToFunction(getJsonNode(payload));
        LOGGER.info(jsonNode.toString());
        this.sender.sendTo(jsonNode.toString(), "eventz");
    }

    private JsonNode getJsonNode(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(payload);
    }

    private JsonNode sendToFunction(JsonNode jsonNode) {

        HttpEntity<JsonNode> request = new HttpEntity<>(jsonNode);

        String url = "https://ohkl-kp0.azurewebsites.net/api/KeyPhraseFunc";
        ResponseEntity<JsonNode> response =
                restTemplate.exchange(url,
                        HttpMethod.POST,
                        request,
                        JsonNode.class);
        return response.getBody();
    }
}
