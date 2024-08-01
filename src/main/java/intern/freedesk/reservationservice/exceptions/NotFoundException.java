package intern.freedesk.reservationservice.exceptions;

import lombok.Getter;

public class NotFoundException extends RuntimeException{

    @Getter
    private String message;

    @Getter
    private String errorCode;

    public NotFoundException(){
        super();
        this.message = null;
        this.errorCode = null;
    }

    public NotFoundException(String message){
        super();
        this.message = message;
        this.errorCode = null;
    }

    public NotFoundException(String message, String errorCode){
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

}
