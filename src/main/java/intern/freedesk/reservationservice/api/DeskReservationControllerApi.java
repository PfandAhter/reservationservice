package intern.freedesk.reservationservice.api;


import intern.freedesk.reservationservice.api.request.*;
import intern.freedesk.reservationservice.api.response.GetReservationsResponse;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.api.response.DeskReservationResponse;
import intern.freedesk.reservationservice.api.response.ReservationListResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/deskReservation")
public interface DeskReservationControllerApi {

    @GetMapping("/{deskNo}")
    ResponseEntity<DeskReservationResponse> getDeskReservation(@NotBlank @PathVariable("deskNo") int deskNo); //TODO Body al burayi posta cevir

    //mockito.when desekReservationControllerApi.createReservation(any(DeskReservationRequest.class)).thenReturn(ResponseEntity.ok(new BaseResponse()));

    @PostMapping("/create")
    ResponseEntity<BaseResponse> createReservation(@Valid @RequestBody DeskReservationRequest deskReservationRequest);

    @PostMapping("/delete")
    ResponseEntity<BaseResponse> deleteReservation(@Valid @RequestBody DeskReservationDeleteRequest deskReservationDeleteRequest);


    @PostMapping("/oldReservations")
    ResponseEntity<ReservationListResponse> getOldReservationList(@Valid @RequestBody BaseRequest baseRequest);

    @GetMapping("/all")
    ResponseEntity<GetReservationsResponse> getAllReservations(@RequestParam String startDate, @RequestParam String endDate); //TODO Burasi react icin suanlik bu durumda ilerliyen zamanda icerisine baserequest gerekicek.

    @PostMapping("/update")
    ResponseEntity<BaseResponse> updateReservation(@Valid @RequestBody DeskReservationUpdateRequest deskReservationUpdateRequest);

    @PostMapping("/create/desk")
    ResponseEntity<BaseResponse> createDesk (@Valid @RequestBody DeskCreateRequest deskCreateRequest);

}
