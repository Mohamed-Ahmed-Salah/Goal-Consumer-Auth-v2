package com.gconsumer.GConsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity()
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
public class UserCredential extends Model {

    //    @Size(min = 10,message = "10")
    @Column(name = "full_name")
    private String fullName;

    //    @Pattern(regexp = "/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/", message = "Invalid password")
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "email", length = 100,unique = true)
    private String email;


    @Column(name = "phone", length = 100,unique = true)
    private String phone;

    @Column(name = "is_enable")
    private boolean enable;

    @Column(name = "status")
    private String status;

    @Column(name = "is_lock")
    private boolean lock;

    @Column(name = "otp")
    private boolean otp;


    @Column(name = "role")
    private String role;


    @Column(name ="app_name")
    private String applicationName;
    @Column(name ="app_description")
    private String applicationDescription;
    private String company;

    @Column(name = "notes")
    private String notes;


}
