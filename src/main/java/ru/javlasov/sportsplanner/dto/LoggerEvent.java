package ru.javlasov.sportsplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import ru.javlasov.sportsplanner.enums.TypeMessage;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class LoggerEvent implements Serializable {

    private String message;

    private TypeMessage typeMessage;

}
