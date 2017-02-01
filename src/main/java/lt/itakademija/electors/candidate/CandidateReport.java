package lt.itakademija.electors.candidate;

import java.util.Date;

/**
 * Created by Pavel on 2017-01-12.
 */
public class CandidateReport {
    private Long id;

    private String name;

    private String surname;

    private Date birthDate;

    private String partijosPavadinimas;

    private Long partijosId;

    private String description;

    private Integer numberInParty;
 
	public Integer getNumberInParty() {
        return numberInParty;
    }

    public void setNumberInParty(Integer numberInParty) {
        this.numberInParty = numberInParty;
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

    public String getPartijosPavadinimas() {
        return partijosPavadinimas;
    }

    public void setPartijosPavadinimas(String partijosPavadinimas) {
        this.partijosPavadinimas = partijosPavadinimas;
    }

    public Long getPartijosId() {
        return partijosId;
    }

    public void setPartijosId(Long partijosId) {
        this.partijosId = partijosId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
}
