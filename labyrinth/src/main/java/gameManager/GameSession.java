package gameManager;

import interfaces.ShowMode;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import characters.players.Player;
import characters.players.PlayersFactory;
import showModes.ShowModesFactory;
import worlds.World;
import worlds.WorldsFactory;

import javax.annotation.PostConstruct;

@Component
public class GameSession {
    @Autowired
    private ShowModesFactory modesFactory;

    @Autowired
    private PlayersFactory playersFactory;

    @Autowired
    private WorldsFactory worldsFactory;


    @Getter
    @Value(value = "${labyrinth.enemiesNum}")
    private int enemiesNum;

    @Getter
    @Value(value = "${labyrinth.treasuresNum}")
    private int treasuresNum;

    @Getter
    @Value(value = "${labyrinth.width}")
    private int width;

    @Getter
    @Value(value = "${labyrinth.height}")
    private int height;

    @Value(value = "${world.name}")
    private String worldName;
    @Getter
    private World world;

    @Value(value = "${player.name}")
    private String playerName;
    @Getter
    private Player player;

    @Value(value = "${mode.name}")
    private String modeName;
    @Getter
    private ShowMode mode;

    @PostConstruct
    private void setSessionProperties() {
        player = playersFactory.getPlayer(playerName);
        mode = modesFactory.getMode(modeName);
        world = worldsFactory.getWorld(worldName);
        if (player == null || mode == null || world == null) {
            throw new RuntimeException("Error while initializing: player = " + player + ", mode = " + mode + ", world = " + world);
        }
    }
}
