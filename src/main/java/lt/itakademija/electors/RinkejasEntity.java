package lt.itakademija.electors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Pavel on 2017-01-12.
 */
@Entity
public class RinkejasEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String vardas;

    @NotNull
    private String pavarde;

    @NotNull
    private Long asmensKodas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public Long getAsmensKodas() {
        return asmensKodas;
    }

    public void setAsmensKodas(Long asmensKodas) {
        this.asmensKodas = asmensKodas;
    }
}
