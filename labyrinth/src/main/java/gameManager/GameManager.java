package gameManager;

import characters.enemies.Enemy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import characters.players.Player;
import labyrinth.*;
import interfaces.ShowMode;
import worlds.World;

import java.awt.*;
import java.util.*;
import java.util.List;

@Component
@PropertySource(value = "classpath:properties/game-session.properties")
public class GameManager {
    private Cell[][] cells;
    @Getter
    private List<Point> corners;

    @Getter
    World world;
    @Getter
    ShowMode mode;
    @Getter
    Player player;
    @Getter
    private List<Enemy> enemies = new ArrayList<>();
    @Getter
    private List<Treasure> treasures = new ArrayList<>();

    @Autowired
    private GameManager(GameSession session) {

        LabyrinthCreator labyrinth = new LabyrinthCreator(session.getWidth(), session.getHeight());

        cells = labyrinth.getCells();
        corners = labyrinth.getCorners();

        getMode(session);
        getWorld(session);
        setPlayer(session);
        createEnemies(session);
        createTreasures(session);
    }

    private void getMode(GameSession session) {
        mode = session.getMode();
    }

    private void getWorld(GameSession session) {
        world = session.getWorld();
    }


    private void setPlayer(GameSession session) {
        player = session.getPlayer();
        player.setCells(cells);
        player.setLocation(new Point(1, 1));
    }

    private void createEnemies(GameSession session) {
        Collections.shuffle(corners);

        int min = Math.min(corners.size(), session.getEnemiesNum());
        List<Point> enemiesLocations = corners.subList(0, min);

        enemiesLocations.stream().forEach(location -> {
            Point p = new Point(location);
            try {
                Enemy enemy = world.createEnemy();
                enemy.setLocation(p);
                enemy.setCells(cells);
                enemies.add(enemy);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createTreasures(GameSession session) {
        Collections.shuffle(corners);

        int min = Math.min(corners.size(), session.getTreasuresNum());
        List<Point> treasuresLocations = corners.subList(0, min);

        treasuresLocations.stream().forEach(location -> {
            Point p = new Point(location);
            treasures.add(new Treasure(p));
        });
    }

    public Cell[][] getCells() {
        return mode.getVisibleCells(cells, player);
    }

    public void uniqueAction() {
        player.uniqueAction(cells, enemies);
        checkForEnemy();
    }

    public void movePlayer(Direction direction) {
        player.move(direction);
        checkForTreasure();
        checkForEnemy();
    }

    public void moveEnemy(Enemy enemy, int delta) {
        enemy.setDelay(enemy.getDelay() + delta);
        if (enemy.getDelay() >= enemy.getMovementDelay()) {
            enemy.move(player.getLocation());
            enemy.setDelay(0);
            if (enemy.getLocation().equals(player.getLocation())) {
                player.setAlive(false);
            }
        }
    }

    private void checkForEnemy() {
        enemies.stream().filter(enemy -> enemy.getLocation()
                .equals(player.getLocation()))
                .findFirst().ifPresent(enemy -> player.setAlive(false));
    }

    private void checkForTreasure() {
        Optional<Treasure> treasure = treasures.stream()
                .filter(t -> t.getLocation().equals(player.getLocation()))
                .findFirst();

        if (treasure.isPresent()) {
            treasures.remove(treasure.get());
            player.setTreasures(player.getTreasures() + 1);
            addNewTreasure();
        }
    }

    private void addNewTreasure() {
        LinkedList<Point> availableCorners = new LinkedList<>(corners);
        availableCorners.remove(player.getLocation());
        for (Treasure treasure : treasures) {
            availableCorners.remove(treasure.getLocation());
        }
        Point location = availableCorners.get((int) (Math.random() * availableCorners.size()));
        treasures.add(new Treasure(location));
    }
}
