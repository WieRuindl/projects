package enemies;

import gameManager.Character;
import interfaces.Logic;
import labyrinth.Cell;
import labyrinth.Direction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "mummyLogic")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Mummy extends Character implements Logic {

    private Direction keepDirection = Direction.values()[(int) (Math.random() * Direction.values().length)];
    private Point playerLastSawLocation;

    public Direction makeDecision(Cell[][] cells, Point location, Point playerLocation) {
        Direction dir = lookForPlayer(cells, location, playerLocation);
        if (dir != null) return dir;

        if (playerLastSawLocation != null && !location.equals(playerLastSawLocation)) {
            return keepDirection;
        }

        playerLastSawLocation = null;

        Map<Direction, Cell> nearestCells = getNearestCells(cells, location);

        List<Direction> availableDirections =
                nearestCells.keySet().stream()
                        .filter(direction -> nearestCells.get(direction) != Cell.WALL)
                        .collect(Collectors.toCollection(LinkedList::new));

        if (availableDirections.size() == 1) {
            keepDirection = availableDirections.get(0);
            return keepDirection;
        }

        availableDirections.remove(keepDirection.revert());
        int i = (int) (Math.random() * availableDirections.size());
        keepDirection = availableDirections.get(i);
        return keepDirection;
    }

    private Direction lookForPlayer(Cell[][] cells, Point location, Point playerLocation) {
        for (int i = 1; ; i++) {
            Point target = new Point(location.x + i * Direction.EAST.getDx(), location.y + i * Direction.EAST.getDy());
            if (cells[target.y][target.x] == Cell.WALL) {
                break;
            }
            if (target.equals(playerLocation)) {
                playerLastSawLocation = new Point(playerLocation);
                keepDirection = Direction.EAST;
                return keepDirection;
            }
        }
        for (int i = 1; ; i++) {
            Point target = new Point(location.x + i * Direction.WEST.getDx(), location.y + i * Direction.WEST.getDy());
            if (cells[target.y][target.x] == Cell.WALL) {
                break;
            }
            if (target.equals(playerLocation)) {
                playerLastSawLocation = new Point(playerLocation);
                keepDirection = Direction.WEST;
                return keepDirection;
            }
        }
        for (int i = 1; ; i++) {
            Point target = new Point(location.x + i * Direction.SOUTH.getDx(), location.y + i * Direction.SOUTH.getDy());
            if (cells[target.y][target.x] == Cell.WALL) {
                break;
            }
            if (target.equals(playerLocation)) {
                playerLastSawLocation = new Point(playerLocation);
                keepDirection = Direction.SOUTH;
                return keepDirection;
            }
        }
        for (int i = 1; ; i++) {
            Point target = new Point(location.x + i * Direction.NORTH.getDx(), location.y + i * Direction.NORTH.getDy());
            if (cells[target.y][target.x] == Cell.WALL) {
                break;
            }
            if (target.equals(playerLocation)) {
                playerLastSawLocation = new Point(playerLocation);
                keepDirection = Direction.NORTH;
                return keepDirection;
            }
        }
        return null;
    }
}
