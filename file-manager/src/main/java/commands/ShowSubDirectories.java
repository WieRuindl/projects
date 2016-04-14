package commands;

import exceptions.MessageException;
import entity.Cursor;
import model.Directory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component(value = "ls")
public class ShowSubdirectories implements Command {

    public void execute(Cursor cursor, List<String> args) throws MessageException {
        Directory directory = cursor.getCurrentDirectory();
        Map<String, Directory> childes = directory.getChildes();

        if (directory.getParent() != null) {
            System.out.println(".");
            System.out.println("..");
        }

        for (Directory child : childes.values()) {
            System.out.println(child.getName());
        }
    }

    public String getDescription() {
        return "shows all subdirectories in the current one";
    }
}
