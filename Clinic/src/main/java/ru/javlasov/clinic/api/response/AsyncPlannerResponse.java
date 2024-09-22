package ru.javlasov.clinic.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AsyncPlannerResponse {

    private HttpStatus status;

    private String message;

    @Override
    public String toString() {
        return "Статус: " + status + " сообщение: " + message;
    }
}
