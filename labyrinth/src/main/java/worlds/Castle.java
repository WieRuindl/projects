package worlds;

import characters.enemies.Enemy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Castle extends World {
    @Autowired
    public Castle(@Qualifier(value = "castle") List<Enemy> enemies) {
        super(enemies);
    }
}
