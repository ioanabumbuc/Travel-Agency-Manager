package sd.tourismagency.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue(value = "STAY")
public class Stay extends Vacation {
    private String hotelName;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Attraction> attractions;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
