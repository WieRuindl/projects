package enemiesSkins;

import interfaces.Logic;
import labyrinth.Direction;
import gameManager.Character;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public abstract class Enemy extends Character {

    private final Logic logic;

    public abstract double getSpeed();

    protected final String getSource() {
        return "images/enemy/" + getClass().getSimpleName().toLowerCase();
    }

    public final void move(Point player) {
        Direction direction = logic.makeDecision(cells, location, player);
        location.translate(direction.getDx(), direction.getDy());
    }
}
