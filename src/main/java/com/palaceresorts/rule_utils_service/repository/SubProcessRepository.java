package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Process;
import com.palaceresorts.rule_utils_service.entity.SubProcess;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubProcessRepository extends Neo4jRepository<SubProcess,String> {

    @Query("MATCH (sp:SubProcess) WHERE sp.processId = $processId AND sp.active = true RETURN sp")
    Optional<SubProcess> findByProcessIdAndIsActiveTrue(String processId);
}