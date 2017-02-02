package lt.itakademija.electors.results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Pavel on 2017-02-02.
 */
@Service
public class ResultSingleService {

    @Autowired
    ResultSingleRepository repository;

    @Transactional
    public String save(List<ResultSingleEntity> results) {
     results.stream().forEach(result -> repository.save(result));
     return "Votes registered";
    }
}
