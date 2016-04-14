package controller;

import model.Directory;
import entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Controller {

    @Autowired
    private Model model;

    public void start() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String trim = input.trim();
            List<String> strings = Arrays.asList(trim.split(" "));
            String commandName = strings.get(0);
            List<String> args = strings.subList(1, strings.size());

            model.execute(commandName, args);
        }
    }

}
