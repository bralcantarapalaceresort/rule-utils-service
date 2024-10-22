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

/**
 * REST controller for managing SubProcess entities.
 */
@RestController
@RequestMapping("/api/rule/utils/subProcess")
public class SubProcessController {
    @Autowired
    private SubProcessService subProcessService;

    /**
     * Retrieves all subprocesses.
     *
     * @return a ResponseEntity containing a list of all subprocesses
     */
    @GetMapping
    public ResponseEntity<List<SubProcess>> getAllSubProcess() {
        List<SubProcess> subProcesses = subProcessService.findAll();
        return ResponseEntity.ok(subProcesses);
    }

    /**
     * Retrieves a subprocess by its ID.
     *
     * @param subProcessId the ID of the subprocess to retrieve
     * @return a ResponseEntity containing the found subprocess, or 404 if not found
     */
    @GetMapping("/{subProcessId}")
    public ResponseEntity<SubProcess> getSubProcesstById(@PathVariable String subProcessId) {
        Optional<SubProcess> subProcess = subProcessService.findById(subProcessId);
        return subProcess.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new subprocess.
     *
     * @param subProcessRequest the subprocess request data
     * @return a ResponseEntity containing the created subprocess
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    @PostMapping
    public ResponseEntity<SubProcess> createSubProcess(@RequestBody SubProcessRequest subProcessRequest) throws JsonProcessingException {
        SubProcess createdSubProcess = subProcessService.create(subProcessRequest);
        return ResponseEntity.status(201).body(createdSubProcess);
    }

    /**
     * Updates an existing subprocess.
     *
     * @param processId the ID of the subprocess to update
     * @param subProcess the updated subprocess data
     * @return a ResponseEntity containing the updated subprocess, or 404 if not found
     */
    @PutMapping("/{processId}")
    public ResponseEntity<SubProcess> updateSubProcess(@PathVariable String processId, @RequestBody SubProcess subProcess) {
        SubProcess updatedSubProcess = subProcessService.update(processId, subProcess);
        return updatedSubProcess != null
                ? ResponseEntity.ok(updatedSubProcess)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a subprocess by its ID.
     *
     * @param processId the ID of the subprocess to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        subProcessService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the isActive flag for a subprocess.
     *
     * @param processId the ID of the subprocess to update
     * @param isActive the new value for the isActive flag
     * @return a ResponseEntity with no content
     */
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
