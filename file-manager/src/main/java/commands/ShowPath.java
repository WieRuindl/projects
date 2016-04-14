package commands;

import exceptions.MessageException;
import entity.Cursor;
import model.Directory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "pwd")
public class ShowPath implements Command {

    public void execute(Cursor cursor, List<String> args) throws MessageException {
        Directory directory = cursor.getCurrentDirectory();
        System.out.println(directory.getPath());
    }

    public String getDescription() {
        return "shows path from root to current directory";
    }
}
