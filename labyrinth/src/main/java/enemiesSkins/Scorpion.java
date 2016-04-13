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
@WorldAccessory(worlds = "pyramid")
public class Scorpion extends Enemy {

    @Autowired
    protected Scorpion(@Qualifier(value = "commonLogic") Logic logic) {
        super(logic);
    }

    public double getMovementDelay() {
        return 1000;
    }

    protected int getAnimationDuration() {
        return 1000;
    }
}
