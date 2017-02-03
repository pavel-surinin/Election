package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class PartyDoesNotExistException extends RuntimeException {

    public PartyDoesNotExistException(String message) {
        super(message);
    }

    public PartyDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
