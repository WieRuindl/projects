package interfaces;

import labyrinth.Cell;
import labyrinth.Direction;

import java.awt.*;

public interface Logic {
    Direction makeDecision(Cell[][] cells, Point location, Point playerLocation);
}
