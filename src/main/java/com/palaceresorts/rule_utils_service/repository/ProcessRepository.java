package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.entity.SubProcess;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Process entities.
 * Extends Neo4jRepository to provide CRUD operations for Process entities.
 * Includes a custom query method to find active Process entities by processId.
 */
@Repository
public interface ProcessRepository extends Neo4jRepository<Process,String> {

    /**
     * Finds an active Process entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Process entity, or empty if no active Process entity with the given processId exists
     */
    @Query("MATCH (p:Process) WHERE p.processId = $processId AND p.active = true RETURN p")
    Optional<Process> findByProcessIdAndIsActiveTrue(String processId);

    @Query("MATCH (p:Process) WHERE p.name = $name AND p.active = true RETURN p LIMIT 1")
    Optional<Process> findByName(String name);

}
