package ru.javlasov.journaling.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.journaling.model.Log;

public interface LogRepository extends CrudRepository<Log, Long> {
}
