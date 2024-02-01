package sd.tourismagency.command;

import sd.tourismagency.model.Vacation;

public interface Command {
    Vacation execute();
    void undo();
}
