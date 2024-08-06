package intern.freedesk.reservationservice.api.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter

public class DeskReservationRequest extends BaseRequest {

    private String deskId;

    private String reservationStartDate; //timestamp

    private String reservationEndDate; //timestamp


}
