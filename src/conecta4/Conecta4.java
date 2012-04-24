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

    static int cols; //Columnas
    static int rows; //Filas
    static int[][] board; //Tablero
    static boolean playing = true; //Para salir del bucle y dejar de jugar cuando se acabe
    static int playerThrowing = 1; //jugador que está tirando
    static int winner; //ganador
    static int numTurns = 0; //Cuantas tiradas se han hecho

    public static void initialize(String[] args) { //Las columnas y filas se pasan por parámetro
        //TIP: Desde Netbeans, botón derecho en el proyecto, propierties, run, y poner por arguments columnas y filas
        //Por ejemplo [Arguments:]  8 8
        cols = Integer.parseInt(args[0]);
        rows = Integer.parseInt(args[1]);
        board = new int[cols][rows];
    }

    public static void main(String[] args) {
        initialize(args);

        while (playing) {
            showBoard();
            while (!turn()) {
                System.out.println("Invalid throw");
            }
            checkBoard();
            numTurns++;
            if (numTurns == cols * rows) { //Si ya no hay mas sitio queda en empate
                winner = 0;
                playing = false;
            }
        }

        showBoard();
        System.out.println();
        if (winner == 0) {
            System.out.println("DRAW");
        } else {
            System.out.println("Winner Player " + winner);
        }
    }

    private static void showBoard() { //Printa el estado actual del tablero

        for (int r = rows - 1; r >= 0; r--) {
            System.out.println();
            for (int c = 0; c < cols; c++) {
                if (board[c][r] == 1) {
                    System.out.print("X ");
                } else if (board[c][r] == 2) {
                    System.out.print("O ");
                } else {
                    System.out.print("- ");
                }
            }
        }
        System.out.println();
    }

    private static boolean turn() { //Función que ejecuta cada tirada, devuelve falso si no se ha podido realizar
        System.out.println("Player " + playerThrowing + " throws");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //entrada por teclado
        int inp;
        try {
            String inp_s = reader.readLine();
            if(inp_s.isEmpty())
                return false;
            inp = Integer.parseInt(inp_s) - 1; //se tira en la columna-1, ya que el vector es de 0 a cols-1
        } catch (IOException e) {
            //Logger.getLogger(Conecta4.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        if (inp < 0 || inp > cols - 1) {
            return false;
        }

        for (int r = 0; r < rows; r++) { //Mira si hay sitio en la columna elegida
            if (board[inp][r] == 0) {
                board[inp][r] = playerThrowing; //Se asigna a la posicion de la tirada, el número del jugador que ha tirado

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

    private static void checkBoard() { //Comprueba si ha habido ganador
        if (playing) {
            vertical();
        }
        if (playing) {
            horizontal();
        }
        if (playing) {
            diagonal_Right();
        }
        if (playing) {
            diagonal_Left();
        }
    }

    private static void vertical() {
        for (int c = 0; c < board.length; c++) {
            check(board[c]);
            if (winner != 0) {
                break;
            }
        }
    }

    private static void horizontal() {
        int[] col = new int[cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < board.length; c++) {
                int i = board[c][r];
                col[c] = board[c][r];
            }
            check(col);
            for (int i = 0; i < col.length; i++) //Resetea vector
            {
                col[i] = 0;
            }
            if (winner != 0) {
                break;
            }
        }
    }

    private static void diagonal_Right() { //La comprobación se hace en forma de L
        //comenzando desde una esquina y recorriendo todas las columnas
        //Y luego recorriendo todas las filas
        int[] diag = new int[Math.max(cols, rows)]; //la diagonal máxima es el max entre columnas y filas
        int rStart = 3;
        int cStart = 0;
        int r = rStart;
        int c = cStart;
        int aux = 0;
        while (cStart < cols - 3) {
            diag[aux] = board[c][r];
            aux++;
            r--;
            c++;
            if (r < 0 || c > cols - 1) {
                check(diag);
                if (rStart < rows - 1) {
                    rStart++;
                } else {
                    cStart++;
                }
                r = rStart;
                c = cStart;

                for (int i = 0; i < diag.length; i++) {
                    diag[i] = 0;
                }
                aux = 0;
            }
        }
    }

    private static void diagonal_Left() {
        int[] diag = new int[Math.max(cols, rows)];
        int rStart = rows - 3;
        int cStart = 0;
        int r = rStart;
        int c = cStart;
        int aux = 0;
        while (cStart < cols - 3) {
            diag[aux] = board[c][r];
            aux++;
            r++;
            c++;
            if (r > rows - 1 || c > cols - 1) {
                check(diag);

                if (rStart > 0) {
                    rStart--;
                } else {
                    cStart++;
                }
                r = rStart;
                c = cStart;

                for (int i = 0; i < diag.length; i++) {
                    diag[i] = 0;
                }
                aux = 0;
            }
        }
    }

    private static void check(int[] line) { //Función que comprueba si en un vector hay 4 numeros iguales consecutivos
        //DEBUG: Printa todas los vectores que tiene que comprobar
        /*
         * for (int s = 0; s < line.length; s++) { System.out.print(line[s]); }
         * System.out.println();
         */
        int acc = 0;
        if (line[0] != 0) {
            acc = 1;
        }
        for (int i = 1; i < line.length; i++) {
            if (line[i] != 0 && line[i] == line[i - 1]) {
                acc++;
                if (acc == 4) {
                    winner = line[i];
                    playing = false;
                    return;
                }
            } else if (line[i] != 0) {
                acc = 1;
            } else {
                acc = 0;
            }
        }
    }
}