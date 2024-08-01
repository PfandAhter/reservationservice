package intern.freedesk.reservationservice.api.response;


import intern.freedesk.reservationservice.model.dto.DeskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class GetReservationsResponse {

    List<DeskDTO> deskList;
}
