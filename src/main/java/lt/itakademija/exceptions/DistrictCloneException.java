package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.CONFLICT, reason="District with this name and County is already registered")
public class DistrictCloneException extends RuntimeException {

    public DistrictCloneException(String message) {
        super(message);
    }

    public DistrictCloneException(String message, Throwable cause) {
        super(message, cause);
    }
}
