package showModes;

import interfaces.ShowMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShowModesFactory {

    private final Map<String, ShowMode> modes;

    @Autowired
    public ShowModesFactory(Map<String, ShowMode> modes) {
        this.modes = modes;
    }

    public ShowMode getMode(String name) {
        return modes.get(name);
    }
}
