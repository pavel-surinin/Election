package lt.itakademija.electors.results.multi.rating;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pavel on 2017-02-10.
 */
@Entity
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RATING_ID")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID")
    private CandidateEntity candidate;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "RESULT_MULTI_ID")
    private ResultMultiEntity multiResults;

    @NotNull
    private Integer points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    public ResultMultiEntity getMultiResults() {
        return multiResults;
    }

    public void setMultiResults(ResultMultiEntity multiResults) {
        this.multiResults = multiResults;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
