/* ---------------------------------------------------------------
 Código fuente: Conecta4.java
 Màster Informàtica
 78094911K Ismael Arroyo Campos
 43744863J Jordi Lladós Segura
 78093080F David Puig Vall

 Descripción breve código clase/fichero: Conecta4
 Genera un tablero de Conecta4 donde los jugadores tendran que hacer una linea de 4
 ya sea en vertical, horizontal o diagonal, donde las fichas se colocan por columnas 
 y siempre en la fila de más abajo disponible.
 --------------------------------------------------------------- */
package conecta4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Conecta4 {

    public static class BadArgsException extends Exception {

        public BadArgsException() {
        }
    }
    static boolean isUserInput;
    static Board board; //Tablero
    public static int numTurns = 0; //Cuantas tiradas se han hecho

    public static void initialize(String[] args) throws BadArgsException { //Las columnas y filas se pasan por parámetro
        //TIP: Desde Netbeans, botón derecho en el proyecto, propierties, run, y poner por arguments columnas y filas
        //Por ejemplo [Arguments:]  8 8
        if (args.length != 2) {
            throw new BadArgsException();
        }
        try {
            board = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } catch (NumberFormatException e) {
            throw new BadArgsException();
        }
    }

    public static void main(String[] args) {
        int winner = 0;
        int playerThrowing;
        isUserInput = true;
        try {
            initialize(args);
        } catch (BadArgsException ex) {
            System.out.println("Please, run this program with 2 parameters as integers (columns, rows)\n"
                    + ". For example: Conecta4.java 6 5");
            return;
        }

        while (winner == 0) {
            board.showBoard();

            playerThrowing = numTurns % 2 + 1;
            while (!turn(0, playerThrowing)) {
                System.out.println("Invalid throw");

            }
            numTurns++;
            
            winner = checkWinner();
        }

        showWinner(winner);
    }

    private static int getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //entrada por teclado
        int inp;
        try {
            String inp_s = reader.readLine();
            if (inp_s.isEmpty()) {
                return -1;
            }
            inp = Integer.parseInt(inp_s) - 1; //se tira en la columna-1, ya que el vector es de 0 a cols-1
        } catch (IOException | NumberFormatException e) {
            //Logger.getLogger(Conecta4.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return inp;
    }

    public static boolean turn(int pos, int playerThrowing) { //Función que ejecuta cada tirada, devuelve falso si no se ha podido realizar
        System.out.println("Player " + playerThrowing + " throws");

        int inp;
        if (isUserInput) {
            inp = getUserInput();
        } else {
            inp = pos;
        }

        if (inp < 0 || inp > board.getCols() - 1) {
            return false;
        }

        return board.throwToken(inp, playerThrowing);
    }

    private static int checkWinner() {
        int winner = board.checkBoard();

        if (winner == 0 && numTurns == board.getCols() * board.getRows()) { //DRAW
            winner = -1;
        }
        return winner;
    }

    private static void showWinner(int winner) {
        board.showBoard();
        System.out.println();
        if (winner == -1) {
            System.out.println("DRAW");
        } else {
            System.out.println("Winner Player " + winner);
        }
    }
}
