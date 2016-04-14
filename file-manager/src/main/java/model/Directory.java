package model;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private String name;
    private String path;
    private Directory parent;
    private Map<String, Directory> childes;

    public Directory(String name) {
        this.name = name;
        this.path = name + "/";
        this.childes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Directory> getChildes() {
        return childes;
    }

    public void addDirectory(Directory directory) {
        directory.parent = this;
        directory.path = this.path + directory.name + "/";
        childes.put(directory.getName().toLowerCase(), directory);
    }

    public void removeDirectory(String name) {
        childes.remove(name);
    }

    public String getPath() {
        return path;
    }

    public Directory getParent() {
        return parent;
    }
}
