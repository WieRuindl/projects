package entity;

import commands.Command;
import exceptions.MessageException;
import model.CommandsManager;
import model.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Model {

    @Autowired
    private Cursor cursor;

    @Autowired
    private CommandsManager commandsManager;

    public void execute(String commandName, List<String> args) {

        try {
            Command command = commandsManager.getCommand(commandName);
            command.execute(cursor, args);
        } catch (MessageException e) {
            System.err.println(e.getMessage());
        }

    }
}
