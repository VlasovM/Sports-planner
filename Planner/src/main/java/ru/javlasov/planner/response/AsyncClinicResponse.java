package ru.javlasov.planner.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AsyncClinicResponse {

    private volatile static AsyncClinicResponse uniqueResponse;

    private String responseStatus = "SUCCEED";

    private String responseMessage = "Сообщение успешно принято системой \"Sports-planner\"";

    public static AsyncClinicResponse getInstance() {
        if (uniqueResponse == null) {
            synchronized (AsyncClinicResponse.class) {
                if (uniqueResponse == null) {
                    uniqueResponse = new AsyncClinicResponse();
                }
            }
        }
        return uniqueResponse;
    }

}
