package sd.tourismagency.command;

import sd.tourismagency.model.Tour;
import sd.tourismagency.service.TourService;

public class AddTourCommand implements Command{
    private final Tour tour;
    private final TourService tourService;

    public AddTourCommand(Tour tour, TourService tourService) {
        this.tour = tour;
        this.tourService = tourService;
    }

    @Override
    public Tour execute() {
        return tourService.addTour(tour);
    }

    @Override
    public void undo() {

    }
}
