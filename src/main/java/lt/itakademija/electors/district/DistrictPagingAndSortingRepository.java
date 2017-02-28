package lt.itakademija.electors.district;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by Pavel on 2017-02-28.
 */
@Repository
public interface DistrictPagingAndSortingRepository<T, Long extends Serializable> extends CrudRepository<DistrictEntity, Long> {
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}
