package com.gconsumer.GConsumer.validatation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Getter
@Setter
@Accessors(chain = true)
public class ExistUser {
    private boolean exist;
    private String message;
}
