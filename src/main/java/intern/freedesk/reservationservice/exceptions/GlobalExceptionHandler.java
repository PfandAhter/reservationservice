package intern.freedesk.reservationservice.exceptions;

import intern.freedesk.reservationservice.api.response.BaseResponse;
import intern.freedesk.reservationservice.lib.constants.Constants;
import intern.freedesk.reservationservice.rest.service.interfaces.MapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MapperService mapperService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException e){
        log.error("Error: " + e);
        return ResponseEntity.internalServerError().body(createFailResponse(e.getMessage().split("HataMesaji")[1].substring(3,e.getMessage().split("HataMesaji")[1].length()-3)));
    }

    //:#{#c.code}

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) //responsebody
    public ResponseEntity<BaseResponse> handleException (NotFoundException e){
        log.error("Error: " + e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponse(e.getMessage()));
    }


    //no_content


    private BaseResponse createFailResponse(String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResultCode(Constants.FAILED);
        baseResponse.setMessage(message);
        return baseResponse;
    }

}
