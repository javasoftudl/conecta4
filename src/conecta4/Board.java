/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta4;

/**
 *
 * @author asherat
 */
public class Board {

    private int board_cols;
    private int board_rows;
    private int[][] matrix;
    private int winner;

    public Board(int cols, int rows) {
        this.board_cols = cols;
        this.board_rows = rows;
        this.matrix = new int[cols][rows];
    }

    public int getCols() {
        return board_cols;
    }

    
    public int getRows() {
        return board_rows;
    }
    
    public void showBoard() { //Printa el estado actual del tablero
        for (int row = board_rows - 1; row >= 0; row--) {
            System.out.println();
            for (int col = 0; col < board_cols; col++) {
                if (matrix[col][row] == 1) {
                    System.out.print("X ");
                } else if (matrix[col][row] == 2) {
                    System.out.print("O ");
                } else {
                    System.out.print("- ");
                }
            }
        }
        System.out.println();
    }

    public boolean throwToken(int col, int playerThrowing) {
        for (int row = 0; row < board_rows; row++) { //Mira si hay sitio en la columna elegida
            if (matrix[col][row] == 0) {
                matrix[col][row] = playerThrowing; //Se asigna a la posicion de la tirada, el número del jugador que ha tirado

                if (playerThrowing == 1) {
                    playerThrowing = 2;
                } else if (playerThrowing == 2) {
                    playerThrowing = 1;
                }
                return true;
            }
        }
        return false;
    }

    public int checkBoard() { //Comprueba si ha habido ganador
        boolean foundWinner = vertical();
        if (!foundWinner) {
            foundWinner = horizontal();
        }
        if (!foundWinner) {
            foundWinner = diagonal_Right();
        }
        if (!foundWinner) {
            diagonal_Left();
        }
        return winner;
    }

    private boolean vertical() {
        for (int col = 0; col < board_cols; col++) {
            if (checkVector(matrix[col])) {
                return true;
            }
        }
        return false;
    }

    private boolean horizontal() {
        int[] cols = new int[board_cols];
        for (int row = 0; row < board_rows; row++) {
            for (int col = 0; col < board_cols; col++) {
                cols[col] = matrix[col][row];
            }
            if (checkVector(cols)) {
                return true;
            }

            resetVector(cols);
        }
        return false;
    }

    private boolean diagonal_Right() { //La comprobación se hace en forma de L
        //comenzando desde una esquina y recorriendo todas las columnas
        //Y luego recorriendo todas las filas
        int[] diag = new int[Math.min(board_cols, board_rows)]; //la diagonal máxima es el max entre columnas y filas
        int startingRow = 3;
        int startingCol = 0;
        int row = startingRow;
        int col = startingCol;
        int aux = 0;
        while (startingCol < board_cols - 3) {
            diag[aux] = matrix[col][row];
            aux++;
            row--;
            col++;
            if (row < 0 || col > board_cols - 1) {
                if (checkVector(diag)) {
                    return true;
                }
                if (startingRow < board_rows - 1) {
                    startingRow++;
                } else {
                    startingCol++;
                }
                row = startingRow;
                col = startingCol;

                resetVector(diag);
                aux = 0;
            }
        }
        return false;
    }

    private boolean diagonal_Left() {
        int[] diag = new int[Math.min(board_cols, board_rows)];
        int startingRow = board_rows - 3;
        int startingCol = 0;
        int row = startingRow;
        int col = startingCol;
        int aux = 0;
        while (startingCol < board_cols - 3) {
            diag[aux] = matrix[col][row];
            aux++;
            row++;
            col++;
            if (row > board_rows - 1 || col > board_cols - 1) {
                if (checkVector(diag)) {
                    return true;
                }

                if (startingRow > 0) {
                    startingRow--;
                } else {
                    startingCol++;
                }
                row = startingRow;
                col = startingCol;

                resetVector(diag);
                aux = 0;
            }
        }
        return false;
    }

    public boolean checkVector(int[] line) { //Función que comprueba si en un vector hay 4 numeros iguales consecutivos
        int counter = 0;
        if (line[0] != 0) {
            counter = 1;
        }
        for (int it = 1; it < line.length; it++) {
            if (line[it] != 0 && line[it] == line[it - 1]) {
                counter++;
                if (counter == 4) {
                    winner = line[it];
                    return true;
                }
            } else if (line[it] != 0) {
                counter = 1;
            } else {
                counter = 0;
            }
        }
        return false;
    }

    private void resetVector(int[] vector) {
        for (int it = 0; it < vector.length; it++) //Resetea vector
        {
            vector[it] = 0;
        }
    }

}
