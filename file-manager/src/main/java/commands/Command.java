package commands;

import exceptions.MessageException;
import entity.Cursor;

import java.util.List;

public interface Command {
    void execute(Cursor cursor, List<String> args) throws MessageException;
    String getDescription();
}
