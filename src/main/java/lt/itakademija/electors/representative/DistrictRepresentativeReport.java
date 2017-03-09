package lt.itakademija.electors.representative;

/**
 * Created by Pavel on 2017-01-16.
 */
public class DistrictRepresentativeReport {
    private DistrictRepresentativeEntity ent;
    private Long id;
    private String name;
    private String surname;
    private Long districtId;
    private String districtName;

    public DistrictRepresentativeReport() {
    }

    public DistrictRepresentativeReport(DistrictRepresentativeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.districtId = entity.getDistrict().getId();
        this.districtName = entity.getDistrict().getName();
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
