package com.targa.labs.dev.demokafka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "/api/sender")
public class SenderController {

    private final Sender sender;
    private final AtomicInteger counter;

    public SenderController(Sender sender) {
        this.sender = sender;
        this.counter = new AtomicInteger(0);
    }

    @GetMapping
    public String send() {
        String msg = "Message " + counter.getAndIncrement() + " is here";
        this.sender.send(msg);
        return msg;
    }
}
