package showModes;

import interfaces.ShowMode;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShowModesFactory {
    @Autowired
    private Map<String, ShowMode> modes;

    public ShowMode getMode(String name) {
        return modes.get(name);
    }
}
