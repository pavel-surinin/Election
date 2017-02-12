package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.party.PartyEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@Entity
public class ResultMultiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PARTY_ID")
    private PartyEntity party;

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

    public ResultMultiEntity() {
    }

    public ResultMultiEntity(PartyEntity party, DistrictEntity district, Long votes, Date date) {
        this.party = party;
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

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
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
