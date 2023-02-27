package com.gconsumer.GConsumer.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Operation extends Model {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "application_name", length = 50, nullable = false)
    private String applicationName;

    @Column(name = "description")
    private String description;
}
