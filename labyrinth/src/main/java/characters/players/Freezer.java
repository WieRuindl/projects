package characters.players;

import characters.enemies.Enemy;
import labyrinth.Cell;
import labyrinth.Direction;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class Freezer extends Player {
    public void uniqueAction(Cell[][] cells, List<Enemy> enemies) {
        Point north = new Point(location.x + Direction.NORTH.getDx(), location.y + Direction.NORTH.getDy());
        Point south = new Point(location.x + Direction.SOUTH.getDx(), location.y + Direction.SOUTH.getDy());
        Point west = new Point(location.x + Direction.WEST.getDx(), location.y + Direction.WEST.getDy());
        Point east = new Point(location.x + Direction.EAST.getDx(), location.y + Direction.EAST.getDy());

        for (Enemy enemy : enemies) {
            if (enemy.getLocation().equals(north) || enemy.getLocation().equals(south) ||
                    enemy.getLocation().equals(west) || enemy.getLocation().equals(east)) {
                int delay = enemy.getDelay();
                if (delay >= 0) {
                    enemy.setDelay(delay - 3000);
                }
            }
        }
    }

    protected int getAnimationDuration() {
        return 1000;
    }

    public String getDescription() {
        return "Mystical man, studied to the forbidden magic of Necronomicon, available to freeze his enemies";
    }
}
