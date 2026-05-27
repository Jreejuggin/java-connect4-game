import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/** This class creates a GUI for the connect 4 game
 */
public class Gui extends JFrame implements ActionListener {
    static int value;//value to send to server
    PrintWriter sockOut;
    Color originalColor;//the default color of the button
    private JButton[][] buttonGrid;
    //private String fromServer;
    static boolean firstFlag=false;
    static boolean secondFlag=false;


    public Gui(){

        buttonGrid = new JButton[7][7];
        setTitle("Connect4");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

        //start screen
        TextField text = new TextField("Welcome to Connect 4!");
        JPanel startScreen = new JPanel();
        JButton startb = new JButton("Start");
        startScreen.add(text,BorderLayout.CENTER);
        startScreen.add(startb);

        //connecting screen
        TextArea t = new TextArea("You are player 1");
        JPanel connScreen = new JPanel();
        text = new TextField("Connecting to server, waiting for player 2...");
        connScreen.add(text,BorderLayout.CENTER);

        // Create the side panel for game updates
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(t);

        


        //creating the game board
        GridLayout grid = new GridLayout(7,7,1,1);
        JPanel panel = new JPanel(grid);
        panel.setLayout(new BorderLayout());
        //adds a button for each spot on the grid
        for (int i = 0; i < 7 ; i++) {
            for (int j = 0; j < 7; j++) {
                JButton button;
                if (i == 0){// the top row is the clickable buttons with the column number on it
                    button = new JButton(""  + j + "");
                    button.setPreferredSize(new Dimension(30, 30));
                    buttonGrid[i][j] = button; // specific button is saved to the grid
                    panel.add(button);//button added to panel
                    int finalJ = j;// col number
                    //create the action for when the button is clicked
                    button.addActionListener(e -> {
                        value = finalJ;//when button is pressed value equals the column number
                        System.out.println(value);
                        sockOut.println(value);//the col number is sent to server
                        for (int row = buttonGrid.length-1; row >0  ; row--) {
                            //drop 'token' in column
                            if (!(buttonGrid[row][finalJ].getBackground()== Color.BLUE || buttonGrid[row][finalJ].getBackground()== Color.RED )){
                                buttonGrid[row][finalJ].setOpaque(true);
                                buttonGrid[row][finalJ].setBackground(Color.RED);
                                return;
                            }
                        }
                        //check win condition everytime button is pressed
                        if (isGameOver(Color.RED)){//player 1(red)
                            JLabel winner = new JLabel("You Win!");
                            add(winner);
                            System.exit(0);
                        }else if (isGameOver(Color.BLUE)){//player 2(blue)
                            JLabel loser = new JLabel("You Lose!");
                            add(loser);
                            System.exit(0);
                        }

                    });
                }else{//buttons (non-clickable) for the rest of the grid (6x7) 
                    button = new JButton();
                    button.setPreferredSize(new Dimension(30, 30)); // Set button size
                    button.setEnabled(false);
                    button.setOpaque(true);
                    buttonGrid[i][j] = button;
                    panel.add(button);
                }
            }
        }//end of for loop

        startb.addActionListener(e -> {
            if (firstFlag && !secondFlag){
                add(connScreen);


                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                remove(startScreen);
            }
            if (firstFlag && secondFlag) {
                add(panel);


                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                remove(startScreen);
            }

        });
        add(startScreen);
        //add(panel);
        Color originalColor = buttonGrid[6][6].getBackground();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        //connecting to server
        try{
                Socket conn=new Socket("localhost", 1024);
                sockOut=new PrintWriter(conn.getOutputStream(),true);
                BufferedReader sockIn=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Scanner termIn = new Scanner(System.in);

            while (true){
                String fromServer=sockIn.readLine();
                System.out.println(fromServer);//print to this client
                if (fromServer.equals("Now connected, waiting for player 2...")){
                    System.out.println("yurrr");
                    firstFlag = true;
                    panel.add(sidePanel,BorderLayout.EAST);

                }
                if (fromServer.equals("Now connected, Game about to start...") || fromServer.equals("Welcome to CONNECT4: You are player 1")) {
                    System.out.println("yesssssss");
                    secondFlag = true;
                    add(panel);


                    pack();
                    setLocationRelativeTo(null);
                    setVisible(true);
                    remove(startScreen);
                    remove(connScreen);
                }
                //while server sends valid game input, update gui board (with other players move)
                while (fromServer.equals("0")||fromServer.equals("1")||fromServer.equals("2")||fromServer.equals("3")||fromServer.equals("4")||fromServer.equals("5")||fromServer.equals("6")){
                    int move = Integer.parseInt(fromServer);
                     for (int i = buttonGrid.length-1; i >0  ; i--) {
                        if (buttonGrid[i][move].getBackground()==originalColor){
                        buttonGrid[i][move].setBackground(Color.BLUE);
                        break;
                        }
                     }
                     break;
                }
                if (isGameOver(Color.RED)){//player 1(red)
                    JLabel winner = new JLabel("You Win!");
                    panel.add(winner);
                    break;
                }else if (isGameOver(Color.BLUE)){//player 2(blue)
                    JLabel loser = new JLabel("You Lose!");
                    panel.add(loser);
                    break;
                }

            }

        }catch (UnknownHostException e) {
            System.out.println("I think there's a problem with the host name.");
        } catch (IOException e) {
            System.out.println("Had an IO error for the connection.");
        }

    }
    /** This method checks the winning condition for connect 4 and determines
     * which player wins the game. Every single 4 consecutive spots in the matrix is checked.
     * @param player checks if player 1(red) or player 2(blue) wins
     */
    public boolean isGameOver(Color player) {
        //if a player has 4 marks in a row (either horizontal, vertical, or diagonal)
        //checks horizontal win
        for (int row = 1; row < buttonGrid.length; row++) {
            for (int col = 0; col <= buttonGrid[0].length  - 4; col++) {
                if (buttonGrid[row][col].getBackground() == player && buttonGrid[row][col + 1].getBackground() == player && buttonGrid[row][col + 2].getBackground() == player && buttonGrid[row][col + 3].getBackground() == player) {
                    return true;
                }
            }
        }

        //checks vertical win
        for (int row = 1; row <4; row++) {
            for (int col = 0; col < buttonGrid[0].length; col++) {
                if (buttonGrid[row][col].getBackground() == player && buttonGrid[row+1][col].getBackground() == player && buttonGrid[row+2][col].getBackground() == player && buttonGrid[row+3][col].getBackground() == player) {
                    return true;
                }
            }
        }
        //checks descending right diagonal win
        for (int row = 1; row < 4; row++) {
            for (int col = 0; col <4; col++) {
                if (buttonGrid[row][col].getBackground() == player && buttonGrid[row+1][col+1].getBackground() == player && buttonGrid[row+2][col+2].getBackground() == player && buttonGrid[row+3][col+3].getBackground() == player) {
                    return true;
                }
            }
        }
        //checks ascending right diagonal win
        for (int row = 4; row < buttonGrid.length; row++) {
            for (int col = 0; col <4; col++) {
                if (buttonGrid[row][col].getBackground() == player && buttonGrid[row-1][col+1].getBackground() == player && buttonGrid[row-2][col+2].getBackground() == player && buttonGrid[row-3][col+3].getBackground() == player) {
                    return true;
                }
            }
        }
        //checks descending left diagonal win
        for (int row = 1; row < 4; row++) {
            for (int col = 4; col < buttonGrid.length; col++) {
                if (buttonGrid[row][col].getBackground() == player && buttonGrid[row-1][col-1].getBackground() == player && buttonGrid[row-2][col-2].getBackground() == player && buttonGrid[row-3][col-3].getBackground() == player) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public static void main(String[] args) {new Gui();}

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}

