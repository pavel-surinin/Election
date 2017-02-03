package lt.itakademija.electors.county;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.candidate.CandidateEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Entity
public class CountyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COUNTY_ID")
    private Long id;

    @Column(unique=true)
    @NotNull
    private String name;

    @OneToMany(mappedBy="county", cascade = CascadeType.ALL)
    private List<DistrictEntity> districts;

    @OneToMany(mappedBy="county", cascade = CascadeType.MERGE)
    private List<CandidateEntity> candidates;

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

    public List<DistrictEntity> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictEntity> districts) {
        this.districts = districts;
    }

    public List<CandidateEntity> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateEntity> candidates) {
        this.candidates = candidates;
    }
}
