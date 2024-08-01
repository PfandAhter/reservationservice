package intern.freedesk.reservationservice.api.response;

import intern.freedesk.reservationservice.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserIdResponse {

    private String userId;

    private Role role;

}
