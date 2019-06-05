package com.openhack;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import java.util.*;

/**
 * Azure Functions with Event Hub trigger.
 */
public class TicketRequestEventFunc {
    /**
     * This function will be invoked when an event is received from Event Hub.
     */
    @FunctionName("TicketRequestEventFunc")
    public void run(
        @EventHubTrigger(name = "message", eventHubName = "ticket_request", connection = "EventHubConStr", consumerGroup = "$Default", cardinality = Cardinality.MANY) List<TicketModel> message,
        @EventHubOutput(name = "outputMsg", eventHubName = "ticket_request_output", connection = "EventHubConStr") OutputBinding<List<TicketModel>> outputMsg,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger function executed.");
        context.getLogger().info("Length:" + message.size());
        message.forEach(singleMessage -> {
            
            singleMessage.ticketRequestDateTime = new Date();
            singleMessage.ticketAvailable = "1";
            context.getLogger().info(singleMessage.eventId);           
            
        });

        outputMsg.setValue(message);
    }
}
