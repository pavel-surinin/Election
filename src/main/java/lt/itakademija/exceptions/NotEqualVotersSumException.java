package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-01-26.
 */
@ResponseStatus(value= HttpStatus.EXPECTATION_FAILED)
public class NotEqualVotersSumException extends RuntimeException {

    public NotEqualVotersSumException(String message) {
        super(message);
    }

    public NotEqualVotersSumException(String message, Throwable cause) {
        super(message, cause);
    }
}
