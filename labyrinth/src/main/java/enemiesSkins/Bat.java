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
public class Bat extends Enemy {

    @Autowired
    public Bat(@Qualifier(value = "commonLogic") Logic logic) {
        super(logic);
    }

    public double getMovementDelay() {
        return 750;
    }

    protected int getAnimationDuration() {
        return 300;
    }
}
