package lt.itakademija.electors.results.reports.dto;

import lt.itakademija.electors.candidate.CandidateEntity;

/**
 * Created by Pavel on 2017-02-13.
 */
public class CandidateIntDTO {
    private CandidateEntity candidate;
    private Integer count;

    public CandidateIntDTO(CandidateEntity candidate, Integer count) {
        this.candidate = candidate;
        this.count = count;
    }

    public CandidateIntDTO() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }
}
