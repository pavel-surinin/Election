package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.I_AM_A_TEAPOT, reason="Party name already exists")
public class PartyNameCloneException extends RuntimeException {

    public PartyNameCloneException(String message) {
        super(message);
    }

    public PartyNameCloneException(String message, Throwable cause) {
        super(message, cause);
    }
}
