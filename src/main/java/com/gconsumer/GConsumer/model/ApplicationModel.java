package com.gconsumer.GConsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModel extends Model {

    @Column(name = "application_name")
    private String applicationName;

}
