package lt.itakademija.electors.candidate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Pavel on 2017-01-12.
 */
public class CandidateReport {
    private CandidateEntity can;

    private Long id;

    private String name;

    private String surname;

    private String partijosPavadinimas;

    private Long partijosId;

    private String description;

    private Integer numberInParty;

    private Long countyId;

    private boolean isMultiList;

    private String birthDate;

    public CandidateReport() {
    }

    public CandidateReport(CandidateEntity candidateEntity) {
        this.setId(candidateEntity.getId());
        this.setName(candidateEntity.getName());
        this.setSurname(candidateEntity.getSurname());
        this.setDescription(candidateEntity.getDescription());
        this.setBirthDate(formatDateToString(candidateEntity.getBirthDate()));
        this.setNumberInParty(candidateEntity.getNumberInParty());
        this.setMultiList(candidateEntity.isMultiList());
        if (candidateEntity.getPartyDependencies() != null) {
            this.setPartijosId(candidateEntity.getPartyDependencies().getId());
            this.setPartijosPavadinimas(candidateEntity.getPartyDependencies().getName());
        }
        if (candidateEntity.getCounty() != null) {
            this.setCountyId(candidateEntity.getCounty().getId());
        }
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public boolean isMultiList() {
        return isMultiList;
    }

    public void setMultiList(boolean multiList) {
        isMultiList = multiList;
    }

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

    private String formatDateToString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateReport that = (CandidateReport) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
