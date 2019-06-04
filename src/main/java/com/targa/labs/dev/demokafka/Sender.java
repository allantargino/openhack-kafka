package com.targa.labs.dev.demokafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import badgeevent.BadgeEvent;

@RestController
public class Sender {

	@Autowired
	private KafkaTemplate<String, String> producer;

	@Value("${kafka.topic.name}")
	private String topicName;

	public void sendMessage(String msg) {
		producer.send(topicName, msg);
	}

	@GetMapping(value = "/event/{eventId}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String getTestData(@PathVariable String eventId) {
		// BadgeEvent event = new BadgeEvent();
		
		// event.setId(eventId);
		// event.setName("sampleName");
		// event.setDisplayName("sampleName");
		// event.setDownVotes(42);
		// event.setUserId("userId");
		// event.setUpVotes(13);
		// event.setReputation("good");
		// event.setProcessedDate("06/04/2019");

		// sendMessage(event);

		sendMessage(eventId);

		return "Ok";
	}

}