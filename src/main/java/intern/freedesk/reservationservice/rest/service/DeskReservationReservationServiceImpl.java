package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.client.TokenServiceClient;
import intern.freedesk.reservationservice.api.client.UserServiceClient;
import intern.freedesk.reservationservice.api.request.*;
import intern.freedesk.reservationservice.api.response.*;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.Role;
import intern.freedesk.reservationservice.model.Status;
import intern.freedesk.reservationservice.model.dto.DeskDTO;
import intern.freedesk.reservationservice.model.dto.DeskReservationDTO;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.model.entity.DeskReservation;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.repository.DeskReservationRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.DeskReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class DeskReservationReservationServiceImpl implements DeskReservationService {

    private final DeskRepository deskRepository;

    private final DeskReservationRepository deskReservationRepository;

    private final MapperServiceImpl mapperService;

    private final TokenServiceClient tokenServiceClient;

    private final UserServiceClient userServiceClient;

    private final UserDeskService userDeskService;

    private final AdminDeskService adminDeskService;


    @Transactional
    public BaseResponse createDesk (DeskCreateRequest request){
        try {
            Desk desk = mapperService.map(request, Desk.class);

            if (desk == null) {
                throw new NotFoundException("DESK NO IN USE");
            }

            desk.setActive(Status.ACTIVE.getValue());
            deskRepository.save(desk);
            return new BaseResponse();
        }catch (Exception e){
            log.error("Create desk error: " + e);
            throw new NotFoundException("DESK NOT FOUND");
        }
    }


    @Transactional
    public BaseResponse createReservation(DeskReservationRequest request) throws NotFoundException {
        try {
            UserIdResponse userInfo = tokenServiceClient.extractUserId(request);
            userInfo.setRole(userServiceClient.extractRoleFromToken(userInfo.getUserId()).getRole());

            if (userInfo.getUserId() == null) {
                throw new NotFoundException("USER NOT FOUND");
            }

            if(userInfo.getRole().equals(Role.ADMIN)){
                return adminDeskService.createReservation(request);
            }else{
                return userDeskService.createReservation(request);
            }

        }catch (Exception e){
            log.error("Create reservation error: " + e);
            throw new NotFoundException("DESK NOT FOUND");
        }
    }


    @Transactional
    public BaseResponse deleteReservation(DeskReservationDeleteRequest request) throws NotFoundException {
        try{
            Desk desk = deskRepository.findByDeskNo(request.getDeskNo());

            if (desk == null) {
                throw new NotFoundException("DESK NOT FOUND");
            }

            DeskReservation deskReservation = deskReservationRepository.findReservationByDeskId(desk.getDeskId());

            if (deskReservation == null) {
                throw new NotFoundException("RESERVATION NOT FOUND");
            }

            UserIdResponse userId = tokenServiceClient.extractUserId(request);

            if (userId == null){
                throw new NotFoundException("USER NOT FOUND");
            }

//        deskReservation.setActive(Status.PASSIVE.getValue());
//        deskReservationRepository.save(deskReservation);

            deskReservationRepository.delete(deskReservation);
            return new BaseResponse();
        }catch (Exception e){
            log.error("Delete reservation error: " + e);
            throw new NotFoundException("RESERVATION NOT DELETED","405");
        }
    }



    @Transactional
    public BaseResponse updateReservation(DeskReservationUpdateRequest request) throws NotFoundException {
        try {
            Desk desk = deskRepository.findByDeskId(request.getDeskId());

            if (desk == null) {
                throw new NotFoundException("DESK NOT FOUND");
            }
            DeskReservation deskReservation = deskReservationRepository.findReservationByDeskId(desk.getDeskId());

            if (deskReservation == null) {
                throw new NotFoundException("RESERVATION NOT FOUND");
            }
//        UserIdResponse userId = tokenServiceClient.extractUserIDByEmail(request);

            if (request.getEmail() == null) {
                throw new NotFoundException("USER NOT FOUND");
            }
            setReservationDates(deskReservation, request);

            deskReservationRepository.save(deskReservation);
            return new BaseResponse();
        }catch (Exception e){
            log.error("Update reservation error: " + e);
            throw new NotFoundException("RESERVATION NOT UPDATED");
        }
    }


    public DeskReservationResponse getReservationByDeskNo(int deskNo) throws NotFoundException {
        Desk desk = deskRepository.findByDeskNo(deskNo);

        if (desk == null) {
            throw new NotFoundException("DESK NOT FOUND");
        }

        DeskReservation deskReservation = deskReservationRepository.findReservationByDeskId(desk.getDeskId());

        if (deskReservation == null) {
            throw new NotFoundException("RESERVATION NOT FOUND");
        }

        return DeskReservationResponse.builder()
                .deskNo(deskNo)
                .reservationDate(deskReservation.getCreateDate())
                .userEmail(deskReservation.getUserId())
                .build();
    }


    public ReservationListResponse getOldReservationList(BaseRequest request)throws NotFoundException {
        UserIdResponse userIdResponse = tokenServiceClient.extractUserId(request);

        List<DeskReservation> reservationByEmail = deskReservationRepository.findReservationByUserId(userIdResponse.getUserId());

        if(reservationByEmail == null){
            throw new NotFoundException("RESERVATION NOT FOUND");
        }
        List<DeskReservationDTO> deskReservationDTOList = mapperService.map(reservationByEmail, DeskReservationDTO.class);

        for (DeskReservationDTO deskReservationDTO: deskReservationDTOList) {
            deskReservationDTO.setDeskNo(deskRepository.findByDeskId(deskReservationDTO.getDeskId()).getDeskNo());
        }


        return new ReservationListResponse(deskReservationDTOList);
    }



    public GetReservationsResponse getReservations(String startDate, String endDate){
        try{
        checkEndDates();

        List<DeskReservation> reservationListByDate = deskReservationRepository.findReservationByDate(startDate, endDate);
        List<DeskDTO> reservationList = mapperService.map(deskRepository.findAll(), DeskDTO.class);

        List<DeskDTO> desksToRemove = new ArrayList<>();

        for (DeskDTO deskList : reservationList) {
            for (DeskReservation reservation: reservationListByDate) {
                if (deskList.getDeskId().equals(reservation.getDeskId())) {
                    desksToRemove.add(deskList);
                    break;
                }
            }
        }
        reservationList.removeAll(desksToRemove);

        return new GetReservationsResponse(reservationList);

        } catch (Exception e){
            log.error("Get reservations error: " + e);
            throw new NotFoundException("RESERVATIONS NOT FOUND");
        }
    }


    private void checkEndDates (){
        List<DeskReservation> deskReservationList = deskReservationRepository.findAll();
        Date date = new Date();

        int month = date.getMonth()+1;
        int day = date.getDate();
        int year = date.getYear() - 100;

        for (DeskReservation deskReservation: deskReservationList) {
            int reservationEndDay = Integer.parseInt(deskReservation.getEndDate().toString().substring(8,10)); // 8,10
            int reservationEndMonth = Integer.parseInt(deskReservation.getEndDate().toString().substring(5,7)); // 5,7
            int reservationEndYear = Integer.parseInt(deskReservation.getEndDate().toString().substring(2,4)); // 2,4

            if(month > reservationEndMonth || (day > reservationEndDay && month == reservationEndMonth) || year > reservationEndYear){
                deskReservation.setActive(Status.PASSIVE.getValue());
                Desk desk = deskRepository.findByDeskId(deskReservation.getDeskId());
                desk.setActive(Status.ACTIVE.getValue());
                deskRepository.save(desk);

                deskReservationRepository.save(deskReservation);
            }
        }
    }

    private void setReservationDates(DeskReservation deskReservation, DeskReservationRequest request){
        try {
            deskReservation.setUpdateDate(Timestamp.from(Instant.now()));
            deskReservation.setStartDate(request.getReservationStartDate());
            deskReservation.setEndDate(request.getReservationEndDate());

            deskReservationRepository.save(deskReservation);
        }catch (Exception e) {
            log.error("Set reservation dates error: " + e);
            throw new NotFoundException("RESERVATION DATES NOT FOUND");
        }
    }

}