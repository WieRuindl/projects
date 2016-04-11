package interfaces;

import enemiesSkins.Enemy;
import labyrinth.Cell;
import players.Player;

import java.awt.*;
import java.util.List;

public interface UniqueAction {
    void uniqueAction(Cell[][] cells, Player player, List<Enemy> enemies);
}
