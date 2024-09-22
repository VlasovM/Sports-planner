package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.LoggerEvent;

public interface LoggingService {

    void sendMessage(LoggerEvent loggerEvent);

}
