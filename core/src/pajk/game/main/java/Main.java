package pajk.game.main.java;

import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.StateManager;

import java.util.Scanner;

/**
 * Created by jonatan on 14/04/2016.
 */
public class Main {
    public static void main(String[] args) {
        Controller control = new Controller(StateManager.getInstance());
        Scanner scan = new Scanner(System.in);
        while (true){
            String str = scan.next();
            char key = str.toCharArray()[0];
            switch (key){
                case 'u':
                    control.upInput();
                    break;
                case 'l':
                    control.leftInput();
                    break;
                case 'r':
                    control.rightInput();
                    break;
                case 'd':
                    control.downInput();
                    break;
            }
        }
    }
}
