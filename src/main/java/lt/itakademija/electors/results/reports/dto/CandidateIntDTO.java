package lt.itakademija.electors.results.reports.dto;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;

import java.util.Objects;

/**
 * Created by Pavel on 2017-02-13.
 */
public class CandidateIntDTO {
    private CandidateReport candidate;
    private Integer votes;

    public CandidateIntDTO(CandidateEntity candidate, Integer count) {
        this.candidate = new CandidateReport(candidate);
        this.votes = count;
    }

    public CandidateIntDTO(CandidateReport candidate, Integer count) {
        this.candidate = candidate;
        this.votes = count;
    }

    public CandidateIntDTO() {
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public CandidateReport getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateReport candidate) {
        this.candidate = candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateIntDTO that = (CandidateIntDTO) o;
        return Objects.equals(candidate, that.candidate) &&
                Objects.equals(votes, that.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, votes);
    }
}
