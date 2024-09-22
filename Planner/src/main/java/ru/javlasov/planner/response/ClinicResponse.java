package ru.javlasov.planner.response;

import lombok.Getter;
import lombok.Setter;
import ru.javlasov.planner.enums.Status;

@Getter
@Setter
public class ClinicResponse {

    private Status status;

    private String message;

    @Override
    public String toString() {
        if (message.isEmpty()) {
            return "Статус: " + status.getTitle() + " - " + status.getText();
        }
        return "Статус: " + status.getTitle() + " - " + status.getText() + ". Сообщение: " + message;
    }

}
