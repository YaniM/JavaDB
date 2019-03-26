package cardealer.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private Double price;
    private Integer quantity;
    private List<Car> cars;
    private Supplier supplier;

    public Part() {
        this.cars=new ArrayList<>();
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToMany(targetEntity = Car.class,mappedBy = "parts",fetch = FetchType.EAGER)
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @ManyToOne(targetEntity = Supplier.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
