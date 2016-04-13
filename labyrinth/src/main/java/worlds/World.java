package worlds;

import enemiesSkins.Enemy;
import interfaces.WorldAccessory;
import labyrinth.Cell;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class World {

    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("xml-configurations/enemies.xml");

    @Value(value = "${world.name}")
    private String world;

    private List<Class<? extends Enemy>> enemies;

    private Animation fog;
    private Animation wall;
    private Animation floor;
    private Animation chest;

    @PostConstruct
    private void InitEnemiesForCurrentWorld() {
        enemies = new ArrayList<>();

        Reflections reflections = new Reflections("enemiesSkins");
        Set<Class<? extends Enemy>> enemies = reflections.getSubTypesOf(Enemy.class);
        for (Class<? extends Enemy> enemy : enemies) {
            WorldAccessory annotation = enemy.getAnnotation(WorldAccessory.class);
            if (annotation == null) {
                throw new RuntimeException("Each one Enemy class should be annotated with @WorldAccessory: "+enemies.getClass().getSimpleName());
            }
            String[] worlds = annotation.worlds();
            if (worlds == null || worlds.length == 0) {
                throw new RuntimeException("Enemy class should have at least 1 world name identifier: "+enemies.getClass().getSimpleName());
            }
            for (String world : worlds) {
                if (this.world.equals(world)) {
                    this.enemies.add(enemy);
                    break;
                }
            }
        }
    }

    public void initAnimation() throws SlickException, IOException {
        fog = initAnimation("images/worlds/" + world + "/fog", 1000);
        wall = initAnimation("images/worlds/" + world + "/wall", 1000);
        floor = initAnimation("images/worlds/" + world + "/floor", 1000);
        chest = initAnimation("images/worlds/" + world + "/chest", 1000);
    }

    private Animation initAnimation(String source, int duration) throws SlickException, IOException {
        File root = new ClassPathResource(source).getFile();
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
        return new Animation(images, duration);
    }

    public final Animation getAnimation(Cell cell) {
        switch (cell) {
            case EMPTY:
                return floor;
            case WALL:
                return wall;
            case FOG:
                return fog;
            case TREASURE:
                return chest;
            default:
                throw new RuntimeException("No such cell image =(");
        }
    }

    public final Enemy createEnemy() throws Exception {
        int i = (int) (Math.random() * enemies.size());
        Class<? extends Enemy> clazz = enemies.get(i);

        return CONTEXT.getBean(clazz);
    }
}
