package worlds;

import characters.enemies.Enemy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Pyramid extends World {
    @Autowired
    public Pyramid(@Qualifier(value = "pyramid") List<Enemy> enemies) {
        super(enemies);
    }
}
