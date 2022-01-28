package com.demo.creditservices.controller;

import com.demo.creditservices.model.UpdateRequest;
import com.demo.creditservices.model.UpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Slf4j
@Validated
@RestController
@RequestMapping("/")
@SuppressWarnings("unused")
public class CreditServiceController {

    @Autowired
    private Sinks.Many<String> many;

    @Operation(summary = "Events testing", description = "Testing EventHubs.")
    @ApiResponse(responseCode = "200", description = "events")
    @PostMapping(value = "/creditlimit")
    public ResponseEntity<UpdateResponse> creditlimit(
            @RequestBody @NotNull(message = "updateRequest can not be empty")
            @Valid UpdateRequest updateRequest){
        many.emitNext(updateRequest.toString(), Sinks.EmitFailureHandler.FAIL_FAST);
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setUpdateRequest(updateRequest);
        updateResponse.setChangeDate(OffsetDateTime.now());

        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}