package commands;

import exceptions.MessageException;
import entity.Cursor;
import model.Directory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component(value = "cd")
public class Move implements Command {

    public void execute(Cursor cursor, List<String> args) throws MessageException {

        if (args.size() != 1) {
            throw new MessageException("Incorrect command signature usage");
        }

        String target = args.get(0);

        if (".".equalsIgnoreCase(target)) {
            return;
        }

        if ("..".equalsIgnoreCase(target)) {
            cursor.moveUp();
            return;
        }

        Directory directory = cursor.getCurrentDirectory();
        Map<String, Directory> childes = directory.getChildes();
        Directory child = childes.get(target.toLowerCase());
        if (child != null) {
            cursor.moveTo(child);
            return;
        }

        throw new MessageException("No such directory");
    }

    public String getDescription() {
        return "move to chosen directory";
    }
}
