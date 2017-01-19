package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateReport;

import java.util.List;

import lt.itakademija.electors.candidate.CandidateReport;

/**
 * Created by Pavel on 2017-01-12.
 */
public class PartyReport {

    private Long id;

    private String name;

    private List<CandidateReport> members;

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

    public List<CandidateReport> getMembers() {
        return members;
    }

    public void setMembers(List<CandidateReport> members) {
        this.members = members;
    }
}
