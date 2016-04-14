package characters.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlayersFactory {

    private final Map<String, Player> players;

    @Autowired
    public PlayersFactory(Map<String, Player> players) {
        this.players = players;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }
}
