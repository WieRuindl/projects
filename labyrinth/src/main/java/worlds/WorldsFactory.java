package worlds;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorldsFactory {
    @Autowired
    private Map<String, World> worlds;

    public World getWorld(String name) {
        return worlds.get(name);
    }
}
