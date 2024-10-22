package com.palaceresorts.rule_utils_service.entity;


import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Process} class represents an entity in the Neo4j database.
 * This class is mapped as a Node entity in the graph database, where each instance
 * represents a process, with attributes such as its process ID, name, initial and end
 * dates, and whether it is active.
 * @author [AQUILES YONATAN ARMENTA HERNANDEZ]
 * @version 1.0
 * @since 2024-10-04
 * */

@Node
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Process {
    /**
     * The unique identifier for the process. This serves as the primary key in Neo4j.
     */
    @Id
    private String processId;


    /**
     * The name of the process.
     */
    private String name;

    /**
     * The date when the process started.
     */
    private LocalDateTime initialDate;

    /**
     * The date when the  process ended, if applicable.
     */
    private LocalDateTime endDate;

    /**
     * A flag indicating whether the process is currently active.
     * If {@code true}, the process is active; if {@code false}, the process is inactive (logically deleted).
     */
    private boolean active;

    /**
     * The process's objective statement.
     **/
    private String objective;

    /**
     * The process's model type (e.g., Department, Company, etc.).
     **/
    private String model;

    /**
     * The process's entity type as a generic object or a map to store dynamic data.
     **/
    private String entity;

    @Relationship(type = "has_subProcess", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<SubProcess> subProcesses = new HashSet<>();

}
