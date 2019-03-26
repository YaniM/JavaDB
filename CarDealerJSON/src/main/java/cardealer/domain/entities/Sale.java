package cardealer.domain.entities;

import javax.persistence.*;

@Entity(name = "sales")
public class Sale extends BaseEntity{
    private Double discountPercentage;
    private Car car;
    private Customer customer;

    public Sale() {
    }

    @Column(name = "discount")
    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @OneToOne(targetEntity = Car.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @OneToOne(targetEntity = Customer.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
