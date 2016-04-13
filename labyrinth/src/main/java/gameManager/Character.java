package gameManager;

import labyrinth.Cell;
import labyrinth.Direction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Character {
    @Getter
    protected Animation animation;

    @Setter
    protected Cell[][] cells;

    @Getter
    @Setter
    protected Point location;

    public void initAnimation() throws SlickException, IOException {
        File root = new ClassPathResource(getSource()).getFile();
        File[] files = root.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("Directory does not exist or empty: " + root);
        }
        Image[] images = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getName().endsWith(".png")) {
                throw new RuntimeException("Found not *.png file: " + files[i].getPath());
            }
            images[i] = new Image(files[i].getPath());
        }
        animation = new Animation(images, getAnimationDuration());
    }

    protected abstract String getSource();
    protected abstract int getAnimationDuration();
}
