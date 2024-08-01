package intern.freedesk.reservationservice.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Builder

public class DeskReservationResponse {

    private String userEmail;
    private int deskNo;
    private int active;
    private Date reservationDate; // was timestamp

}
