import java.net.*;
import java.io.*;
/** This program is COSC2P13 assignment 2
 *
 * @author Jreanne Ramgeet
 * Studen#: 7366081
 * @version 1.0 (Apr. 2024)                                                        */
//this class manages the connection between the two clients
public class ConnectionThread extends Thread{
    private final Socket client1;
    private final Socket client2;

    public ConnectionThread(Socket c1, Socket c2) {
        client1=c1; client2=c2;
    }

    public void run() {
        try (
                PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
                PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()))
        ) {
            String inputLine;
            out1.println("Welcome to CONNECT4: You are player 1");
            out2.println("Welcome to CONNECT4: You are player 2");

            //start game
            GameManager game = new GameManager();
            game.setupGame();

            while (true) {
                //displays board to both clients
                displayBoard(game,out1,out2);
                out1.println("(player 1) Drop into which column");
                out2.println("Player 1's turn...");
                inputLine=in1.readLine();

                //checks if user's input is invaild
                while (!(inputLine.equals("0")||inputLine.equals("1")||inputLine.equals("2")||inputLine.equals("3")||inputLine.equals("4")||inputLine.equals("5")||inputLine.equals("6"))){
                    out1.println("invalid input, try again");
                    inputLine=in1.readLine();
                }
                game.update(1,Integer.parseInt(inputLine));
                //check win condition after each player's turn
                if (game.isGameOver(1)){
                    out1.println("GAME OVER. player 1 wins");
                    out2.println("GAME OVER. player 1 wins");
                    displayBoard(game,out1,out2);
                    break;
                }else if (game.isGameOver(2)){
                    out1.println("GAME OVER. player 2 wins");
                    out2.println("GAME OVER. player 2 wins");
                    displayBoard(game,out1,out2);
                    break;
                }else if(game.isDraw()){
                    out1.println("GAME OVER. No one wins, tie");
                    out2.println("GAME OVER. No one wins, tie");
                    displayBoard(game,out1,out2);
                    break;
                }

                if (inputLine==null) break;
                out2.println(inputLine);
                //display board
                displayBoard(game,out1,out2);
                out2.println("(player 2)Drop into which column");
                out1.println("Player 2's turn...");
                inputLine=in2.readLine();

                //if user's input is invalid
                while (!(inputLine.equals("0")||inputLine.equals("1")||inputLine.equals("2")||inputLine.equals("3")||inputLine.equals("4")||inputLine.equals("5")||inputLine.equals("6"))){
                    out2.println("invalid input, try again");
                    inputLine=in2.readLine();
                }
                game.update(2,Integer.parseInt(inputLine));//check win condition after each player's turn
                if (game.isGameOver(1)){
                    out1.println("GAME OVER. player 1 wins");
                    out2.println("GAME OVER. player 1 wins");
                    displayBoard(game,out1,out2);
                    break;
                }else if (game.isGameOver(2)){
                    out1.println("GAME OVER. player 2 wins");
                    out2.println("GAME OVER. player 2 wins");
                    displayBoard(game,out1,out2);
                    break;
                }else if(game.isDraw()){
                    out1.println("GAME OVER. No one wins, tie");
                    out2.println("GAME OVER. No one wins, tie");
                    displayBoard(game,out1,out2);
                    break;
                }
                if (inputLine==null) break;
                out1.println(inputLine);

            }
        } catch (IOException e) {
            System.out.println("Ah nertz.");
        }
    }
    /** This method displays the game bored (text version) to both clients
     * @param game
     * @param out1 output for client 1
     * @param out2 output for client 2
     */
    public void displayBoard(GameManager game,PrintWriter out1,PrintWriter out2){
        for (int row = 0; row < game.gameBoard.length; row++) {
            for (int col=0; col< game.gameBoard[0].length;col++){
                out1.print("| " + game.gameBoard[row][col] + " ");
                out2.print("| " + game.gameBoard[row][col] + " ");
            }
            out1.println("|");
            out2.println("|");
        }
        out1.println("--------------------------");
        out2.println("--------------------------");

    }
}
