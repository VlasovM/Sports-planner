package ru.javlasov.clinic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Specialization {

    UNKNOWN(1L, "Не установлено"),
    THERAPIST(2L, "Терапевт"),
    SURGEON(3L, "Хирург"),
    OPHTHALMOLOGIST(4L, "Офтальмолог");

    private final Long id;

    private final String title;

    public static Specialization findById(Long id) {
        for (Specialization specialization : values()) {
            if (specialization.id.equals(id)) {
                return specialization;
            }
        }
        return UNKNOWN;
    }

}
