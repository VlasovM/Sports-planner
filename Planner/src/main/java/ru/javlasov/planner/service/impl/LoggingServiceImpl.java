package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.kafka.config.KafkaConfig;
import ru.javlasov.planner.service.LoggingService;

@RequiredArgsConstructor
@Service
public class LoggingServiceImpl implements LoggingService {

    private final KafkaTemplate<String, LoggerEvent> kafkaTemplate;

    @Override
    public void sendMessage(LoggerEvent loggerEvent) {
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, loggerEvent);
    }

}
