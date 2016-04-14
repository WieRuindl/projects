package commands;

import exceptions.MessageException;
import model.CommandsManager;
import entity.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "help")
public class Help implements Command {

    @Autowired
    private CommandsManager commandsManager;

    public void execute(Cursor cursor, List<String> args) throws MessageException {
        for (String key : commandsManager.getCommands().keySet()) {
            Command command = commandsManager.getCommand(key);
            System.out.println(key + " - " + command.getDescription());
        }
    }

    public String getDescription() {
        return "show info about available commands";
    }
}
