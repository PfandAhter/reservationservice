package intern.freedesk.reservationservice.rest.controller;

import intern.freedesk.reservationservice.api.DeskReservationControllerApi;
import intern.freedesk.reservationservice.api.request.*;
import intern.freedesk.reservationservice.api.response.*;
import intern.freedesk.reservationservice.rest.service.DeskReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor //undefined check
@CrossOrigin

public class DeskReservationServiceController implements DeskReservationControllerApi {

    private final DeskReservationServiceImpl deskService;

    @Override
    public ResponseEntity<DeskReservationResponse> getDeskReservation(int deskNo) {
        return ResponseEntity.ok(deskService.getReservationByDeskNo(deskNo));
    }

    @Override
    public ResponseEntity<BaseResponse> createReservation(DeskReservationRequest deskReservationRequest) {
        return ResponseEntity.ok(deskService.createReservation(deskReservationRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteReservation(DeskReservationDeleteRequest deskReservationDeleteRequest) {
        return ResponseEntity.ok(deskService.deleteReservation(deskReservationDeleteRequest));
    }

    @Override
    public ResponseEntity<ReservationListResponse> getOldReservationList(BaseRequest baseRequest) {
        return ResponseEntity.ok(deskService.getOldReservationList(baseRequest));
    }

    @Override
    public ResponseEntity<GetReservationsResponse> getAllReservations(String startDate, String endDate) {
        return ResponseEntity.ok(deskService.getReservations(startDate, endDate));
    }

    @Override
    public ResponseEntity<BaseResponse> updateReservation(DeskReservationUpdateRequest deskReservationUpdateRequest) {
        return ResponseEntity.ok(deskService.updateReservation(deskReservationUpdateRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> createDesk(DeskCreateRequest deskCreateRequest) {
        return ResponseEntity.ok(deskService.createDesk(deskCreateRequest));
    }

}
