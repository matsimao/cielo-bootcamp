package com.cielo.bootcamp.prospect.application.controllers;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.services.ProspectService;
import com.cielo.bootcamp.prospect.application.services.ProspectQueueService;
import com.cielo.bootcamp.prospect.domain.Prospect;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prospects")
public class ProspectController {

    @Autowired
    private ProspectService prospectService;

    @Autowired
    private ProspectQueueService prospectQueueService;

    @GetMapping
    @Operation(summary = "Get the list of prospects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all prospects")
    })
    public ResponseEntity<List<Prospect>> getAll() {
        List<Prospect> prospectList = this.prospectService.findAll();

        return new ResponseEntity<>(prospectList, HttpStatus.OK);
    }

    @GetMapping("/{prospectId}")
    @Operation(summary = "Get the a specific prospect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific prospect"),
            @ApiResponse(responseCode = "404", description = "Prospect not found")
    })
    public ResponseEntity<Prospect> get(@PathVariable Long prospectId) {
        try {
            Prospect prospect = this.prospectService.findProspectById(prospectId);

            return new ResponseEntity<>(prospect, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new prospect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prospect registration successfully completed")
    })
    public ResponseEntity<Prospect> create(@RequestBody ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = this.prospectService.save(prospectDTO);

        this.prospectQueueService.add(prospect);

        return new ResponseEntity<>(prospect, HttpStatus.CREATED);
    }

    @PutMapping("/{prospectId}")
    @Operation(summary = "Update the a specific prospect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specific prospect successfully updated"),
            @ApiResponse(responseCode = "404", description = "Prospect not found")
    })
    public ResponseEntity<Prospect> update(@PathVariable Long prospectId, @RequestBody ProspectDTO prospectDTO) throws Exception {
        try {
            Prospect prospect = this.prospectService.update(prospectId, prospectDTO);

            this.prospectQueueService.removeQueue(prospect.getId());
            this.prospectQueueService.add(prospect);

            return new ResponseEntity<>(prospect, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{prospectId}")
    @Operation(summary = "Delete the a specific prospect")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Specific prospect successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Prospect not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long prospectId) {
        try {
            this.prospectService.delete(prospectId);

            this.prospectQueueService.removeQueue(prospectId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
