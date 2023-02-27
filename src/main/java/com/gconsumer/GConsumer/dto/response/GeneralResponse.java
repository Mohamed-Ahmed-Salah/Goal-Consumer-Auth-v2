package com.gconsumer.GConsumer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor

public class GeneralResponse implements Serializable {

    int code;

    String message;

    Object body;
}
