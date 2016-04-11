package gameManager;

import enemiesSkins.Enemy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import players.Player;
import labyrinth.*;
import interfaces.ShowMode;
import players.PlayersFactory;
import worlds.World;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.*;
import java.util.List;

@Component
@PropertySource(value = "classpath:properties/game-session.properties")
public class GameManager {

    @Autowired
    @Getter
    private GameSession session;

    private Cell[][] cells;
    @Getter
    private List<Point> corners;
    @Getter
    private List<Enemy> enemies = new ArrayList<>();
    @Getter
    private List<Treasure> treasures = new ArrayList<>();

    @Value(value = "${labyrinth.enemiesNum}")
    private int enemiesNum;

    @Value(value = "${labyrinth.treasuresNum}")
    private int treasuresNum;

    @Autowired
    private GameManager(
            @Value(value = "${labyrinth.width}") int width,
            @Value(value = "${labyrinth.height}") int height) {

        LabyrinthCreator labyrinth = new LabyrinthCreator(width, height);

        cells = labyrinth.getCells();
        corners = labyrinth.getCorners();

        while (corners.size() < enemiesNum + treasuresNum && treasuresNum > 1) {
            if (enemiesNum > treasuresNum) {
                enemiesNum--;
            } else {
                treasuresNum--;
            }
        }
    }

    @PostConstruct
    private void setPlayer() {
        Player player = session.getPlayer();
        player.setCells(cells);
        player.setLocation(new Point(1, 1));
    }

    @PostConstruct
    private void createEnemies() {
        Collections.shuffle(corners);

        List<Point> enemiesLocations = corners.subList(0, enemiesNum);

        enemiesLocations.stream().forEach(location -> {
            Point p = new Point(location);
            try {
                Enemy enemy = session.getWorld().createEnemy();
                enemy.setLocation(p);
                enemy.setCells(cells);
                enemies.add(enemy);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @PostConstruct
    private void createTreasures() {
        Collections.shuffle(corners);

        List<Point> treasuresLocations = corners.subList(0, treasuresNum);

        treasuresLocations.stream().forEach(location -> {
            Point p = new Point(location);
            treasures.add(new Treasure(p));
        });
    }

    public Cell[][] getCells() {
        return session.getMode().getVisibleCells(cells, session.getPlayer());
    }

    public void uniqueAction() {
        session.getPlayer().uniqueAction(cells, enemies);
        checkForEnemy();
    }

    public void movePlayer(Direction direction) {
        session.getPlayer().move(direction);
        checkForTreasure();
        checkForEnemy();
    }

    public void moveEnemy(Enemy enemy) {
        enemy.move(session.getPlayer().getLocation());

        if (enemy.getLocation().equals(session.getPlayer().getLocation())) {
            session.getPlayer().setAlive(false);
        }
    }

    private void checkForEnemy() {
        enemies.stream().filter(enemy -> enemy.getLocation()
                .equals(session.getPlayer().getLocation()))
                .findFirst().ifPresent(enemy -> session.getPlayer().setAlive(false));
    }

    private void checkForTreasure() {
        Optional<Treasure> treasure = treasures.stream()
                .filter(t -> t.getLocation().equals(session.getPlayer().getLocation()))
                .findFirst();

        if (treasure.isPresent()) {
            treasures.remove(treasure.get());
            session.getPlayer().findTreasure();
            addNewTreasure();
        }
    }

    private void addNewTreasure() {
        LinkedList<Point> availableCorners = new LinkedList<>(corners);
        availableCorners.remove(session.getPlayer().getLocation());
        for (Treasure treasure : treasures) {
            availableCorners.remove(treasure.getLocation());
        }
        Point location = availableCorners.get((int) (Math.random() * availableCorners.size()));
        treasures.add(new Treasure(location));
    }
}
