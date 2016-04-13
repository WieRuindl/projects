package players;

import enemiesSkins.Enemy;
import interfaces.Description;
import interfaces.Orientation;
import labyrinth.Cell;
import labyrinth.Direction;
import lombok.Getter;
import lombok.Setter;
import gameManager.Character;

import java.util.Map;
import java.util.List;

public abstract class Player extends Character implements Description, Orientation {

    @Getter
    @Setter
    private boolean alive = true;

    @Getter
    @Setter
    protected int treasures = 0;

    public final void move(Direction direction) {
        Map<Direction, Cell> nearestCells = getNearestCells(cells, location);
        if (nearestCells.get(direction) != Cell.WALL) {
            location.translate(direction.getDx(), direction.getDy());
        }
    }

    protected final String getSource() {
        return "images/player/" + getClass().getSimpleName().toLowerCase();
    }

    public abstract void uniqueAction(Cell[][] cells, List<Enemy> enemies);
}
