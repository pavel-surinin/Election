package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Gabriele on 2017-02-12.
 */

@ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="More votes registered than there is voters in the district")
public class TooManyVotersException extends RuntimeException{

    public TooManyVotersException(String message) {
        super(message);
    }

    public TooManyVotersException(String message, Throwable cause) {
        super(message, cause);
    }
}
