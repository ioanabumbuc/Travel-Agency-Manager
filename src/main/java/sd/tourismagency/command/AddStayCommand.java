package sd.tourismagency.command;

import sd.tourismagency.model.Stay;
import sd.tourismagency.service.StayService;

public class AddStayCommand implements Command {
    private final Stay stay;
    private final StayService stayService;

    public AddStayCommand(Stay stay, StayService stayService) {
        this.stay = stay;
        this.stayService = stayService;
    }

    @Override
    public Stay execute() {
        return stayService.addStay(stay);
    }

    @Override
    public void undo() {

    }
}
