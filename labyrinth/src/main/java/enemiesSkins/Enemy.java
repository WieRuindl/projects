package enemiesSkins;

import interfaces.Animation;
import labyrinth.Cell;
import interfaces.Logic;
import labyrinth.Direction;
import gameManager.Character;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.*;

@RequiredArgsConstructor
public abstract class Enemy extends Character implements Animation {

    private final Logic logic;

    @Setter
    private Cell[][] cells;

    @Getter
    @Setter
    private Point location;

    public abstract double getSpeed();

    public abstract double getAnimationDuration();

    public final String getSource() {
        return "images/enemy/" + getClass().getSimpleName().toLowerCase();
    }

    public final void move(Point player) {
        Direction direction = logic.makeDecision(cells, location, player);
        location.translate(direction.getDx(), direction.getDy());
    }
}
