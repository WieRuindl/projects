package showModes;

import interfaces.ShowMode;
import org.springframework.stereotype.Component;
import characters.players.Player;
import labyrinth.Cell;

@Component
public class Classic implements ShowMode {
    public Cell[][] getVisibleCells(Cell[][] cells, Player player) {
        return cells;
    }
}
