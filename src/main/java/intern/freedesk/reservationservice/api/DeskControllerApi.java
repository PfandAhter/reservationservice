package intern.freedesk.reservationservice.api;

import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.api.request.DeskDeleteRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.api.response.GetDeskListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/desk") //tag //operation
public interface DeskControllerApi {

    @PostMapping("/create")
    ResponseEntity<BaseResponse> createDesk (DeskCreateRequest deskCreateRequest);

    @PostMapping("/update")
    ResponseEntity<BaseResponse> updateDesk (DeskCreateRequest deskCreateRequest);

    @PostMapping("/delete")
    ResponseEntity<BaseResponse> deleteDesk (DeskDeleteRequest deskDeleteRequest);

    @GetMapping("/list")
    ResponseEntity<GetDeskListResponse> listDesk();

    @GetMapping("/activate")
    ResponseEntity<BaseResponse> activateDesk(@RequestParam String deskId);

    @GetMapping("/deActivate")
    ResponseEntity<BaseResponse> deactivateDesk(@RequestParam String deskId);

}
