package pl.school.directo.common.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.school.directo.common.Enums.ResponseCodeEnums;

@Component
public class ResponseUtils {
    public static HttpStatus mapHttpStatus(ResponseCodeEnums responseCodeEnums) {
        switch (responseCodeEnums) {
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
            case SUCCESS_LOGIN:
                return HttpStatus.OK;
            case FAILED_LOGIN:
                return HttpStatus.BAD_REQUEST;
            case METHOD_ERROR:
                return HttpStatus.BAD_REQUEST;
            case BLOCKED:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.BAD_REQUEST;
        }
    }

    public static ResponseEntity<String> generateResponseEntity(ResponseCodeEnums responseCodeEnum){
        return ResponseEntity.status(ResponseUtils.mapHttpStatus(responseCodeEnum)).body(responseCodeEnum.name());
    }
}
