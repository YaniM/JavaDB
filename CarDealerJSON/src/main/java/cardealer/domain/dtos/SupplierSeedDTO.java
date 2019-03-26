package cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class SupplierSeedDTO {
    @Expose
    private String name;
    @Expose
    private Boolean isImporter;

    public SupplierSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isImported() {
        return isImporter;
    }

    public void setImported(Boolean imported) {
        isImporter = imported;
    }
}
