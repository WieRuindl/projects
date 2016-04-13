package enemiesSkins;

import interfaces.Logic;
import interfaces.WorldAccessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@WorldAccessory(worlds = "castle")
public class Ghost extends Enemy {

    @Autowired
    public Ghost(@Qualifier(value = "ghostLogic") Logic logic) {
        super(logic);
    }

    public double getSpeed() {
        return 1;
    }

    public int getAnimationDuration() {
        return 1000;
    }

}
