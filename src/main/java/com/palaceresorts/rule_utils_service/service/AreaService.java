package com.palaceresorts.rule_utils_service.service;


import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.repository.AreaRepository;
import com.palaceresorts.rule_utils_service.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing Area entities.
 */
@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ProcessRepository processRepository;

    /**
     * Finds an active Area entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Area entity, or empty if no active Area entity with the given processId exists
     */
    public Optional<Area> findByProcessIdAndStatusActive(String processId) {
        return areaRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    /**
     * Retrieves all Area entities.
     *
     * @return a list of all Area entities
     */
    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    /**
     * Finds an Area entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Area entity, or empty if no Area entity with the given processId exists
     */
    public Optional<Area> findById(String processId) {
        return areaRepository.findById(processId);
    }

    /**
     * Creates a new Area entity.
     *
     * @param area the Area entity to create
     * @return the created Area entity
     */
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

    /**
     * Updates an existing Area entity.
     *
     * @param processId the process ID of the Area entity to update
     * @param area the updated Area entity data
     * @return the updated Area entity, or null if no Area entity with the given processId exists
     */
    public Area update(String processId, Area area) {
        if (!areaRepository.existsById(processId)) {
            return null;
        }
        area.setProcessId(processId);
        return areaRepository.save(area);
    }

    /**
     * Deletes an Area entity by its processId.
     *
     * @param processId the process ID of the Area entity to delete
     */
    public void deleteById(String processId) {
        areaRepository.deleteById(processId);
    }

    /**
     * Updates the active status of an Area entity.
     *
     * @param processId the process ID of the Area entity to update
     * @param isActive the new active status
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Adds a Process entity to an Area entity.
     *
     * @param areaId the process ID of the Area entity
     * @param processId the process ID of the Process entity to add
     * @return the updated Area entity, or null if no Area or Process entity with the given IDs exists
     */
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
