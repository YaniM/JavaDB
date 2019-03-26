package cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarDetailsDTO {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;
    @Expose
    private List<PartsDetailsDTO> partsDetails;


    public CarDetailsDTO() {

    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartsDetailsDTO> getPartsDetails() {
        return partsDetails;
    }

    public void setPartsDetails(List<PartsDetailsDTO> partsDetails) {
        this.partsDetails = partsDetails;
    }
}
