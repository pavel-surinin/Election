package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.district.DistrictReport;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-19.
 */
@Component
public class CountyDetailsReport {

    private CountyEntity ent;

    private Long id;

    private String name;

    private List<DistrictReport> districts;

    private List<CandidateReport> candidates;

    public CountyDetailsReport(CountyEntity ent) {
        this.id = ent.getId();
        this.name = ent.getName();
        this.districts = ent.getDistricts().stream().map(d -> new DistrictReport(d)).collect(Collectors.toList());
        this.candidates = ent.getCandidates().stream().map(c -> new CandidateReport(c)).collect(Collectors.toList());
    }

    public CountyDetailsReport() {
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
