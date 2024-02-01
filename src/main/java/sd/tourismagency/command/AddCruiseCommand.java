package sd.tourismagency.command;

import sd.tourismagency.model.Cruise;
import sd.tourismagency.service.CruiseService;

public class AddCruiseCommand implements Command {

    private final Cruise cruise;
    private final CruiseService cruiseService;

    public AddCruiseCommand(Cruise cruise, CruiseService cruiseService) {
        this.cruise = cruise;
        this.cruiseService = cruiseService;
    }

    @Override
    public Cruise execute() {
        return cruiseService.addCruise(cruise);
    }

    @Override
    public void undo() {

    }
}
