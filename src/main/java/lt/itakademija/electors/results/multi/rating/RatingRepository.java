package lt.itakademija.electors.results.multi.rating;

import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-02-22.
 */
@Repository
public class RatingRepository {

    @Autowired
    EntityManager em;

    public List<CandidateIntDTO> findByPartyGroupByCandidate(PartyEntity party) {
        System.out.println("findByPartyGroupByCandidate " + party.getName());
        List list = em
                .createQuery("SELECT r.candidate, SUM(r.points) " +
                        "FROM RatingEntity r JOIN r.multiResults mr " +
                        "GROUP BY r.candidate HAVING mr.party =:party")
                .setParameter("party", party)
                .getResultList();
        return list;
    }

    public List<RatingEntity> findByPartyCandidate(PartyEntity p) {
        return em
                .createQuery("SELECT r FROM RatingEntity r JOIN r.multiResults mr WHERE mr.party =:party" )
                .setParameter("party", p)
                .getResultList();
    }
}
