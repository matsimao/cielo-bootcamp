package com.cielo.bootcamp.prospect.application.controllers;

import com.cielo.bootcamp.prospect.application.services.ProspectQueueService;
import com.cielo.bootcamp.prospect.domain.Prospect;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prospects/queues")
public class ProspectQueuesController {

    @Autowired
    private ProspectQueueService prospectQueueService;

    @GetMapping
    public ResponseEntity<List<Prospect>> getAllQueues() {
        List<Prospect> listQueue = this.prospectQueueService.getAll();
        return new ResponseEntity<>(listQueue, HttpStatus.OK);
    }

    @GetMapping("/retrieve")
    @Operation(summary = "Retrieve the first queue prospect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the first queue prospect"),
            @ApiResponse(responseCode = "204", description = "Queues prospect is empty")
    })
    public ResponseEntity<Prospect> retrieveQueue() {
        if (this.prospectQueueService.length() <= 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(this.prospectQueueService.retrieve(), HttpStatus.OK);
    }
}