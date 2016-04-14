package model;

import commands.Command;
import exceptions.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CommandsManager {

    @Autowired
    private Map<String, Command> commands;

    public Command getCommand(String commandName) throws MessageException {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new MessageException("No such command");
        }
        return command;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
