package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.client.TokenServiceClient;
import intern.freedesk.reservationservice.api.request.DeskReservationRequest;
import intern.freedesk.reservationservice.api.response.UserIdResponse;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.Status;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.model.entity.DeskReservation;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.repository.DeskReservationRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.MapperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class UserDeskServiceImplTest {

    @InjectMocks
    private UserDeskServiceImpl userDeskService;

    @Mock
    private DeskRepository deskRepository;

    @Mock
    private DeskReservationRepository deskReservationRepository;

    @Mock
    private MapperService mapperService;

    @Mock
    private TokenServiceClient tokenServiceClient;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void setReservationDates_shouldReturn_void_whenSetReservationDates(){
        DeskReservation deskReservation = new DeskReservation();
        userDeskService.setReservationDates(deskReservation,new DeskReservationRequest());

        Mockito.verify(deskReservationRepository,Mockito.times(1)).save(deskReservation);
    }

    @Test
    public void createReservation_shouldReturn_baseResponse_whenCreateReservation(){
        DeskReservationRequest deskReservationRequest = new DeskReservationRequest();
        UserIdResponse userIdResponse = new UserIdResponse();
        DeskReservation deskReservation = new DeskReservation();
        Desk desk = new Desk();
        desk.setActive(Status.ACTIVE.getValue());

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(null);
        Mockito.when(tokenServiceClient.extractUserId(Mockito.any())).thenReturn(userIdResponse);

        Mockito.when(mapperService.map(Mockito.any(Object.class),Mockito.any())).thenReturn(deskReservation);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
//        Mockito.when(deskReservationRepository.findDeskReservationsByUserIdAndDates(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

        userDeskService.createReservation(deskReservationRequest);


        Mockito.verify(deskRepository,Mockito.times(1)).save(desk);
        Mockito.verify(deskReservationRepository,Mockito.times(2)).save(deskReservation);
    }

    @Test
    public void createReservation_shouldThrowNotFoundException_whenUserNull(){
        Desk desk = new Desk();
        desk.setActive(Status.ACTIVE.getValue());
        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(tokenServiceClient.extractUserId(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> userDeskService.createReservation(new DeskReservationRequest()));
    }


    @Test
    public void createReservation_shouldThrowNotFoundException_whenUserReserveMoreThanOneTableOnSameDay(){
        DeskReservationRequest deskReservationRequest = new DeskReservationRequest();
        deskReservationRequest.setDeskId("1");

        UserIdResponse userIdResponse = new UserIdResponse();
        userIdResponse.setUserId("TESTUSER");
        DeskReservation deskReservation = new DeskReservation();
        deskReservation.setUserId("TESTUSER");
        deskReservation.setDeskId("1");
        List<DeskReservation> deskReservations = new ArrayList<>();
        deskReservations.add(deskReservation);

        Desk desk = new Desk();
        desk.setDeskId("1");
        desk.setActive(Status.ACTIVE.getValue());

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(tokenServiceClient.extractUserId(Mockito.any())).thenReturn(userIdResponse);

        Mockito.when(deskReservationRepository.findDeskReservationsByUserIdAndDates(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(deskReservations);

        Assertions.assertThrows(NotFoundException.class, () -> userDeskService.createReservation(deskReservationRequest));
    }


    @Test
    public void createReservation_shouldThrowNotFoundException_whenDeskNullOrDeskNotActive(){
        DeskReservationRequest deskReservationRequest =  new DeskReservationRequest();
        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> userDeskService.createReservation(deskReservationRequest));
    }













}
