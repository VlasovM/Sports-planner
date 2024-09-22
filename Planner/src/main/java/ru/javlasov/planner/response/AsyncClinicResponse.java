package ru.javlasov.planner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class AsyncClinicResponse {

    private HttpStatus status;

    private String message;

}
