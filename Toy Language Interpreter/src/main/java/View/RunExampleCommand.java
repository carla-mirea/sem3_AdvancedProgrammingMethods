package View;

import Controller.Controller;
import Model.Exceptions.MyException;

import java.io.IOException;

public class RunExampleCommand extends Command{
    Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (MyException | IOException  | InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
