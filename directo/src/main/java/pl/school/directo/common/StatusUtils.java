package pl.school.directo.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StatusUtils {
    public static HttpStatus mapHttpStatus(ResponseCode responseCode) {
        switch (responseCode) {
            case SUCCESS_CREATED:
                return HttpStatus.CREATED;
            case SUCCESS_MODIFIED:
                return HttpStatus.OK;
            case SUCCESS_REMOVED:
                return HttpStatus.OK;
            case CONFLICT_ID_EXISTS:
                return HttpStatus.BAD_REQUEST;
            case CONFLICT_ID_EXISTS_MODIFICATION:
                return HttpStatus.BAD_REQUEST;
            case VALIDATION_ERROR:
                return HttpStatus.BAD_REQUEST;
            case OBJ_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case FAILED_REMOVAL:
                return HttpStatus.BAD_REQUEST;
            case FAILED_MODIFICATION:
                return HttpStatus.BAD_REQUEST;
            case FAILED_CREATION:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.BAD_REQUEST;
        }
    }
}
