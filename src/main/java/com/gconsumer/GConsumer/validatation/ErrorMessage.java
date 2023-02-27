package com.gconsumer.GConsumer.validatation;

import com.gconsumer.GConsumer.config.Constant;
import com.gconsumer.GConsumer.dto.response.GeneralResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String field;
    private String message;
    private String rejectedValue;


    public static GeneralResponse setValidationMessages(BindingResult result) {
        Map<String, Object> fieldErrors = new HashMap<>();
        result.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, fieldErrors);

    }

    public static ResponseEntity<?> sendValidationMessages(BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(setValidationMessages(result));

        return null;
    }

}
