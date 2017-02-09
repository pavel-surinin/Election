package lt.itakademija.electors.results.single;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.district.DistrictEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Pavel on 2017-02-02.
 */

@Entity
public class ResultSingleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID")
    private CandidateEntity candidate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private DistrictEntity district;

    @NotNull
    private Long votes;

    @NotNull
    private boolean isApproved = false;

    @NotNull
    private Date datePublished;

    public ResultSingleEntity() {
    }

    public ResultSingleEntity(CandidateEntity candidate, DistrictEntity district, Long votes, Date date) {
        this.candidate = candidate;
        this.district = district;
        this.votes = votes;
        this.datePublished = date;
    }

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

    public DistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }
}
