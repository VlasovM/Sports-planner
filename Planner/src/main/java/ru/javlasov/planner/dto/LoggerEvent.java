package ru.javlasov.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.javlasov.planner.enums.TypeMessage;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class LoggerEvent implements Serializable {

    private String message;

    private TypeMessage typeMessage;

}
