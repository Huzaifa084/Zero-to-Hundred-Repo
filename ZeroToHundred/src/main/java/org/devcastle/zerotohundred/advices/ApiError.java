package org.devcastle.zerotohundred.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private boolean operationCompleted = true;
    private List<String> subErrors;
}
