package com.palaceresorts.rule_utils_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palaceresorts.rule_utils_service.entity.SubProcess;
import com.palaceresorts.rule_utils_service.model.SubProcessRequest;
import com.palaceresorts.rule_utils_service.repository.SubProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing SubProcess entities.
 */
@Service
public class SubProcessService {

    @Autowired
    private SubProcessRepository subProcessRepository;

    /**
     * Finds an active SubProcess entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found SubProcess entity, or empty if no active SubProcess entity with the given processId exists
     */
    public Optional<SubProcess> findByProcessIdAndStatusActive(String processId) {
        return subProcessRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    /**
     * Retrieves all SubProcess entities.
     *
     * @return a list of all SubProcess entities
     */
    public List<SubProcess> findAll() {
        return subProcessRepository.findAll();
    }

    /**
     * Finds a SubProcess entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found SubProcess entity, or empty if no SubProcess entity with the given processId exists
     */
    public Optional<SubProcess> findById(String processId) {
        return subProcessRepository.findById(processId);
    }

    /**
     * Creates a new SubProcess entity.
     *
     * @param subProcessRequest the SubProcessRequest containing the data to create the SubProcess entity
     * @return the created SubProcess entity
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    public SubProcess create(SubProcessRequest subProcessRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        SubProcess subProcessBuilder = SubProcess.builder()
                .processId(UUID.randomUUID().toString())
                .name(subProcessRequest.getName())
                .initialDate(subProcessRequest.getInitialDate())
                .endDate(subProcessRequest.getEndDate())
                .active(subProcessRequest.isActive())
                .objective(subProcessRequest.getObjective())
                .rule(objectMapper.writeValueAsString(subProcessRequest.getRule()))
                .build();
        return subProcessRepository.save(subProcessBuilder);
    }

    /**
     * Updates an existing SubProcess entity.
     *
     * @param processId the process ID of the SubProcess entity to update
     * @param subProcess the updated SubProcess entity data
     * @return the updated SubProcess entity, or null if no SubProcess entity with the given processId exists
     */
    public SubProcess update(String processId, SubProcess subProcess) {
        if (!subProcessRepository.existsById(processId)) {
            return null;
        }
        subProcess.setProcessId(processId);
        return subProcessRepository.save(subProcess);
    }

    /**
     * Deletes a SubProcess entity by its processId.
     *
     * @param processId the process ID of the SubProcess entity to delete
     */
    public void deleteById(String processId) {
        subProcessRepository.deleteById(processId);
    }

    /**
     * Updates the active status of a SubProcess entity.
     *
     * @param processId the process ID of the SubProcess entity to update
     * @param isActive the new active status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<SubProcess> subProcessOptional = subProcessRepository.findById(processId);

        if (subProcessOptional.isPresent()) {
            SubProcess subProcess = subProcessOptional.get();
            subProcess.setActive(isActive);
            subProcessRepository.save(subProcess);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }
}
