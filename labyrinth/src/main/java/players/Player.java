package players;

import interfaces.Animation;
import enemiesSkins.Enemy;
import interfaces.Description;
import interfaces.UniqueAction;
import labyrinth.Cell;
import labyrinth.Direction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import gameManager.Character;

import java.awt.*;
import java.util.Map;
import java.util.List;

public abstract class Player extends Character implements Animation, Description {

    @Setter
    private Cell[][] cells;

    @Getter
    @Setter
    private boolean alive = true;

    @Getter
    @Setter
    protected Point location;

    @Getter
    protected int treasureGot = 0;

    public void findTreasure() {
        treasureGot++;
    }

    public final void move(Direction direction) {
        Map<Direction, Cell> nearestCells = getNearestCells(cells, location);
        if (nearestCells.get(direction) != Cell.WALL) {
            location.translate(direction.getDx(), direction.getDy());
        }
    }

    public final String getSource() {
        return "images/player/" + getClass().getSimpleName().toLowerCase();
    }

    public abstract double getAnimationDuration();

    public abstract void uniqueAction(Cell[][] cells, List<Enemy> enemies);
}
