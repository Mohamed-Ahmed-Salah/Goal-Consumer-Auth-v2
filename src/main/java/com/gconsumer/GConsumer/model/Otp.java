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
public class Otp extends Model {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredential user;
//
    @Column(name = "otp", length = 50, nullable = false, unique = true)
    private String otp;
//
    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "status")
    private String status;


}
