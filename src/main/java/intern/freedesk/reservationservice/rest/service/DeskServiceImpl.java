package intern.freedesk.reservationservice.rest.service;

import intern.freedesk.reservationservice.api.request.DeskCreateRequest;
import intern.freedesk.reservationservice.api.request.DeskDeleteRequest;
import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.api.response.GetDeskListResponse;
import intern.freedesk.reservationservice.exceptions.NotFoundException;
import intern.freedesk.reservationservice.model.Status;
import intern.freedesk.reservationservice.model.dto.DeskDTO;
import intern.freedesk.reservationservice.model.entity.Desk;
import intern.freedesk.reservationservice.repository.DeskRepository;
import intern.freedesk.reservationservice.rest.service.interfaces.DeskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j

public class DeskServiceImpl implements DeskService {

    private final DeskRepository deskRepository;

    private final MapperServiceImpl mapperService;


    public GetDeskListResponse listDesk (){
        List<Desk> deskList = deskRepository.findAllActiveAndDeActivateAndReservedDesks();
        List<DeskDTO> deskDTOList = mapperService.map(deskList, DeskDTO.class);


        return new GetDeskListResponse(deskDTOList);
    }

    public BaseResponse createDesk (DeskCreateRequest deskCreateRequest) {
        Desk desk = new Desk();
        desk.setDeskNo(deskCreateRequest.getDeskNo());
        desk.setActive(Status.ACTIVE.getValue());
        desk.setCreateDate(Timestamp.from(Instant.now()));
        deskRepository.save(desk);

        return new BaseResponse();
    }

    public BaseResponse updateDesk (DeskCreateRequest deskCreateRequest) {
        Desk desk = deskRepository.findByDeskId(deskCreateRequest.getDeskId());
        Desk byDeskNo = deskRepository.findByDeskNo(deskCreateRequest.getDeskNo());

        if(deskCreateRequest.getDeskNo().equals(desk.getDeskNo())){
            desk.setDescription(deskCreateRequest.getDescription());
            deskRepository.save(desk);

            return new BaseResponse();
        }

        if(byDeskNo != null){
            throw new NotFoundException("DESK ALREADY EXISTS");
        }

        desk.setDeskNo(deskCreateRequest.getDeskNo());
        desk.setDescription(deskCreateRequest.getDescription());
        deskRepository.save(desk);

        return new BaseResponse();
    }

    @Transactional (rollbackOn = Exception.class)
    public BaseResponse deleteDesk (DeskDeleteRequest deskDeleteRequest) {
        Desk desk = deskRepository.findByDeskId(deskDeleteRequest.getDeskId());

        deskRepository.delete(desk);
        return new BaseResponse();
    }


    public BaseResponse activateDesk (String deskId){
        Desk desk = deskRepository.findByDeskId(deskId);

        if(desk == null){
            throw new NotFoundException("DESK NOT FOUND");
        }

        desk.setActive(Status.ACTIVE.getValue());
        deskRepository.save(desk);

        return new BaseResponse();
    }

    public BaseResponse deactivateDesk(String deskId){
        Desk desk = deskRepository.findByDeskId(deskId);

        if(desk == null){
            throw new NotFoundException("DESK NOT FOUND");
        }

        desk.setActive(Status.PASSIVE.getValue());
        deskRepository.save(desk);

        return new BaseResponse();
    }





}
