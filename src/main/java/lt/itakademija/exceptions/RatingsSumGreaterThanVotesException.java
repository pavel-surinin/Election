package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-03-10.
 */
@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class RatingsSumGreaterThanVotesException extends RuntimeException {

    public RatingsSumGreaterThanVotesException(String message){
        super(message);
    }

    public RatingsSumGreaterThanVotesException(String message, Throwable cause) {
        super(message, cause);
    }
}
