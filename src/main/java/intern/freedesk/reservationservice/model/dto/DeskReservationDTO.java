package intern.freedesk.reservationservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class DeskReservationDTO {

    private String deskId;
    private int deskNo;

    private Timestamp createDate;
    private Timestamp updateDate;

    private String startDate;
    private String endDate;

}
