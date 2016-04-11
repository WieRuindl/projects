package players;

import enemiesSkins.Enemy;
import interfaces.UniqueAction;
import labyrinth.Cell;
import labyrinth.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class Magician extends Player {

    public double getAnimationDuration() {
        return 1;
    }

    private Point createdWallLocation;

    public void uniqueAction(Cell[][] cells, List<Enemy> enemies) {
        int x = location.x;
        int y = location.y;

        int nearestWallsCount =
                (cells[y + Direction.NORTH.getDy()][x + Direction.NORTH.getDx()] == Cell.WALL ? 1 : 0) +
                        (cells[y + Direction.SOUTH.getDy()][x + Direction.SOUTH.getDx()] == Cell.WALL ? 1 : 0) +
                        (cells[y + Direction.EAST.getDy()][x + Direction.EAST.getDx()] == Cell.WALL ? 1 : 0) +
                        (cells[y + Direction.WEST.getDy()][x + Direction.WEST.getDx()] == Cell.WALL ? 1 : 0);

        if (nearestWallsCount == 3) {
            createWall(cells, location, enemies, Direction.NORTH);
            createWall(cells, location, enemies, Direction.SOUTH);
            createWall(cells, location, enemies, Direction.EAST);
            createWall(cells, location, enemies, Direction.WEST);
        }

        if (nearestWallsCount == 4) {
            if (createdWallLocation != null) {
                cells[createdWallLocation.y][createdWallLocation.x] = Cell.EMPTY;
                createdWallLocation = null;
            }
        }
    }

    private void createWall(Cell[][] cells, Point location, List<Enemy> enemies, Direction direction) {
        int x = location.x;
        int y = location.y;

        if (cells[y + direction.getDy()][x + direction.getDx()] == Cell.EMPTY) {
            createdWallLocation = new Point(location.x + direction.getDx(), location.y + direction.getDy());
            for (Enemy enemy : enemies) {
                if (enemy.getLocation().equals(createdWallLocation)) {
                    return;
                }
            }
            cells[createdWallLocation.y][createdWallLocation.x] = Cell.WALL;
        }
    }

    public String getDescription() {
        return "Wise hero, who can create powerful illusions to confusing his enemies";
    }
}

