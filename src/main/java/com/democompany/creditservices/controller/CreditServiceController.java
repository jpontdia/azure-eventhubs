package com.democompany.creditservices.controller;

import com.democompany.creditservices.model.UpdateRequest;
import com.democompany.creditservices.model.UpdateResponse;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/")
@SuppressWarnings("unused")
public class CreditServiceController {

    @Value("${spring.cloud.azure.eventhub.connection-string}")
    private String connectionString;

    @Operation(summary = "Events testing", description = "Testing EventHubs.")
    @ApiResponse(responseCode = "200", description = "events")
    @PostMapping(value = "/events")
    public ResponseEntity<UpdateResponse> updateLimit(
            @RequestBody @NotNull(message = "updateRequest can not be empty")
            @Valid UpdateRequest updateRequest){
        String eventHubName = "test";
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString)
                .buildProducerClient();

        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            if (!eventDataBatch.tryAdd(eventData)) {
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }

        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }

        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setUpdateRequest(updateRequest);
        updateResponse.setChangeDate(OffsetDateTime.now());

        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}
