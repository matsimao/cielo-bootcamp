package com.cielo.bootcamp.prospect.application.controllers;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prospects")
public class ProspectController {

    @GetMapping
    public String getAll() {
        return "Route getAll";
    }

    @GetMapping("/{prospectId}")
    public String get(@PathVariable Long prospectId) {
        return "Route get";
    }

    @PostMapping
    public String create(@RequestBody ProspectDTO prospectDTO) {
        return "Route create";
    }

    @PutMapping("/{prospectId}")
    public String update(@PathVariable Long prospectId) {
        return "Route update";
    }

    @DeleteMapping("/{prospectId}")
    public String delete(@PathVariable Long prospectId) {
        return "Route delete";
    }
}
