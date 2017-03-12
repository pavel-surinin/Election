package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pavel on 2017-03-10.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class RatingBiggerThanVotesException extends RuntimeException{

    public RatingBiggerThanVotesException(String message){
        super(message);
    }

    public RatingBiggerThanVotesException(String message, Throwable cause) {
        super(message, cause);
    }
}
