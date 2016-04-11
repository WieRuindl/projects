package gameManager;

import interfaces.ShowMode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import players.Player;
import players.PlayersFactory;
import showModes.ModesFactory;
import worlds.World;

import javax.annotation.PostConstruct;

@Component
public class GameSession {

    @Autowired
    PlayersFactory playersFactory;

    @Autowired
    ModesFactory modesFactory;

    @Value(value = "${world.name}")
    String worldName;

    @Autowired
    @Getter
    World world;

    @Value(value = "${player.name}")
    String playerName;
    @Getter
    Player player;

    @Value(value = "${mode.name}")
    String modeName;
    @Getter
    ShowMode mode;

    @PostConstruct
    private void setSessionProperties() {
        player = playersFactory.getPlayer(playerName);
        mode = modesFactory.getMode(modeName);
        if (player == null || mode == null) {
            throw new RuntimeException();
        }
    }
}
