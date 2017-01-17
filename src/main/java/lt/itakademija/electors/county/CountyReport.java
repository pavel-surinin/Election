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

    private Long id;

    private String name;

    public CountyReport(Long id, String name) {
        this.id = id;
        this.name = name;
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
