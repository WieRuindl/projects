package animationManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.newdawn.slick.Image;

import java.util.List;

@AllArgsConstructor
public class AnimationContainer {
    @Getter
    private final List<Image> images;
    @Getter
    private final double animationDuration;
}
