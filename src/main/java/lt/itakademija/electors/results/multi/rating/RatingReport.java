package lt.itakademija.electors.results.multi.rating;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.results.multi.ResultMultiEntity;

/**
 * Created by Pavel on 2017-02-13.
 */
public class RatingReport {

    private RatingEntity rating;
    private Long id;
    private CandidateReport candidate;
    private ResultMultiEntity multiResults;
    private Integer points;

    public RatingReport(RatingEntity rating) {
        this.id = rating.getId();
        this.candidate = new CandidateReport(rating.getCandidate());
        this.points = rating.getPoints();
    }

    public RatingReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateReport getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateReport candidate) {
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
