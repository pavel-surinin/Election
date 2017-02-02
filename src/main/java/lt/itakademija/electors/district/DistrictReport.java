package lt.itakademija.electors.district;

/**
 * Created by Pavel on 2017-01-16.
 */
public class DistrictReport {

    private DistrictEntity ent;
    private Long id;
    private String name;
    private String adress;
    private Long representativeId;
    private String representativeName;
    private Long countyId;
    private String countyName;
    private Long numberOfElectors;

    public DistrictReport(DistrictEntity ent) {
        this.id = ent.getId();
        this.name = ent.getName();
        this.adress = ent.getAdress();
        if (ent.getRepresentative() != null) {
            this.representativeId = ent.getRepresentative().getId();
            this.representativeName = ent.getRepresentative().getName();
        }
        if (ent.getCounty() != null) {
            this.countyId = ent.getCounty().getId();
            this.countyName = ent.getCounty().getName();
        }
        this.numberOfElectors = ent.getNumberOfElectors();
    }

    public Long getNumberOfElectors() {
        return numberOfElectors;
    }

    public void setNumberOfElectors(Long numberOfElectors) {
        this.numberOfElectors = numberOfElectors;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
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

    public Long getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(Long representativeId) {
        this.representativeId = representativeId;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }
}
