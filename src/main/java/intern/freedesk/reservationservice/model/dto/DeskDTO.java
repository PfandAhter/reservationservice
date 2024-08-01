package intern.freedesk.reservationservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class DeskDTO {

    private String deskId;
    private String deskNo;
    private String description;


    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer active;
}