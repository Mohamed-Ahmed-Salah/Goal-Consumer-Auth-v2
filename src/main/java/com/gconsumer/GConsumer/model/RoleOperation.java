package com.gconsumer.GConsumer.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleOperation extends Model {

    @Column(name = "role_name", length = 50, nullable = false)
    private String roleName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    private Operation operation;

    @Column(name = "application_name", length = 50, nullable = false)
    private String applicationName;

}
