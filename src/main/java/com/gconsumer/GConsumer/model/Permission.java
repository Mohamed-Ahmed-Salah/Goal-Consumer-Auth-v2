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
public class Permission extends Model {

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "operation_name", length = 50, nullable = false)
    private String operationName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredential userCredential;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    private Operation operation;

    public Permission(UserCredential userCredential, Operation operation) {

    }
}
