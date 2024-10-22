package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Department entities.
 * Extends Neo4jRepository to provide CRUD operations for Department entities.
 * Includes a custom query method to find active Department entities by processId.
 */

@Repository
public interface DepartmentRepository extends Neo4jRepository<Department,String> {

    /**
     * Finds an active Department entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Department entity, or empty if no active Department entity with the given processId exists
     */
    @Query("MATCH (d:Department) WHERE d.processId = $processId AND d.active = true RETURN d")
    Optional<Department> findByProcessIdAndIsActiveTrue(String processId);
}
