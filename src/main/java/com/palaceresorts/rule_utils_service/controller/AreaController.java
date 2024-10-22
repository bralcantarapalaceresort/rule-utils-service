package com.palaceresorts.rule_utils_service.controller;

import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.service.AreaService;
import com.palaceresorts.rule_utils_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Area entities.
 */
@RestController
@RequestMapping("/api/rule/utils/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * Retrieves all areas.
     *
     * @return a ResponseEntity containing a list of all areas
     */
    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaService.findAll();
        return ResponseEntity.ok(areas);
    }

    /**
     * Retrieves an area by its process ID.
     *
     * @param processId the process ID of the area to retrieve
     * @return a ResponseEntity containing the found area, or 404 if not found
     */
    @GetMapping("/{processId}")
    public ResponseEntity<Area> getAreatById(@PathVariable String processId) {
        Optional<Area> area = areaService.findById(processId);
        return area.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new area.
     *
     * @param area the area to create
     * @return a ResponseEntity containing the created area
     */
    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        Area createdArea = areaService.create(area);
        return ResponseEntity.status(201).body(createdArea);
    }

    /**
     * Updates an existing area.
     *
     * @param processId the process ID of the area to update
     * @param area the updated area data
     * @return a ResponseEntity containing the updated area, or 404 if not found
     */
    @PutMapping("/{processId}")
    public ResponseEntity<Area> updateArea(@PathVariable String processId, @RequestBody Area area) {
        Area updatedArea = areaService.update(processId, area);
        return updatedArea != null
                ? ResponseEntity.ok(updatedArea)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an area by its process ID.
     *
     * @param processId the process ID of the area to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        areaService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the active status of an area.
     *
     * @param processId the process ID of the area to update
     * @param isActive the new active status
     * @return a ResponseEntity indicating the result of the operation
     */
    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = areaService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    /**
     * Adds a process to an area.
     *
     * @param areaId the ID of the area
     * @param processId the ID of the process to add
     * @return a ResponseEntity containing the updated area
     */
    @PutMapping("/{areaId}/add-process/{processId}")
    public ResponseEntity<Area> addProcessToArea(@PathVariable String areaId,
                                                          @PathVariable String processId) {
        Area area = areaService.addProcessToArea(areaId,processId);
        return new ResponseEntity<>(area, HttpStatus.OK);
    }

}
