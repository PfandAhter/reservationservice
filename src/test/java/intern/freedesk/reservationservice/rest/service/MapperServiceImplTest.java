package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.rest.service.interfaces.MapperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapperServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MapperService mapperService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void map_shouldReturnList_whenSourceIsList(){
        // given

        List<Desk> sourceList = new ArrayList<>();
        Desk testDesk = new Desk();

        testDesk.setDeskNo(15);
        sourceList.add(testDesk);
        mapperService.map(sourceList, DeskCreateRequest.class);

        // when
        //List<DeskCreateRequest> result = mapperService.map(sourceList, DeskCreateRequest.class);

        // then
//        Assertions.assertEquals(0, result.size());

        //        Assertions.assertEquals(15, result.get(0).getDeskNo());
    }

}
