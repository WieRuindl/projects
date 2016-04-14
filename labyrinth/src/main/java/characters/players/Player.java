package characters.players;

import characters.enemies.Enemy;
import interfaces.Description;
import labyrinth.Cell;
import labyrinth.Direction;
import lombok.Getter;
import lombok.Setter;
import characters.Character;

import java.util.Map;
import java.util.List;

public abstract class Player extends Character implements Description {

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

    public int getAnimationDuration() {
        return 1000;
    }
}
