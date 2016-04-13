package worlds;

import characters.enemies.Enemy;
import labyrinth.Cell;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.*;

public abstract class World {

    private final List<Enemy> enemies;

    private Image fog;
    private Image wall;
    private Image floor;
    private Image chest;

    protected World(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void initImages() throws SlickException {
        String world = getClass().getSimpleName().toLowerCase();
        fog = new Image("images/worlds/" + world + "/fog.png");
        wall = new Image("images/worlds/" + world + "/wall.png");
        floor = new Image("images/worlds/" + world + "/floor.png");
        chest = new Image("images/worlds/" + world + "/chest.png");
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
        Class<? extends Enemy> clazz = enemies.get(i).getClass();

        return clazz.newInstance();
    }
}
