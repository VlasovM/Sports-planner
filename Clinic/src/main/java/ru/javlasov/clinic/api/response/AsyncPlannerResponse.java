package ru.javlasov.clinic.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsyncPlannerResponse {

    private String responseStatus;

    private String responseMessage;

    @Override
    public String toString() {
        return "Статус: " + responseStatus + " сообщение: " + responseMessage;
    }
}
