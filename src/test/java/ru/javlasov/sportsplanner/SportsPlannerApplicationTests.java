package ru.javlasov.sportsplanner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class SportsPlannerApplicationTests {

    @Test
    void contextLoads() {
    }

}
