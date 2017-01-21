package lt.itakademija.electors.district;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.itakademija.electors.RinkejasEntity;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Entity
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DISTRICT_ID")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String adress;

    @OneToMany
    private List<RinkejasEntity> electors;

    @ManyToOne
    @JoinColumn(nullable = true, name= "COUNTY_ID")
    private CountyEntity county;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "district")
    private DistrictRepresentativeEntity representative;

    public DistrictRepresentativeEntity getRepresentative() {
        return representative;
    }

    public void setRepresentative(DistrictRepresentativeEntity representative) {
        this.representative = representative;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public void setCounty(CountyEntity county) {
        this.county = county;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<RinkejasEntity> getElectors() {
        return electors;
    }

    public void setElectors(List<RinkejasEntity> electors) {
        this.electors = electors;
    }
}
