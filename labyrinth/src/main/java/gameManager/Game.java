package gameManager;

import org.springframework.beans.factory.annotation.Autowired;
import enemiesSkins.Enemy;
import org.newdawn.slick.*;
import labyrinth.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.springframework.beans.factory.annotation.Value;
import players.Player;
import worlds.World;

import java.io.IOException;
import java.util.List;

public class Game extends BasicGameState {

    @Value(value = "${window.fps}")
    private double FPS;
    private double fps = 0;

    @Autowired
    private GameManager gameManager;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        World world = gameManager.getSession().getWorld();
        Player player = gameManager.getSession().getPlayer();
        List<Enemy> enemies = gameManager.getEnemies();
        try {
            world.initAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.initAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Enemy enemy : enemies) {
            try {
                enemy.initAnimation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_LEFT) {
            gameManager.movePlayer(Direction.WEST);
        } else if (key == Input.KEY_RIGHT) {
            gameManager.movePlayer(Direction.EAST);
        } else if (key == Input.KEY_UP) {
            gameManager.movePlayer(Direction.SOUTH);
        } else if (key == Input.KEY_DOWN) {
            gameManager.movePlayer(Direction.NORTH);
        } else if (key == Input.KEY_C) {
            gameManager.uniqueAction();
        }

//        if (gameManager.isDead()) {
//            throw new RuntimeException();
//        }
    }


    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Cell[][] cells = gameManager.getCells();

        drawWorld(g, cells);
        drawEnemies(g, cells);
        drawTreasures(g, cells);
        drawPlayer(g);

        if (!gameManager.getSession().getPlayer().isAlive()) {
            System.err.println("dead");
        }
    }

    private void drawPlayer(Graphics g) {
        Player player = gameManager.getSession().getPlayer();
        Animation animation = player.getAnimation();
        g.drawAnimation(animation, animation.getWidth() * player.getLocation().x, animation.getHeight() * player.getLocation().y);
    }

    private void drawTreasures(Graphics g, Cell[][] cells) {
        for (Treasure treasure : gameManager.getTreasures()) {
            Cell cell = cells[treasure.getLocation().y][treasure.getLocation().x];
            if (cell == Cell.FOG) {
                continue;
            }
            boolean present = gameManager.getEnemies().stream().filter(enemy -> enemy.getLocation().equals(treasure.getLocation())).findFirst().isPresent();
            if (present) {
                continue;
            }

            Animation animation = gameManager.getSession().getWorld().getAnimation(Cell.TREASURE);
            g.drawAnimation(animation, animation.getWidth() * treasure.getLocation().x, animation.getHeight() * treasure.getLocation().y);
        }
    }

    private void drawEnemies(Graphics g, Cell[][] cells) {
        for (Enemy enemy : gameManager.getEnemies()) {
            int y = enemy.getLocation().y;
            int x = enemy.getLocation().x;
            Cell cell = cells[y][x];
            if (cell == Cell.FOG) {
                continue;
            }
            Animation animation = enemy.getAnimation();
            g.drawAnimation(animation, animation.getWidth() * x, animation.getHeight() * y);
        }
    }

    private void drawWorld(Graphics g, Cell[][] cells) {
        int height = cells.length;
        int width = cells[0].length;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                Animation animation = gameManager.getSession().getWorld().getAnimation(cell);
                g.drawAnimation(animation, animation.getWidth() * x, animation.getHeight() * y);
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        System.out.println(delta);

        Player player = gameManager.getSession().getPlayer();
        List<Enemy> enemies = gameManager.getEnemies();
        player.getAnimation().update(delta);
        for (Enemy enemy : enemies) {
            enemy.getAnimation().update(delta);
        }

        fps++;
        if (fps >= FPS * 10) {
            fps = 0;
        }

        gameManager.getEnemies().stream()
                .filter(enemy -> (int) (fps % (FPS / enemy.getSpeed())) == 0)
                .forEach(gameManager::moveEnemy);
    }

    public int getID() {
        return 0;
    }
}
