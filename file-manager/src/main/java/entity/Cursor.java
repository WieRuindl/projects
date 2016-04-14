package entity;

import model.Directory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Cursor {
    private Directory currentDirectory = initRootDirectory();

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void moveTo(Directory directory) {
        currentDirectory = directory;
    }

    public void moveUp() {
        Directory parent = currentDirectory.getParent();
        if (parent != null) {
            currentDirectory = parent;
        }
    }

    private Directory initRootDirectory() {
        String property = System.getProperty("user.home");
        File file = new File(property);
        Directory root = new Directory(file.getName());
        File[] files = file.listFiles();
        if (files != null) {
            for (File child : files) {
                if (child == null || !child.isDirectory()) {
                    continue;
                }

                Directory directory = new Directory(child.getName());
                root.addDirectory(directory);
                File[] subFiles = child.listFiles();
                if (subFiles != null) {

                    for (File subChild : subFiles) {
                        if (subChild == null || !subChild.isDirectory()) {
                            continue;
                        }
                        Directory subDirectory = new Directory(subChild.getName());
                        directory.addDirectory(subDirectory);
                    }
                }
            }
        }

        return root;
    }
}
