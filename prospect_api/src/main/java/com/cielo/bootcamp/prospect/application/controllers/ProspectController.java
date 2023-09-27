package com.cielo.bootcamp.prospect.application.controllers;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.services.ProspectService;
import com.cielo.bootcamp.prospect.domain.Prospect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prospects")
public class ProspectController {

    @Autowired
    private ProspectService service;

    @GetMapping
    public ResponseEntity<List<Prospect>> getAll() {
        List<Prospect> prospectList = this.service.findAll();

        return new ResponseEntity<>(prospectList, HttpStatus.OK);
    }

    @GetMapping("/{prospectId}")
    public ResponseEntity<Prospect> get(@PathVariable Long prospectId) {
        try {
            Prospect prospect = this.service.findProspectById(prospectId);
            return new ResponseEntity<>(prospect, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Prospect> create(@RequestBody ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = this.service.save(prospectDTO);

        return new ResponseEntity<>(prospect, HttpStatus.CREATED);
    }

    @PutMapping("/{prospectId}")
    public ResponseEntity<Prospect> update(@PathVariable Long prospectId, @RequestBody ProspectDTO prospectDTO) throws Exception {
        Prospect prospect = this.service.update(prospectId, prospectDTO);

        return new ResponseEntity<>(prospect, HttpStatus.OK);
    }

    @DeleteMapping("/{prospectId}")
    public ResponseEntity<Void> delete(@PathVariable Long prospectId) {
        try {
            this.service.delete(prospectId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
