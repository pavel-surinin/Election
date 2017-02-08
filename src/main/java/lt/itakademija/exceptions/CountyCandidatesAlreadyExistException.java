package lt.itakademija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by VytautasL on 2/8/2017.
 */
@ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="County Candidates Already Exist in this County")
public class CountyCandidatesAlreadyExistException extends RuntimeException{

//    public String CountyCandidatesAlreadyExistException (String message){ super(message); }

    public CountyCandidatesAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
