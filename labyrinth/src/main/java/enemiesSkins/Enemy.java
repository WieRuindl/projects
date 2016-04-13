package enemiesSkins;

import interfaces.Logic;
import labyrinth.Direction;
import gameManager.Character;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.*;

@RequiredArgsConstructor
public abstract class Enemy extends Character {

    private final Logic logic;

    @Getter
    @Setter
    private int delay = 0;

    public abstract double getMovementDelay();

    protected final String getSource() {
        return "images/enemy/" + getClass().getSimpleName().toLowerCase();
    }

    public final void move(Point player) {
        Direction direction = logic.makeDecision(cells, location, player);
        location.translate(direction.getDx(), direction.getDy());
    }
}
