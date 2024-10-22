package com.palaceresorts.rule_utils_service.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Company} class represents an entity in the Neo4j database.
 * This class is mapped as a Node entity in the graph database, where each instance
 * represents a company, with attributes such as its process ID, name, initial and end
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
public class Company {

    /**
     * The unique identifier for the company process. This serves as the primary key in Neo4j.
     */
    @Id
    private String processId;

    /**
     * The name of the company.
     */
    private String name;

    /**
     * The date when the company process started.
     */
    private Date initialDate;;

    /**
     * The date when the company process ended, if applicable.
     */
    private Date endDate;

    /**
     * A flag indicating whether the company is currently active.
     * If {@code true}, the company is active; if {@code false}, the company is inactive (logically deleted).
     */
    private boolean active;

    /** The company's mission statement.
    **/
     private String mission;

    /** The company's vision statement.
    **/
     private String vision;

    @Relationship(type = "has_department", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<Department> departments = new HashSet<>();


}
