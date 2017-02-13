package lt.itakademija.electors.results.single;

import lt.itakademija.electors.candidate.CandidateReport;

/**
 * Created by Pavel on 2017-02-13.
 */
public class ResultSingleReport {

    private ResultSingleEntity result;
    private Long id;
    private CandidateReport candidate;
    private Long votes;

    public ResultSingleReport(ResultSingleEntity result) {
        this.id = result.getId();
        this.candidate = new CandidateReport(result.getCandidate());
        this.votes = result.getVotes();
    }

    public ResultSingleReport() {
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

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
