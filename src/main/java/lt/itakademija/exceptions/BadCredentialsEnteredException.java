package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-03-07.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadCredentialsEnteredException extends RuntimeException {

    public BadCredentialsEnteredException(String message) {
        super(message);
    }

    public BadCredentialsEnteredException(String message, Throwable cause) {
        super(message, cause);
    }
}