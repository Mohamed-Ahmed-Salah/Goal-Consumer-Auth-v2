package com.gconsumer.GConsumer.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)

public class EmailDetails {


    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}