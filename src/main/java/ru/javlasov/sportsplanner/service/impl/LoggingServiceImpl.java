package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.kafka.config.KafkaConfig;
import ru.javlasov.sportsplanner.service.LoggingService;

@RequiredArgsConstructor
@Service
public class LoggingServiceImpl implements LoggingService {

    private final KafkaTemplate<String, LoggerEvent> kafkaTemplate;

    @Override
    public void sendMessage(LoggerEvent loggerEvent) {
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, loggerEvent);
    }

}
