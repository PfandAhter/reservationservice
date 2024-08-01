package intern.freedesk.reservationservice.rest.service.interfaces;

import intern.freedesk.reservationservice.api.request.DeskReservationRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.entity.DeskReservation;

public interface ICreateReservation {
    BaseResponse createReservation(DeskReservationRequest request) throws NotFoundException;

    void setReservationDates(DeskReservation deskReservation, DeskReservationRequest request);
}
