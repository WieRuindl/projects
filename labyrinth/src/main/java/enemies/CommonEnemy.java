package enemies;

import enemiesSkins.Enemy;
import gameManager.Character;
import interfaces.Logic;
import interfaces.Orientation;
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

@Component(value = "commonLogic")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommonEnemy implements Logic, Orientation {

    private Direction keepDirection = Direction.values()[(int) (Math.random() * Direction.values().length)];

    public Direction makeDecision(Cell[][] cells, Point location, Point playerLocation) {

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
}
