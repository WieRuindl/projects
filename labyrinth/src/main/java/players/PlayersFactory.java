package players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlayersFactory {
    @Autowired
    private Map<String, Player> players;

    public Player getPlayer(String name) {
        return players.get(name);
    }
}
