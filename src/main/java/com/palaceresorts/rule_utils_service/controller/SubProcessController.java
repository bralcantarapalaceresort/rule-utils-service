package com.palaceresorts.rule_utils_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.palaceresorts.rule_utils_service.entity.SubProcess;
import com.palaceresorts.rule_utils_service.model.SubProcessRequest;
import com.palaceresorts.rule_utils_service.service.SubProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rule/utils/subProcess")
public class SubProcessController {
    @Autowired
    private SubProcessService subProcessService;

    @GetMapping
    public ResponseEntity<List<SubProcess>> getAllSubProcess() {
        List<SubProcess> subProcesses = subProcessService.findAll();
        return ResponseEntity.ok(subProcesses);
    }


    @GetMapping("/{subProcessId}")
    public ResponseEntity<SubProcess> getSubProcesstById(@PathVariable String subProcessId) {
        Optional<SubProcess> subProcess = subProcessService.findById(subProcessId);
        return subProcess.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubProcess> createSubProcess(@RequestBody SubProcessRequest subProcessRequest) throws JsonProcessingException {
        SubProcess createdSubProcess = subProcessService.create(subProcessRequest);
        return ResponseEntity.status(201).body(createdSubProcess);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<SubProcess> updateSubProcess(@PathVariable String processId, @RequestBody SubProcess subProcess) {
        SubProcess updatedSubProcess = subProcessService.update(processId, subProcess);
        return updatedSubProcess != null
                ? ResponseEntity.ok(updatedSubProcess)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        subProcessService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = subProcessService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }
}
