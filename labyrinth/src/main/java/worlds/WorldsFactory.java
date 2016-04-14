package worlds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorldsFactory {

    private final Map<String, World> worlds;

    @Autowired
    public WorldsFactory(Map<String, World> worlds) {
        this.worlds = worlds;
    }

    public World getWorld(String name) {
        return worlds.get(name);
    }
}
