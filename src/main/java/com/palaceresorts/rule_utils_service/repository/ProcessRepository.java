package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Process;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends Neo4jRepository<Process,String> {
    @Query("MATCH (p:Process) WHERE p.processId = $processId AND p.active = true RETURN p")
    Optional<Process> findByProcessIdAndIsActiveTrue(String processId);
}
