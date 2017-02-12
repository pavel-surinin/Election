package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Gabriele on 2017-02-12.
 */
@ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="Negative value of votes number")
public class NegativeVoteNumberException extends RuntimeException{

    public NegativeVoteNumberException(String message) {
        super(message);
    }

    public NegativeVoteNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
