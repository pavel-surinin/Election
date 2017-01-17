package lt.itakademija.electors.representative;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.itakademija.electors.district.DistrictEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pavel on 2017-01-16.
 */
@Entity
public class DistrictRepresentativeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @OneToOne
    @JoinColumn(name = "DISTRICT_ID")
    private DistrictEntity district;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }
}
