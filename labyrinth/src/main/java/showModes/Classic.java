package showModes;

import interfaces.ShowMode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import characters.players.Player;
import labyrinth.Cell;

@Component
@Qualifier(value = "classic")
public class Classic implements ShowMode {
    public Cell[][] getVisibleCells(Cell[][] cells, Player player) {
        return cells;
    }
}
