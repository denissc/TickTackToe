package game.ticktacktoe;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by denissc on 01.04.17.
 *
 * Class that control game process
 */
public class Game {

    private Field field;
    private Scanner input;

    Game(){
        field = new Field();
        input = new Scanner( System.in );
    }

    /**
     * Starts 1 game turn for computer ond user
     *
     */
    public void play() {
        field.draw();
        userTurn();
        if(! field.isFull()) {
            computerTurn();
        }
    }

    /**
     * User turn
     */
    private void userTurn() {
        int x,y;
        boolean isInputCorrect;
        do {
            System.out.print("Type position X : ");
            x = input.nextInt();
            System.out.print("Type position Y : ");
            y = input.nextInt();
            System.out.println();

            isInputCorrect = field.isCellCanBeAdded(x, y);

            if (! isInputCorrect) {
                System.out.println("Incorrect X and Y should be in [0,2],Or cell in not empty");
            }
        } while (! isInputCorrect);

        field.addUserCell(x, y);

    }

    /**
     * Computer turn
     */
    private void computerTurn() {
        int x,
            y;
        boolean isInputCorrect;
        Random random = new Random();

        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
            isInputCorrect = field.isCellCanBeAdded(x, y);
        } while (! isInputCorrect);

        field.addComputerCell(x, y);
    }

    /**
     * Check if game is still running
     *
     * @return boolean
     */
    public boolean isRunning() {
        boolean isRunning = true;

        if (field.isUserWin()) {
            System.out.println("You win! Computer is too bad :)");
            isRunning = false;
        } else if (field.isComputerWin()) {
            System.out.println("Computer win! You lose :(");
            isRunning = false;
        } else if (field.isFull()) {
            System.out.println("You tied! Try next time :)");
            isRunning = false;
        }

        return isRunning;
    }
}
