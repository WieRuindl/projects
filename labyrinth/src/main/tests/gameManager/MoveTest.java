package gameManager;

import labyrinth.Cell;
import labyrinth.Direction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import characters.players.Player;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/xml-configurations/main.xml")
public class MoveTest {

    private Cell[][] cells;
    Point startLocation = new Point(1, 1);

    @Autowired
    @Qualifier(value = "rogue")
    private Player player;

    @Before
    public void setUp() throws Exception {
        cells = new Cell[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                cells[y][x] = Cell.WALL;
            }
        }
        cells[1][0] = Cell.EMPTY;
        cells[1][1] = Cell.EMPTY;

        player.setCells(cells);
        player.setLocation(new Point(startLocation));
    }

    @Test
    public void testGetNearestCells() throws Exception {
        player.move(Direction.SOUTH);
        assertEquals(startLocation, player.getLocation());
        player.move(Direction.NORTH);
        assertEquals(startLocation, player.getLocation());
        player.move(Direction.EAST);
        assertEquals(startLocation, player.getLocation());
        player.move(Direction.WEST);
        assertNotEquals(startLocation, player.getLocation());
    }
}