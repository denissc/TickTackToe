package game.ticktacktoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();

        do {

            game.play();

        } while (game.isRunning());
    }
}
