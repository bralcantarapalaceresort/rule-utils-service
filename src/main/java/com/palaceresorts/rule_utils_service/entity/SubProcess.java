package com.palaceresorts.rule_utils_service.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;

/**
 * The {@code SubProcess} class represents an entity in the Neo4j database.
 * This class is mapped as a Node entity in the graph database, where each instance
 * represents a SubProcess, with attributes such as its process ID, name, initial and end
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
public class SubProcess {
    /**
     * The unique identifier for the company process. This serves as the primary key in Neo4j.
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
     * The process's entity type as a generic object or a map to store dynamic data.
     **/
    private String rule;
}
