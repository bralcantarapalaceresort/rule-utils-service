package com.palaceresorts.rule_utils_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.model.ProcessRequest;
import com.palaceresorts.rule_utils_service.service.ProcessService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Process entities.
 */
@RestController
@RequestMapping("/api/rule/utils/process")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    /**
     * Retrieves all processes.
     *
     * @return a ResponseEntity containing a list of all processes
     */
    @GetMapping
    public ResponseEntity<List<Process>> getAllAreas() {
        List<Process> areas = processService.findAll();
        return ResponseEntity.ok(areas);
    }

    /**
     * Retrieves a process by its process ID.
     *
     * @param processId the process ID of the process to retrieve
     * @return a ResponseEntity containing the found process, or 404 if not found
     */
    @GetMapping("/{processId}")
    public ResponseEntity<Process> getProcesstById(@PathVariable String processId) {
        Optional<Process> process = processService.findById(processId);
        return process.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Process> getProcessByName(@PathVariable String name) {
        Optional<Process> optionalProcess = processService.findByName(name);
        return optionalProcess
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new process.
     *
     * @param process the process to create
     * @return a ResponseEntity containing the created process
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    @PostMapping
    public ResponseEntity<Process> createProcess(@RequestBody ProcessRequest process) throws JsonProcessingException {
        Process createdProcess = processService.create(process);
        return ResponseEntity.status(201).body(createdProcess);
    }

    /**
     * Updates an existing process.
     *
     * @param processId the process ID of the process to update
     * @param process the updated process data
     * @return a ResponseEntity containing the updated process, or 404 if not found
     */
    @PutMapping("/{processId}")
    public ResponseEntity<Process> updateProcess(@PathVariable String processId, @RequestBody Process process) {
        Process updatedProcess = processService.update(processId, process);
        return updatedProcess != null
                ? ResponseEntity.ok(updatedProcess)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a process by its process ID.
     *
     * @param processId the process ID of the process to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        processService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the isActive flag for a process.
     *
     * @param processId the process ID of the process to update
     * @param isActive the new value for the isActive flag
     * @return a ResponseEntity with no content
     */
    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = processService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    /**
     * Adds a subprocess to a process.
     *
     * @param processId the process ID of the process to update
     * @param subProcessId the process ID of the subprocess to add
     * @return a ResponseEntity containing the updated process, or 404 if not found
     */
    @PutMapping("/{processId}/add-subprocess/{subProcessId}")
    public ResponseEntity<Process> addSubProcessToProcess(@PathVariable String processId,
                                                 @PathVariable String subProcessId) {
        Process process = processService.addSubProcessToProcess(processId,subProcessId);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }
}
