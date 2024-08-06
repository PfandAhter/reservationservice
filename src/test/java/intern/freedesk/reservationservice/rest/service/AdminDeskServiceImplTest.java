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

public class AdminDeskServiceImplTest {

    @InjectMocks
    private AdminDeskServiceImpl adminDeskService;

    @Mock
    private DeskRepository deskRepository;

    @Mock
    private TokenServiceClient tokenServiceClient;

    @Mock
    private MapperService mapperService;

    @Mock
    private DeskReservationRepository deskReservationRepository;

    @BeforeEach
    public void init(){
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createReservation_shouldReturn_baseResponse_whenCreateReservation(){
        UserIdResponse userIdResponse = new UserIdResponse();
        userIdResponse.setUserId("TESTUSER");

        Desk desk = new Desk();
        desk.setActive(Status.ACTIVE.getValue());
        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(tokenServiceClient.extractUserId(Mockito.any())).thenReturn(userIdResponse);
        Mockito.when(mapperService.map(Mockito.any(Object.class),Mockito.any())).thenReturn(new DeskReservation());

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);

        adminDeskService.createReservation(new DeskReservationRequest());
        Mockito.verify(deskRepository,Mockito.times(1)).save(desk);
    }

    @Test
    public void createReservation_shouldThrowNotFoundException_whenDeskNotActive(){
        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> adminDeskService.createReservation(new DeskReservationRequest()));
    }

    @Test
    public void createReservation_shouldThrowNotFoundException_whenUserNotFound(){
        Desk desk = new Desk();
        desk.setActive(Status.ACTIVE.getValue());

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(tokenServiceClient.extractUserId(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> adminDeskService.createReservation(new DeskReservationRequest()));
    }

    @Test
    public void setReservationDates_shouldReturn_void_whenSetReservationDates(){
        DeskReservation deskReservation = new DeskReservation();
        adminDeskService.setReservationDates(deskReservation,new DeskReservationRequest());

        Mockito.verify(deskReservationRepository,Mockito.times(1)).save(deskReservation);
    }
}
