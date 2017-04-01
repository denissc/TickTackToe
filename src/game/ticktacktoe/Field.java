package game.ticktacktoe;

import org.jetbrains.annotations.Contract;

import java.io.IOException;

/**
 * Created by denissc on 01.04.17.
 *
 * Class represent the game filed and controls cells on it
 */
public class Field {
    public final int EMPTY_CELL = 0;
    public final int CROSS_CELL = 1;
    public final int ZERO_CELL = 2;
    public final int MEASUREMENT = 3;
    private int[][] cells = new int[MEASUREMENT][MEASUREMENT];
    private int userCellType;
    private int computerCellType;

    Field(){
        userCellType = CROSS_CELL;
        computerCellType = ZERO_CELL;

        for (int x=0; x < MEASUREMENT;x++){
            for (int y=0; y < MEASUREMENT; y++){
                cells[x][y] = EMPTY_CELL;
            }
        }
    }

    /**
     * Add User cell on field
     *
     * @param x X axis
     * @param y Y axis
     */
    public void addUserCell(int x, int y){
        addCell(x,y,userCellType);
    }

    /**
     * Add Computer cell on field
     *
     * @param x X axis
     * @param y Y axis
     */
    public void addComputerCell(int x, int y){
        addCell(x,y,computerCellType);
    }

    /**
     * Adds cell on the field in specified position
     *
     * @param x X axis
     * @param y Y axis
     * @param cellType cell type
     */
    private void addCell(int x, int y,int cellType) {
        boolean cellTypeAcceptable = cellType == EMPTY_CELL || cellType == CROSS_CELL || cellType == ZERO_CELL;
        if (isCellCanBeAdded(x, y) && cellTypeAcceptable) {
            cells[x][y] = cellType;
        } else {
            System.out.println("Failed to add cell");
        }
    }

    /**
     * Check is cell can be added on specified position
     *
     * @param x X axis
     * @param y Y axis
     * @return boolean
     */
    public boolean isCellCanBeAdded(int x, int y) {
        boolean canBeAdded = false;
        try {
            canBeAdded = isCellEmpty(x, y);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return canBeAdded;
    }

    /**
     * Check if cell exists on the field
     *
     * @param x - x axis
     * @param y - y axis
     * @return boolean
     */
    public boolean isCellExits(int x, int y) {
        return x >= 0 && x < MEASUREMENT && y >= 0 && y < MEASUREMENT;
    }

    /**
     * Check if cell is empty on the field
     *
     * @param x - x axis
     * @param y - y axis
     * @return boolean
     */
    public boolean isCellEmpty(int x, int y) throws IOException {
        if (! isCellExits(x,y)) {
            throw new IOException("Out of field bounds");
        }

        return cells[x][y] == EMPTY_CELL;
    }

    /**
     * Draws field
     *
     */
    public void draw() {
        System.out.print(' ');
        for (int i = 0; i < MEASUREMENT;i++){
            System.out.print("| ");

            System.out.print(i);
            System.out.print(" |");
        }
        System.out.println();
        System.out.println("----------------");
        for (int i = 0; i < MEASUREMENT;i++){
            for (int j = 0; j < MEASUREMENT; j++){
                if (j == 0)
                    System.out.print(i);
                System.out.print("| ");

                drawCell(i, j);
                System.out.print(" |");
            }
            System.out.println();
            System.out.println("----------------");
        }
    }

    /**
     * Draws field cell by type
     *
     * @param x X axis
     * @param y Y axis
     */
    private void drawCell(int x, int y) {
        int cell = cells[x][y];
        switch (cell) {
            case EMPTY_CELL:
                System.out.print(' ');
                break;
            case CROSS_CELL:
                System.out.print('X');
                break;
            case ZERO_CELL:
                System.out.print('O');
        }
    }

    /**
     * Check if field is full
     *
     * @return boolean
     */
    public boolean isFull() {
        boolean isFull = true;

        OUTER : for (int i = 0; i < MEASUREMENT;i++){
            for (int j = 0; j < MEASUREMENT; j++){
                if (cells[i][j] == EMPTY_CELL) {
                    isFull = false;
                    break OUTER;
                }
            }
        }
        return isFull;
    }

    /**
     * Check field for vertical horizontal or diagonal winner combination
     *
     * @param cellType field cell type
     * @return boolean value if winner combination on the field
     */
    private boolean hasWinnerCombinationByCellType(int cellType) {

        boolean horizontalLineFilled = false;
        for (int i = 0; i < MEASUREMENT;i++){
            horizontalLineFilled = true;
            for (int j = 0; j < MEASUREMENT; j++){
                if (cells[i][j] != cellType) {
                    horizontalLineFilled = false;
                }
            }
        }

        boolean verticalLineFilled = false;
        for (int i = 0; i < MEASUREMENT;i++){
            verticalLineFilled = true;
            for (int j = 0; j < MEASUREMENT; j++){
                if (cells[j][i] != cellType) {
                    verticalLineFilled = false;
                }
            }
        }

        boolean diagonalLineFilled = true;
        for (int i = 0; i < MEASUREMENT;i++){
            if (cells[i][i] != cellType) {
                diagonalLineFilled = false;
            }
        }

         boolean reverseDiagonalFilled = true;
        for (int i = MEASUREMENT - 1,j = 0; j < MEASUREMENT;i--,j++){
            if (cells[i][j] != cellType) {
                reverseDiagonalFilled = false;
            }
        }

        boolean hasWinner = horizontalLineFilled || verticalLineFilled || diagonalLineFilled || reverseDiagonalFilled;

        return hasWinner;
    }

    /**
     * Check is computer won
     *
     * @return boolean
     */
    public boolean isComputerWin() {
        return hasWinnerCombinationByCellType(computerCellType);
    }

    /**
     * Check is user win
     *
     * @return boolean
     */
    public boolean isUserWin() {
        return hasWinnerCombinationByCellType(userCellType);
    }
}
