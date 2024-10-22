package com.palaceresorts.rule_utils_service.service;

import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.repository.AreaRepository;
import com.palaceresorts.rule_utils_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing Department entities.
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AreaRepository areaRepository;

    /**
     * Finds an active Department entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Department entity, or empty if no active Department entity with the given processId exists
     */
    public Optional<Department> findByProcessIdAndStatusActive(String processId) {
        return departmentRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    /**
     * Retrieves all Department entities.
     *
     * @return a list of all Department entities
     */
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    /**
     * Retrieves all active Department entities.
     *
     * @return a list of all active Department entities
     */
    public Optional<Department> findById(String processId) {
        return departmentRepository.findById(processId);
    }

    /**
     * Creates a new Department entity.
     *
     * @param department the Department entity to create
     * @return the created Department entity
     */
    public Department create(Department department) {
        Department departmentBuilder = Department.builder()
                .processId(UUID.randomUUID().toString())
                .name(department.getName())
                .initialDate(department.getInitialDate())
                .endDate(department.getEndDate())
                .active(department.isActive())
                .objective(department.getObjective())
                .build();
        return departmentRepository.save(departmentBuilder);
    }

    /**
     * Updates an existing Department entity.
     *
     * @param processId the process ID of the Department entity to update
     * @param department the updated Department entity data
     * @return the updated Department entity, or null if no Department entity with the given processId exists
     */
    public Department update(String processId, Department department) {
        if (!departmentRepository.existsById(processId)) {
            return null;
        }
        department.setProcessId(processId);
        return departmentRepository.save(department);
    }

    /**
     * Deletes a Department entity by its processId.
     *
     * @param processId the process ID of the Department entity to delete
     */
    public void deleteById(String processId) {
        departmentRepository.deleteById(processId);
    }

    /**
     * Updates the active status of a Department entity.
     *
     * @param processId the process ID of the Department entity to update
     * @param isActive the new active status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<Department> departmentOptional = departmentRepository.findById(processId);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setActive(isActive);
            departmentRepository.save(department);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }

    /**
     * Adds an Area entity to a Department entity.
     *
     * @param departmentId the ID of the Department entity
     * @param areaId the ID of the Area entity to add
     * @return the updated Department entity, or null if either the Department or Area entity does not exist
     */
    public Department addAreaToDepartment(String departmentId, String areaId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        Optional<Area> areaOptional = areaRepository.findById(areaId);

        if (departmentOptional.isPresent() && areaOptional.isPresent()) {
            Department department = departmentOptional.get();
            Area area = areaOptional.get();
            department.getAreas().add(area);
            departmentRepository.save(department);
            return department;
        }
        return null;
    }
}
