package sd.tourismagency.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import sd.tourismagency.model.Attraction;
import sd.tourismagency.model.Stay;

import java.time.LocalDate;
import java.util.List;

public class VacationDto {
    private Long id;
    private String dtype;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private int availability;
    private String hotelName;
    private List<Long> attractions;
    private String guide;
    private String itinerary;
    private String boardingLocation;
    private String shipName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<Long> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Long> attractions) {
        this.attractions = attractions;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getBoardingLocation() {
        return boardingLocation;
    }

    public void setBoardingLocation(String boardingLocation) {
        this.boardingLocation = boardingLocation;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

}
