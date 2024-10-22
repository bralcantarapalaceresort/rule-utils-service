package com.palaceresorts.rule_utils_service.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleComponent {
    @Id
    private String processId;

    private String name;

    private Date initialDate;;

    private Date endDate;

    private boolean active;

    private String verb;

    private String participation;

    private String role;

    private String element;

}
