package interfaces;

import characters.players.Player;
import labyrinth.Cell;

public interface ShowMode {
    Cell[][] getVisibleCells(Cell[][] cells, Player player);
}
