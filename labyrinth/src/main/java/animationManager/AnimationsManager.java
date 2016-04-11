package animationManager;

import interfaces.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class AnimationsManager {
    private Map<String, AnimationContainer> animations = new HashMap<>();

    public AnimationContainer getAnimationContainer(Animation entity) {

        AnimationContainer container = animations.get(entity.getSource());

        if (container == null) {
            try {
                container = initImages(entity);
                animations.put(entity.getSource(), container);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return container;
    }

    private AnimationContainer initImages(Animation entity) throws IOException, SlickException {
        String source = entity.getSource();
        List<Image> images = new ArrayList<>();

        File folder = new ClassPathResource(source).getFile();
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("No such directory or no files in it: " + folder.getName());
        }
        for (File file : files) {
            String name = file.getName();
            if (!name.endsWith(".png")) {
                throw new RuntimeException("Found not *.png image in " + source + "/: " + name);
            }
            Image image = new Image(source + "/" + name);
            images.add(image);
        }

        return new AnimationContainer(images, entity.getAnimationDuration());
    }
}
