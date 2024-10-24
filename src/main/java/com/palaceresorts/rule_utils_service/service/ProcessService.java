package com.palaceresorts.rule_utils_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.entity.SubProcess;
import com.palaceresorts.rule_utils_service.model.ProcessRequest;
import com.palaceresorts.rule_utils_service.repository.ProcessRepository;
import com.palaceresorts.rule_utils_service.repository.SubProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing Process entities.
 */
@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private SubProcessRepository subProcessRepository;

    /**
     * Finds an active Process entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Process entity, or empty if no active Process entity with the given processId exists
     */
    public Optional<Process> findByProcessIdAndStatusActive(String processId) {
        return processRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    /**
     * Retrieves all Process entities.
     *
     * @return a list of all Process entities
     */
    public List<Process> findAll() {
        return processRepository.findAll();
    }

    /**
     * Finds a Process entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Process entity, or empty if no Process entity with the given processId exists
     */
    public Optional<Process> findById(String processId) {
        return processRepository.findById(processId);
    }

    /**
     *
     * @param name the name to search for
     * @return a list of Process entities with the given name
     */
    public Optional<Process> findByName(String name) {
        return processRepository.findByName(name);
    }

    /**
     * Creates a new Process entity.
     *
     * @param process the ProcessRequest containing the data to create the Process entity
     * @return the created Process entity
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    public Process create(ProcessRequest process) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Process processBuilder = Process.builder()
                .processId(UUID.randomUUID().toString())
                .name(process.getName())
                .initialDate(process.getInitialDate())
                .endDate(process.getEndDate())
                .active(process.isActive())
                .objective(process.getObjective())
                .model(process.getModel())
                .entity(objectMapper.writeValueAsString(process.getEntity()))
                .build();
        return processRepository.save(processBuilder);
    }

    /**
     * Updates an existing Process entity.
     *
     * @param processId the process ID of the Process entity to update
     * @param process the updated Process entity data
     * @return the updated Process entity, or null if no Process entity with the given processId exists
     */
    public Process update(String processId, Process process) {
        if (!processRepository.existsById(processId)) {
            return null;
        }
        process.setProcessId(processId);
        return processRepository.save(process);
    }

    /**
     * Deletes a Process entity by its processId.
     *
     * @param processId the process ID to delete
     */
    public void deleteById(String processId) {
        processRepository.deleteById(processId);
    }

    /**
     * Updates the active status of a Process entity.
     *
     * @param processId the process ID of the Process entity to update
     * @param isActive the new active status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<Process> processOptional = processRepository.findById(processId);

        if (processOptional.isPresent()) {
            Process process = processOptional.get();
            process.setActive(isActive);
            processRepository.save(process);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }

    /**
     * Adds a SubProcess entity to a Process entity.
     *
     * @param processId the ID of the Process entity
     * @param subProcessId the ID of the SubProcess entity to add
     * @return the updated Process entity, or null if either the Process or SubProcess entity does not exist
     */
    public Process addSubProcessToProcess(String processId, String subProcessId) {
        Optional<Process> processOptional = processRepository.findById(processId);
        Optional<SubProcess> subProcessOptional = subProcessRepository.findById(subProcessId);

        if (subProcessOptional.isPresent() && processOptional.isPresent()) {
            Process process = processOptional.get();
            SubProcess subProcess = subProcessOptional.get();
            process.getSubProcesses().add(subProcess);
            processRepository.save(process);
            return process;
        }
        return null;
    }

    /**
     * Retrieves all SubProcess entities for a given Process entity.
     *
     * @param processName the name of the Process entity
     * @return a list of SubProcess entities for the given Process entity
     */
    public List<SubProcess> findSubProcessesByProcessName(String processName) {
        return subProcessRepository.findSubProcessesByProcessName(processName);
    }

}
