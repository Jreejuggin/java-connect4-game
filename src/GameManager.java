/** This Class Manages the state of Connect 4 game
 */
public class GameManager {
    public int[][] gameBoard;

    public GameManager(){

    }
    /** This method creates an empty 7x7 int 2d array. This is the game board
     * the top row is used to store the column numbers
     */
    public void setupGame(){
        gameBoard = new int[7][7];
        for (int col = 0; col<gameBoard.length; col++){
            gameBoard[0][col] = col;
        }
        for (int i = 1; i < gameBoard.length; i++) {
            for (int j=0; j< gameBoard[0].length;j++){
            gameBoard[i][j] = 0;
            }
        }
    }
    /** This method updates the board when a player makes a play.
     * this is where the game logic is implemented.
     * @param player whichever players turn it is
     * @param move the column they want to drop their 'token' in
     */
    public void update(int player, int move) {
        // if player1 move, place player number at the lowest available spot in the selected column
        if(player == 1){
            for (int i = gameBoard.length-1; i >0  ; i--) {
                if (gameBoard[i][move]==0){
                    gameBoard[i][move] = 1;
                    return;
                }
            }
        } else {//player 2
            for (int i = gameBoard.length-1; i >0  ; i--) {
                if (gameBoard[i][move]==0){
                    gameBoard[i][move] = 2;
                    return;
                }
            }
        }
    }
    /** This method checks the winning condition for connect 4 and determines
     * which player wins the game. Every single 4 spots in a row in the matrix is checked.
     * @param player checks if player 1 or player 2 wins
     */
    public boolean isGameOver(int player) {
        //if a player has 4 marks in a row (either horizontal, vertical, or diagonal)
        //checks horizontal win
        for (int row = 1; row < gameBoard.length; row++) {
            for (int col = 0; col <= gameBoard[0].length  - 4; col++) {
                if (gameBoard[row][col] == player && gameBoard[row][col + 1] == player && gameBoard[row][col + 2] == player && gameBoard[row][col + 3] == player) {
                    return true;
                }
            }
        }

        //checks vertical win
        for (int row = 1; row <4; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] == player && gameBoard[row+1][col] == player && gameBoard[row+2][col] == player && gameBoard[row+3][col] == player) {
                    return true;
                }
            }
        }
        //checks descending right diagonal win
        for (int row = 1; row < 4; row++) {
            for (int col = 0; col <4; col++) {
                if (gameBoard[row][col] == player && gameBoard[row+1][col+1] == player && gameBoard[row+2][col+2] == player && gameBoard[row+3][col+3] == player) {
                    return true;
                }
            }
        }
        //checks ascending right diagonal win
        for (int row = 4; row < gameBoard.length; row++) {
            for (int col = 0; col <4; col++) {
                if (gameBoard[row][col] == player && gameBoard[row-1][col+1] == player && gameBoard[row-2][col+2] == player && gameBoard[row-3][col+3] == player) {
                    return true;
                }
            }
        }
        //checks descending left diagonal win
        for (int row = 1; row < 4; row++) {
            for (int col = 4; col < gameBoard.length; col++) {
                if (gameBoard[row][col] == player && gameBoard[row-1][col-1] == player && gameBoard[row-2][col-2] == player && gameBoard[row-3][col-3] == player) {
                    return true;
                }
            }
        }

    return false;
    }
    /** This method checks if the game ends in a tie.
     */
    public boolean isDraw(){
        //loops through all spots in the 2d array, if no spots are empty it is a draw
        for (int row=0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if(gameBoard[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }
}
