package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.DeskReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeskReservationImplTest {


    @InjectMocks
    private DeskReservationServiceImpl deskReservationService;

    @Mock
    private DeskRepository deskRepository;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }





}
