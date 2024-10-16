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

@Service
public class SubProcessService {

    @Autowired
    private SubProcessRepository subProcessRepository;

    public Optional<SubProcess> findByProcessIdAndStatusActive(String processId) {
        return subProcessRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    public List<SubProcess> findAll() {
        return subProcessRepository.findAll();
    }

    public Optional<SubProcess> findById(String processId) {
        return subProcessRepository.findById(processId);
    }

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

    public SubProcess update(String processId, SubProcess subProcess) {
        if (!subProcessRepository.existsById(processId)) {
            return null;
        }
        subProcess.setProcessId(processId);
        return subProcessRepository.save(subProcess);
    }

    public void deleteById(String processId) {
        subProcessRepository.deleteById(processId);
    }

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
