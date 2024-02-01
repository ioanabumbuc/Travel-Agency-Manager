package sd.tourismagency.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CRUISE")
public class Cruise extends Vacation {
    private String boardingLocation;
    private String shipName;

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

    public String toString() {
        return "Cruise " + getDestination() + " " + getBoardingLocation() + " " + getShipName();
    }


}
