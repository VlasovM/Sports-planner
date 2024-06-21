package ru.javlasov.journaling.dto;

import lombok.Getter;
import lombok.Setter;
import ru.javlasov.journaling.enums.TypeMessage;

@Getter
@Setter
public class LoggerEvent {

    private String message;

    private TypeMessage typeMessage;

}
