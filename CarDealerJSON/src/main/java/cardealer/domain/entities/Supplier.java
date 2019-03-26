package cardealer.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "suppliers")
public class Supplier extends BaseEntity{
    private String name;
    private Boolean isImporter;

    public Supplier() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_imported")
    public Boolean isImported() {
        return isImporter;
    }

    public void setImported(Boolean imported) {
        isImporter = imported;
    }


}
