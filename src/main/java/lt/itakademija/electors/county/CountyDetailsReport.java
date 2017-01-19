package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.district.DistrictReport;

import java.util.List;

/**
 * Created by Pavel on 2017-01-19.
 */
public class CountyDetailsReport {

    private Long id;

    private String name;

    private List<DistrictReport> districts;

    private List<CandidateReport> candidates;

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

    public List<DistrictReport> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictReport> districts) {
        this.districts = districts;
    }

    public List<CandidateReport> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateReport> candidates) {
        this.candidates = candidates;
    }
}
