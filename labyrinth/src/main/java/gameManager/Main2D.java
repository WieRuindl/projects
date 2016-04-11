package gameManager;

import org.newdawn.slick.AppGameContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Main2D {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml-configurations/main.xml");
        AppGameContainer gameContainer = context.getBean(AppGameContainer.class);
        gameContainer.setTargetFrameRate(60);
        gameContainer.setShowFPS(false);

        gameContainer.start();
    }
}
