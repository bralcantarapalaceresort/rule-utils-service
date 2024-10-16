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

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private SubProcessRepository subProcessRepository;

    public Optional<Process> findByProcessIdAndStatusActive(String processId) {
        return processRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public Optional<Process> findById(String processId) {
        return processRepository.findById(processId);
    }

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

    public Process update(String processId, Process process) {
        if (!processRepository.existsById(processId)) {
            return null;
        }
        process.setProcessId(processId);
        return processRepository.save(process);
    }

    public void deleteById(String processId) {
        processRepository.deleteById(processId);
    }

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

}
