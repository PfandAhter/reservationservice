package intern.freedesk.reservationservice.rest.controller;

import intern.freedesk.reservationservice.api.DeskControllerApi;
import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.api.request.DeskDeleteRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.api.response.GetDeskListResponse;
import intern.freedesk.reservationservice.rest.service.DeskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
@CrossOrigin

public class DeskServiceController implements DeskControllerApi {

    private final DeskServiceImpl deskService;

    @Override
    public ResponseEntity<BaseResponse> createDesk(DeskCreateRequest deskCreateRequest) {
        return ResponseEntity.ok(deskService.createDesk(deskCreateRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> updateDesk(DeskCreateRequest deskCreateRequest) {
        return ResponseEntity.ok(deskService.updateDesk(deskCreateRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteDesk(DeskDeleteRequest deskDeleteRequest) {
        return ResponseEntity.ok(deskService.deleteDesk(deskDeleteRequest));
    }

    @Override
    public ResponseEntity<GetDeskListResponse> listDesk() {
        return ResponseEntity.ok(deskService.listDesk());
    }

    @Override
    public ResponseEntity<BaseResponse> activateDesk(String deskId) {
        return ResponseEntity.ok(deskService.activateDesk(deskId));
    }

    @Override
    public ResponseEntity<BaseResponse> deactivateDesk(String deskId) {
        return ResponseEntity.ok(deskService.deactivateDesk(deskId));
    }
}
