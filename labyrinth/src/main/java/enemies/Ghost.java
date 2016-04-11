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

@Component(value = "ghostLogic")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Ghost extends Character implements Logic {

    private Direction keepDirection = Direction.values()[(int) (Math.random() * Direction.values().length)];

    public Direction makeDecision(Cell[][] cells, Point location, Point playerLocation) {

        Map<Direction, Cell> nearestCells = getNearestCells(cells, location);

        int height = cells.length;
        int width = cells[0].length;

        if (nearestCells.get(keepDirection) == Cell.WALL) {
            if (location.x - 2 >= 0 && location.x + 2 < width && location.y - 2 >= 0 && location.y + 2 < height) {
                if (cells[location.y + keepDirection.getDy() * 2][location.x + keepDirection.getDx() * 2] == Cell.EMPTY) {
                    if ((int) (Math.random() * 2) == 0) {
                        return keepDirection;
                    }
                }
            }
        }

        List<Direction> availableDirections =
                nearestCells.keySet().stream()
                        .filter(direction -> nearestCells.get(direction) != Cell.WALL)
                        .collect(Collectors.toCollection(LinkedList::new));

        if (availableDirections.size() == 0) {
            if (location.x - 2 < 0 || location.x + 2 >= width || location.y - 2 < 0 || location.y + 2 >= height) {
                keepDirection = keepDirection.revert();
            }
            return keepDirection;
        }

        if (availableDirections.size() == 1) {
            keepDirection = availableDirections.get(0);
            return keepDirection;
        }

        availableDirections.remove(keepDirection.revert());

        int i = (int) (Math.random() * availableDirections.size());
        keepDirection = availableDirections.get(i);
        return keepDirection;
    }
}
