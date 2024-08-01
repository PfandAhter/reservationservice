package intern.freedesk.reservationservice.api.response;

import intern.freedesk.reservationservice.lib.constants.Constants;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class BaseResponse {

    private String resultCode = Constants.SUCCESS;

    private String message = Constants.SUCCESS;

}
