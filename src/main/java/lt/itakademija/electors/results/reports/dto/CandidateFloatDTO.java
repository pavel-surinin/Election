package lt.itakademija.electors.results.reports.dto;

import lt.itakademija.electors.candidate.CandidateEntity;

/**
 * Created by Pavel on 2017-02-13.
 */
public class CandidateFloatDTO {
    private CandidateEntity candidate;
    private Float percent;

    public CandidateFloatDTO(CandidateEntity candidate, Float percent) {
        this.candidate = candidate;
        this.percent = percent;
    }

    public CandidateFloatDTO() {
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
