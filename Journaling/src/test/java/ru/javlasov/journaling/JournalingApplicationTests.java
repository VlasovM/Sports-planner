package ru.javlasov.journaling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class JournalingApplicationTests {

    @Test
    void contextLoads() {
    }

}
