package intern.freedesk.reservationservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeskCreateRequest {

    private String deskId;
    private Integer deskNo;
    private String description;
}
