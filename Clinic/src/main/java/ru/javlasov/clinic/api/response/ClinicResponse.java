package ru.javlasov.clinic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.javlasov.clinic.enums.Status;

@Getter
@Setter
@AllArgsConstructor
public class ClinicResponse {


    private Status status;

    private String message;

    @Override
    public String toString() {
        return "Статус: " + status + " сообщение: " + message;
    }

}
