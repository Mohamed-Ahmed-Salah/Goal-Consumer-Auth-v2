package com.gconsumer.GConsumer.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity()
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
public class LoginHistory extends Model{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private UserCredential userCredential;

    private String status;
}
