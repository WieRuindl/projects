package worlds;

import enemiesSkins.Enemy;
import interfaces.WorldAccessory;
import labyrinth.Cell;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class World {

    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("xml-configurations/enemies.xml");

    @Value(value = "${world.name}")
    private String world;

    private List<Class<? extends Enemy>> enemies;

    private Image fog;
    private Image wall;
    private Image floor;
    private Image chest;

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

    public void initImages() throws SlickException {
        fog = new Image("images/worlds/" + world + "/fog/fog.png");
        wall = new Image("images/worlds/" + world + "/wall/wall.png");
        floor = new Image("images/worlds/" + world + "/floor/floor.png");
        chest = new Image("images/worlds/" + world + "/chest/chest.png");
    }

    public final Image getAnimation(Cell cell) {
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
