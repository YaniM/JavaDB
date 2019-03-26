package cardealer.domain.dtos;

import cardealer.domain.entities.Part;
import com.google.gson.annotations.Expose;

import java.util.List;

public class LocalSuppliersDTO {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private Integer partsCount;

    public LocalSuppliersDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
