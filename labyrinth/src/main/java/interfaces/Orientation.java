package interfaces;

import labyrinth.Cell;
import labyrinth.Direction;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface Orientation {
    default Map<Direction, Cell> getNearestCells(Cell[][] cells, Point location) {
        Map<Direction, Cell> nearestCells = new HashMap<>();

        nearestCells.put(Direction.NORTH, cells[location.y+Direction.NORTH.getDy()][location.x+Direction.NORTH.getDx()]);
        nearestCells.put(Direction.SOUTH, cells[location.y+Direction.SOUTH.getDy()][location.x+Direction.SOUTH.getDx()]);
        nearestCells.put(Direction.WEST, cells[location.y+Direction.WEST.getDy()][location.x+Direction.WEST.getDx()]);
        nearestCells.put(Direction.EAST, cells[location.y+Direction.EAST.getDy()][location.x+Direction.EAST.getDx()]);

        return nearestCells;
    }
}
