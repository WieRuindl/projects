package showModes;

import interfaces.ShowMode;
import characters.players.Player;
import labyrinth.Cell;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

@Component
public class Memory implements ShowMode {

    private Map<Point, Cell[][]> memoredCells = new LinkedHashMap<>();
    private List<Point> memoredLocations = new LinkedList<>();

    public Cell[][] getVisibleCells(Cell[][] cells, Player player) {

        int height = cells.length;
        int width = cells[0].length;

        Cell[][] copy = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            copy[y][0] = Cell.WALL;
            copy[y][width - 1] = Cell.WALL;
        }

        for (int x = 0; x < width; x++) {
            copy[0][x] = Cell.WALL;
            copy[height - 1][x] = Cell.WALL;
        }

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                copy[y][x] = Cell.FOG;
            }
        }

        int y = player.getLocation().y;
        int x = player.getLocation().x;

        Cell[][] cellsToMemory = new Cell[3][3];

        cellsToMemory[0][0] = cells[y - 1][x - 1];
        cellsToMemory[0][1] = cells[y - 1][x];
        cellsToMemory[0][2] = cells[y - 1][x + 1];

        cellsToMemory[1][0] = cells[y][x - 1];
        cellsToMemory[1][1] = cells[y][x];
        cellsToMemory[1][2] = cells[y][x + 1];

        cellsToMemory[2][0] = cells[y + 1][x - 1];
        cellsToMemory[2][1] = cells[y + 1][x];
        cellsToMemory[2][2] = cells[y + 1][x + 1];

        Cell[][] memory = memoredCells.get(player.getLocation());
        if (memory == null) {
            if (memoredCells.size() >= 10) {
                Point lastLocation = memoredLocations.get(0);
                memoredCells.remove(lastLocation);
                memoredLocations.remove(lastLocation);
            }
            memoredCells.put(player.getLocation(), cellsToMemory);
            memoredLocations.add(player.getLocation());
        } else {
            for (Point p : memoredLocations) {
                Cell[][] c = memoredCells.get(p);
                if (c == memory) {
                    memoredCells.remove(p);
                    memoredLocations.remove(p);

                    memoredCells.put(p, c);
                    memoredLocations.add(p);

                    break;
                }
            }
        }

        for (Point location : memoredLocations) {
            Cell[][] c = memoredCells.get(location);

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    copy[location.y - 1 + j][location.x - 1 + i] = c[j][i];
                }
            }
        }


        return copy;
    }
}
