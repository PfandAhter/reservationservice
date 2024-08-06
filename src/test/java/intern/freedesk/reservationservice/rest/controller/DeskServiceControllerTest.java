package intern.freedesk.reservationservice.rest.controller;

import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.rest.controller.DeskServiceController;
import intern.freedesk.reservationservice.rest.service.DeskServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//mockito kullaniliyorsa runwith


//@ExtendWith(MockitoExtension.class)
class DeskServiceControllerTest {

    @InjectMocks
    private DeskServiceController deskServiceController;

    @Mock
    private DeskServiceImpl deskService;

    @Mock
    private DeskRepository deskRepository;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void createDesk_shouldReturnHttpStatus200_whenSuccessCreate() {
        createRequest.setDeskNo(5);

        ResponseEntity<BaseResponse> response = deskServiceController.createDesk(createRequest);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

    @Test
    public void createDesk_shouldReturnHttpStatus200_whenSuccessCreate2() {
        createRequest.setDeskId("5");
        createRequest.setDeskNo(5);
        createRequest.setDescription("test");
        when(deskService.updateDesk(Mockito.any())).thenReturn(new BaseResponse());
        Assertions.assertDoesNotThrow(() -> deskServiceController.updateDesk(createRequest));

    }

    private DeskCreateRequest createRequest = new DeskCreateRequest();

}