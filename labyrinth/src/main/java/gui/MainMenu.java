package gui;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainMenu {

    public static void main(String[] args) {
        JFrame frame = new JFrame("hello");
        frame.setSize(600, 600);
        frame.setLocation(100, 100);
        frame.add("button", new JButton("sdfsfd"));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
