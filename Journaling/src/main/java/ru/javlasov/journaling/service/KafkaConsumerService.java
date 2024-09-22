package ru.javlasov.journaling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.journaling.dto.LoggerEvent;
import ru.javlasov.journaling.model.Log;
import ru.javlasov.journaling.repository.LogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final LogRepository logRepository;

    @KafkaListener(topics = "topicKafka")
    @Transactional
    public void lister(LoggerEvent message) {
        var log = new Log();
        log.setCreated(LocalDateTime.now());
        log.setAction(message.getMessage());
        log.setType(message.getTypeMessage().getTitle());
        logRepository.save(log);
    }

}
