package worlds;

import interfaces.Animation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Texture implements Animation {

    @Getter
    private final String source;
    @Getter
    private final double animationDuration;
}
