package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.LoggerEvent;

public interface LoggingService {

    void sendMessage(LoggerEvent loggerEvent);

}
