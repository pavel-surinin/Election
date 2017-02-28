package lt.itakademija.electors.county;

/**
 * Created by Pavel on 2017-01-17.
 */
public class CountyReport {

    private CountyEntity countyEntity;

    private Long id;

    private String name;

    private Integer candidatesCount;

    private Integer districtsCount;

    private Long votersCount;

    public CountyReport(CountyEntity ent) {
        this.id = ent.getId();
        this.name = ent.getName();
        if (ent.getCandidates() != null){
            this.candidatesCount = ent.getCandidates().size();
        } else {
            this.candidatesCount = 0;
        }
        if (ent.getDistricts() != null){
            this.districtsCount = ent.getDistricts().size();
        } else {
            this.districtsCount =0;
        }
        this.votersCount = ent.getDistricts().stream().mapToLong(d -> d.getNumberOfElectors()).sum();
    }

    public CountyReport() {
    }

    public Long getVotersCount() {
        return votersCount;
    }

    public void setVotersCount(Long votersCount) {
        this.votersCount = votersCount;
    }

    public CountyEntity getCountyEntity() {
        return countyEntity;
    }

    public void setCountyEntity(CountyEntity countyEntity) {
        this.countyEntity = countyEntity;
    }

    public Integer getDistrictsCount() {
        return districtsCount;
    }

    public void setDistrictsCount(Integer districtsCount) {
        this.districtsCount = districtsCount;
    }

    public Integer getCandidatesCount() {
        return candidatesCount;
    }

    public void setCandidatesCount(Integer candidatesCount) {
        this.candidatesCount = candidatesCount;
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