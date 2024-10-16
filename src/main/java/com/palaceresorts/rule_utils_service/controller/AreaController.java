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

@RestController
@RequestMapping("/api/rule/utils/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaService.findAll();
        return ResponseEntity.ok(areas);
    }


    @GetMapping("/{processId}")
    public ResponseEntity<Area> getAreatById(@PathVariable String processId) {
        Optional<Area> area = areaService.findById(processId);
        return area.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        Area createdArea = areaService.create(area);
        return ResponseEntity.status(201).body(createdArea);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<Area> updateArea(@PathVariable String processId, @RequestBody Area area) {
        Area updatedArea = areaService.update(processId, area);
        return updatedArea != null
                ? ResponseEntity.ok(updatedArea)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteArea(@PathVariable String processId) {
        areaService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = areaService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    @PutMapping("/{areaId}/add-process/{processId}")
    public ResponseEntity<Area> addProcessToArea(@PathVariable String areaId,
                                                          @PathVariable String processId) {
        Area area = areaService.addProcessToArea(areaId,processId);
        return new ResponseEntity<>(area, HttpStatus.OK);
    }

}
