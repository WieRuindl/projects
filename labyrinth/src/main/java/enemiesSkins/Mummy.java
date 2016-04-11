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
public class Mummy extends Enemy {

    @Autowired
    protected Mummy(@Qualifier(value = "mummyLogic") Logic logic) {
        super(logic);
    }

    public double getSpeed() {
        return 1;
    }

    public double getAnimationDuration() {
        return 1;
    }
}
