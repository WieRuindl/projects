package gameManager;

import interfaces.Animation;
import animationManager.AnimationContainer;
import animationManager.AnimationsManager;
import org.springframework.beans.factory.annotation.Autowired;
import enemiesSkins.Enemy;
import org.newdawn.slick.*;
import labyrinth.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class Game extends BasicGameState {

    @Value(value = "${window.fps}")
    private double FPS;
    private double fps = 0;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private AnimationsManager animationManager;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
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
        AnimationContainer animationContainer = animationManager.getAnimationContainer(gameManager.getSession().getPlayer());
        Image img = selectImage(animationContainer);
        g.drawImage(img, gameManager.getSession().getPlayer().getLocation().x * 32, gameManager.getSession().getPlayer().getLocation().y * 32);
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

            Animation animation = gameManager.getSession().getWorld().getImage(Cell.TREASURE);
            AnimationContainer animationContainer = animationManager.getAnimationContainer(animation);
            Image img = selectImage(animationContainer);
            g.drawImage(img, img.getWidth() * treasure.getLocation().x, img.getHeight() * treasure.getLocation().y);
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
            AnimationContainer animationContainer = animationManager.getAnimationContainer(enemy);
            Image img = selectImage(animationContainer);
            g.drawImage(img, img.getWidth() * x, img.getHeight() * y);
        }
    }

    private void drawWorld(Graphics g, Cell[][] cells) {
        int height = cells.length;
        int width = cells[0].length;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];
                Animation animation = gameManager.getSession().getWorld().getImage(cell);
                AnimationContainer animationContainer = animationManager.getAnimationContainer(animation);
                Image img = selectImage(animationContainer);
                g.drawImage(img, img.getWidth() * x, img.getHeight() * y);
            }
        }
    }

    private Image selectImage(AnimationContainer animationContainer) {
        List<Image> images = animationContainer.getImages();
        double animationDuration = animationContainer.getAnimationDuration();
        int i = (int) (((fps / FPS) / animationDuration) % images.size());
        return images.get(i);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
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
