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
    private Integer spoiledMulti;
    private Integer spoiledSingle;
    private boolean isResultSingleRegistered = false;
    private boolean isResultMultiRegistered = false;
    private boolean isResultSingleApproved = false;
    private boolean isResultMultiApproved = false;

    public DistrictReport() {
    }

    public DistrictReport(DistrictEntity ent) {
        this.id = ent.getId();
        this.name = ent.getName();
        this.adress = ent.getAdress();
        if (ent.getRepresentative() != null) {
            this.representativeId = ent.getRepresentative().getId();
            this.representativeName = ent.getRepresentative().getName() + " " + ent.getRepresentative().getSurname();
        }
        if (ent.getCounty() != null) {
            this.countyId = ent.getCounty().getId();
            this.countyName = ent.getCounty().getName();
        }
        this.numberOfElectors = ent.getNumberOfElectors();
        if (ent.getResultSingleEntity() != null) {
            this.isResultSingleRegistered = ent.getResultSingleEntity().size() != 0;
            this.isResultSingleApproved = ent.getResultSingleEntity()
                    .stream()
                    .filter(res -> res.isApproved() == true)
                    .toArray().length != 0;
        }
        if (ent.getResultMultiEntity() != null) {
            this.isResultMultiRegistered = ent.getResultMultiEntity().size() != 0;
            this.isResultMultiApproved = ent.getResultMultiEntity()
                    .stream()
                    .filter(res -> res.isApproved() == true)
                    .toArray().length != 0;
        }
        this.spoiledMulti = ent.getSpoiledMulti();
        this.spoiledSingle = ent.getSpoiledSingle();
    }

    public Integer getSpoiledMulti() {
        return spoiledMulti;
    }

    public void setSpoiledMulti(Integer spoiledMulti) {
        this.spoiledMulti = spoiledMulti;
    }

    public Integer getSpoiledSingle() {
        return spoiledSingle;
    }

    public void setSpoiledSingle(Integer spoiledSingle) {
        this.spoiledSingle = spoiledSingle;
    }

    public boolean isResultSingleApproved() {
        return isResultSingleApproved;
    }

    public void setResultSingleApproved(boolean resultSingleApproved) {
        isResultSingleApproved = resultSingleApproved;
    }

    public boolean isResultMultiApproved() {
        return isResultMultiApproved;
    }

    public void setResultMultiApproved(boolean resultMultiApproved) {
        isResultMultiApproved = resultMultiApproved;
    }

    public boolean isResultSingleRegistered() {
        return isResultSingleRegistered;
    }

    public void setResultSingleRegistered(boolean resultSingleRegistered) {
        isResultSingleRegistered = resultSingleRegistered;
    }

    public boolean isResultMultiRegistered() {
        return isResultMultiRegistered;
    }

    public void setResultMultiRegistered(boolean resultMultiRegistered) {
        isResultMultiRegistered = resultMultiRegistered;
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
