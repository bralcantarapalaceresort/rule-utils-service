package com.palaceresorts.rule_utils_service.service;


import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.repository.AreaRepository;
import com.palaceresorts.rule_utils_service.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AreaService {

    private final AreaRepository areaRepository;
    private final ProcessRepository processRepository;

    public Optional<Area> findByProcessIdAndStatusActive(String processId) {
        return areaRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    public Optional<Area> findById(String processId) {
        return areaRepository.findById(processId);
    }

    public Area create(Area area) {
        Area areaBuilder = Area.builder()
                .processId(UUID.randomUUID().toString())
                .name(area.getName())
                .initialDate(area.getInitialDate())
                .endDate(area.getEndDate())
                .active(area.isActive())
                .objective(area.getObjective())
                .build();
        return areaRepository.save(areaBuilder);
    }

    public Area update(String processId, Area area) {
        if (!areaRepository.existsById(processId)) {
            return null;
        }
        area.setProcessId(processId);
        return areaRepository.save(area);
    }

    public void deleteById(String processId) {
        areaRepository.deleteById(processId);
    }

    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<Area> areaOptional = areaRepository.findById(processId);

        if (areaOptional.isPresent()) {
            Area area = areaOptional.get();
            area.setActive(isActive);
            areaRepository.save(area);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }

    public Area addProcessToArea(String areaId, String processId) {
        Optional<Area> areaOptional = areaRepository.findById(areaId);
        Optional<Process> processOptional = processRepository.findById(processId);

        if (areaOptional.isPresent() && processOptional.isPresent()) {
            Area area = areaOptional.get();
            Process process = processOptional.get();
            area.getProcesses().add(process);
            areaRepository.save(area);
            return area;
        }
        return null;
    }
}
