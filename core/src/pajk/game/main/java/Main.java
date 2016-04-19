package pajk.game.main.java;

import pajk.game.main.java.controller.Controller;
import pajk.game.main.java.model.Model;

import java.util.Scanner;

/**
 * Created by jonatan on 14/04/2016.
 */
public class Main {
    public static void main(String[] args) {
        Controller cont = new Controller(new Model());
        Scanner scan = new Scanner(System.in);
        while (true){
            String str = scan.next();
            char key = str.toCharArray()[0];
            switch (key){
                case 'u':
                    cont.upInput();
                    break;
                case 'l':
                    cont.leftInput();
                    break;
                case 'r':
                    cont.rightInput();
                    break;
                case 'd':
                    cont.downInput();
                    break;
            }
        }
    }
}
