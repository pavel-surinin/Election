package lt.itakademija.electors.candidate;

import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.party.PartyEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Pavel on 2017-01-12.
 */
@Entity
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private Date birthDate;

    @ManyToOne
    @JoinColumn(nullable = true, name= "PARTY_ID")
    private PartyEntity partyDependencies;

    private String description;

    @ManyToOne
    @JoinColumn(nullable = true, name= "COUNTY_ID")
    private CountyEntity county;

    public Long getId() {
        return id;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public void setCounty(CountyEntity county) {
        this.county = county;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PartyEntity getPartyDependencies() {
        return partyDependencies;
    }

    public void setPartyDependencies(PartyEntity partyDependencies) {
        this.partyDependencies = partyDependencies;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
