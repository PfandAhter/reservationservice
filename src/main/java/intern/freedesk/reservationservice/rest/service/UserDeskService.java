package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.client.TokenServiceClient;
import intern.freedesk.reservationservice.api.client.UserServiceClient;
import intern.freedesk.reservationservice.api.request.DeskReservationRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.api.response.UserIdResponse;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.Status;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.model.entity.DeskReservation;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.repository.DeskReservationRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.ICreateReservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserDeskService implements ICreateReservation {

    private final DeskRepository deskRepository;

    private final TokenServiceClient tokenServiceClient;

    private final DeskReservationRepository deskReservationRepository;

    private final MapperServiceImpl mapperService;


    @Override
    public BaseResponse createReservation(DeskReservationRequest request) throws NotFoundException {
        try {
            Desk desk = deskRepository.findByDeskId(request.getDeskId());

            if (desk == null && desk.getActive().equals(Status.ACTIVE.getValue())) {
                throw new NotFoundException("DESK NOT FOUND \nDESK NOT ACTIVE");
            }

            desk.setActive(Status.RESERVED.getValue());
            deskRepository.save(desk);

//        UserIdResponse userId = tokenServiceClient.extractUserIDByEmail(request);

            UserIdResponse userId = tokenServiceClient.extractUserId(request);


            if (userId == null) {
                throw new NotFoundException("USER NOT FOUND");
            }

            if (checkDidUserReserveMoreThanOneTableOnTheSameDay(userId.getUserId(), request.getDeskId(), request.getReservationStartDate(), request.getReservationEndDate())) {
                throw new NotFoundException("USER ALREADY RESERVED A DESK ON THE SAME DAYS");
            }

            DeskReservation deskReservation = mapperService.map(request, DeskReservation.class);

            deskReservation.setUserId(userId.getUserId());
            deskReservation.setActive(Status.ACTIVE.getValue());

            deskReservation.setDeskId(deskRepository.findByDeskId(request.getDeskId()).getDeskId());
            deskReservation.setCreateDate(Timestamp.from(Instant.now()));

            deskReservationRepository.save(deskReservation);
            setReservationDates(deskReservation, request);

        } catch (Exception e) {
            log.error("Create reservation error: " + e);
            throw new NotFoundException("DESK NOT FOUND");
        }
        return new BaseResponse();
    }


    @Override
    public void setReservationDates(DeskReservation deskReservation, DeskReservationRequest request) {
        deskReservation.setUpdateDate(Timestamp.from(Instant.now()));
        deskReservation.setStartDate(request.getReservationStartDate());
        deskReservation.setEndDate(request.getReservationEndDate());

        deskReservationRepository.save(deskReservation);

    }


    private boolean checkDidUserReserveMoreThanOneTableOnTheSameDay(String userId, String deskId, String startDate, String endDate) {
        List<DeskReservation> deskUserReservations = deskReservationRepository.findDeskReservationsByUserIdAndDates(userId, startDate, endDate);

        for (DeskReservation deskReservation : deskUserReservations) {
            if (deskReservation.getDeskId().equals(deskId) && deskReservation.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

}
