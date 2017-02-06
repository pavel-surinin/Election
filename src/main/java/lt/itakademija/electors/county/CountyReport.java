package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.party.PartyEntity;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pavel on 2017-01-17.
 */
public class CountyReport {

    private CountyEntity countyEntity;

    private Long id;

    private String name;

    private Integer candidatesCount;

    public CountyReport(CountyEntity ent) {
        this.id = ent.getId();
        this.name = ent.getName();
        if (ent.getCandidates() != null){
            this.candidatesCount = ent.getCandidates().size();
        } else {
            this.candidatesCount = 0;
        }
    }

    public CountyReport() {
    }

    public Integer getCandidatesCount() {
        return candidatesCount;
    }

    public void setCandidatesCount(Integer candidatesCount) {
        this.candidatesCount = candidatesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}