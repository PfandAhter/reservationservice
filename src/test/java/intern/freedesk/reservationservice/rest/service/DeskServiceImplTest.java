package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.api.request.DeskDeleteRequest;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.MapperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class DeskServiceImplTest {

    @InjectMocks
    private DeskServiceImpl deskService;

    @Mock
    private DeskRepository deskRepository;

    @Mock
    private MapperService mapperService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateDesk_shouldReturn_baseResponse_whenUpdateDescription() {
        Desk desk = new Desk();
        desk.setDeskNo(15);

        DeskCreateRequest createRequest = new DeskCreateRequest();
        createRequest.setDeskNo(15);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        deskService.updateDesk(createRequest);
        Mockito.verify(deskRepository).save(desk);
    }

    @Test
    void updateDesk_shouldThrowNotFoundException_whenDeskAlreadyExist() {
        Desk desk = new Desk();
        desk.setDeskNo(15);

        DeskCreateRequest createRequest = new DeskCreateRequest();
        createRequest.setDeskNo(16);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(deskRepository.findByDeskNo(Mockito.anyInt())).thenReturn(desk);
        Assertions.assertThrows(NotFoundException.class, () -> deskService.updateDesk(createRequest));
    }

    @Test
    void updateDesk_shouldReturn_baseResponse_whenUpdateDeskNoAndDescription(){
        Desk desk = new Desk();
        desk.setDeskNo(15);

        DeskCreateRequest createRequest = new DeskCreateRequest();
        createRequest.setDeskNo(16);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        Mockito.when(deskRepository.findByDeskNo(Mockito.anyInt())).thenReturn(null);

        deskService.updateDesk(createRequest);
        Mockito.verify(deskRepository).save(desk);
    }

    @Test
    void listDesk_shouldReturn_getDeskListResponse(){
        Mockito.when(deskRepository.findAllActiveAndDeActivateAndReservedDesks()).thenReturn(null);
        Mockito.when(mapperService.map(Mockito.any(),Mockito.any())).thenReturn(null);

        deskService.listDesk();
        Mockito.verify(deskRepository).findAllActiveAndDeActivateAndReservedDesks();
    }

    @Test
    void createDesk_shouldReturn_baseResponse_whenCreateDesk(){
        DeskCreateRequest createRequest = new DeskCreateRequest();
        createRequest.setDeskNo(15);

        deskService.createDesk(createRequest);
        Mockito.verify(deskRepository).save(Mockito.any());
    }

    @Test
    void deleteDesk_shouldReturn_baseResponse_whenDeleteDesk(){
        Desk desk = new Desk();
        desk.setDeskNo(15);
        DeskDeleteRequest deskDeleteRequest = new DeskDeleteRequest();

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        deskDeleteRequest.setDeskId(desk.getDeskId());

        deskService.deleteDesk(deskDeleteRequest);
        Mockito.verify(deskRepository).delete(desk);
    }

    @Test
    void activateDesk_shouldReturn_baseResponse_whenActivateDesk(){
        Desk desk = new Desk();
        desk.setDeskNo(15);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        deskService.activateDesk(desk.getDeskId());
        Mockito.verify(deskRepository).save(desk);
    }

    @Test
    void activateDesk_shouldThrowNotFoundException_whenDeskNotFound(){
        Desk desk = new Desk();
        desk.setDeskNo(15);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> deskService.activateDesk(desk.getDeskId()));
    }

    @Test
    void deactivateDesk_shouldReturn_baseResponse_whenDeActivateDesk(){
        Desk desk = new Desk();
        desk.setDeskNo(5);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(desk);
        deskService.deactivateDesk(desk.getDeskId());
        Mockito.verify(deskRepository).save(desk);
    }

    @Test
    void deActivateDesk_shouldThrowNotFoundException_whenDeskNotFound(){
        Desk desk = new Desk();
        desk.setDeskNo(5);

        Mockito.when(deskRepository.findByDeskId(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class,() ->deskService.deactivateDesk(desk.getDeskId()));


    }


}