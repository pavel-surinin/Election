package lt.itakademija.electors.results.multi.rating;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return Objects.equals(candidate.getId(), that.candidate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate.getId());
    }
}
