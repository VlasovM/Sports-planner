package ru.javlasov.planner.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicResponse {

    private String status;

    private Long code;

    private String message;

}
