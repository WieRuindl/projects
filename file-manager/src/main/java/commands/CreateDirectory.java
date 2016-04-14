package commands;

import exceptions.MessageException;
import entity.Cursor;
import model.Directory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component(value = "mkdir")
public class CreateDirectory implements Command {

    public void execute(Cursor cursor, List<String> args) throws MessageException {
        if (args.size() != 1) {
            throw new MessageException("Incorrect command signature usage");
        }

        String newDirectoryName = args.get(0);

        Directory directory = cursor.getCurrentDirectory();
        Map<String, Directory> childes = directory.getChildes();

        Directory newDirectory = childes.get(newDirectoryName);
        if (newDirectory != null) {
            throw new MessageException("Directory with the same name already exists");
        }

        newDirectory = new Directory(newDirectoryName);
        directory.addDirectory(newDirectory);
    }

    public String getDescription() {
        return "creates directory";
    }
}
