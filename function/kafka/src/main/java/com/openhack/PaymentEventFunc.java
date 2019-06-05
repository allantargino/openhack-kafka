package com.openhack;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Azure Functions with Event Hub trigger.
 */
public class PaymentEventFunc {
    /**
     * This function will be invoked when an event is received from Event Hub.
     */
    @FunctionName("PaymentEventFunc")
    public void run(
        @EventHubTrigger(name = "message", eventHubName = "payment", connection = "EventHubConStr", consumerGroup = "$Default", cardinality = Cardinality.MANY) List<PaymentModel> message,
        @EventHubOutput(name = "outputMsg", eventHubName = "ticket_request_output", connection = "EventHubConStr") OutputBinding<List<PaymentModel>> outputMsg,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Event Hub trigger function executed.");
        context.getLogger().info("Length:" + message.size());
        message.forEach(singleMessage -> {
            singleMessage.paymentTime = new Date();
            singleMessage.paymentValid = "1";
            context.getLogger().info(singleMessage.customerId);
        });

        outputMsg.setValue(message);
    }
}
