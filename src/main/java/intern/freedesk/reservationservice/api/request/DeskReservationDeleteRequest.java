package intern.freedesk.reservationservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeskReservationDeleteRequest extends BaseRequest {

//    private String email;

    private int deskNo;
}
