package com.palaceresorts.rule_utils_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.model.ProcessRequest;
import com.palaceresorts.rule_utils_service.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rule/utils/process")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @GetMapping
    public ResponseEntity<List<Process>> getAllAreas() {
        List<Process> areas = processService.findAll();
        return ResponseEntity.ok(areas);
    }


    @GetMapping("/{processId}")
    public ResponseEntity<Process> getProcesstById(@PathVariable String processId) {
        Optional<Process> process = processService.findById(processId);
        return process.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Process> createProcess(@RequestBody ProcessRequest process) throws JsonProcessingException {
        Process createdProcess = processService.create(process);
        return ResponseEntity.status(201).body(createdProcess);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<Process> updateProcess(@PathVariable String processId, @RequestBody Process process) {
        Process updatedProcess = processService.update(processId, process);
        return updatedProcess != null
                ? ResponseEntity.ok(updatedProcess)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        processService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = processService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    @PutMapping("/{processId}/add-subprocess/{subProcessId}")
    public ResponseEntity<Process> addSubProcessToProcess(@PathVariable String processId,
                                                 @PathVariable String subProcessId) {
        Process process = processService.addSubProcessToProcess(processId,subProcessId);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }
}
