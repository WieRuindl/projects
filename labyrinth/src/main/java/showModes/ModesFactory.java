package showModes;

import interfaces.ShowMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import players.Player;

import java.util.Map;

@Component
public class ModesFactory {
    @Autowired
    private Map<String, ShowMode> modes;

    public ShowMode getMode(String name) {
        return modes.get(name);
    }
}
