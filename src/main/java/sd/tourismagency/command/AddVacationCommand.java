package sd.tourismagency.command;

import org.springframework.stereotype.Component;
import sd.tourismagency.model.Cruise;
import sd.tourismagency.model.Stay;
import sd.tourismagency.model.Tour;
import sd.tourismagency.model.Vacation;
import sd.tourismagency.service.CruiseService;
import sd.tourismagency.service.StayService;
import sd.tourismagency.service.TourService;
import sd.tourismagency.service.VacationService;

public class AddVacationCommand {// implements Command {
    private Vacation vacation;
    private TourService tourService;
    private StayService stayService;
    private CruiseService cruiseService;

    public AddVacationCommand(Vacation vacation, TourService tourService, StayService stayService, CruiseService cruiseService) {
        this.vacation = vacation;
        this.tourService = tourService;
        this.stayService = stayService;
        this.cruiseService = cruiseService;
    }


   // @Override
    public void execute() {
        if (vacation instanceof Tour tour) {
            tourService.addTour(tour);
        } else if (vacation instanceof Cruise cruise) {
            cruiseService.addCruise(cruise);
        } else {
            Stay stay = (Stay) vacation;
            stayService.addStay(stay);
        }
    }

  //  @Override
    public void undo() {
    }
}
