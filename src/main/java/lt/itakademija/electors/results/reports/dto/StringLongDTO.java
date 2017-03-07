package lt.itakademija.electors.results.reports.dto;

/**
 * Created by Pavel on 2017-03-01.
 */
public class StringLongDTO {
    String name;
    Long number;

    public StringLongDTO(String name, Long number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "StringLongDTO{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
