package com.gconsumer.GConsumer.dto.response;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(chain = true)
@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    private Map<String, Object> data;

}
