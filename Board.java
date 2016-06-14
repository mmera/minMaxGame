package hw2;

import java.util.ArrayList;

class Board {
	private byte[][] board = new byte[SIZE][SIZE];
	
	private final static int  SIZE   = 9;
	private final static byte BLANK  = 0;
	private final static byte PLAYER = 1;
	private final static byte AI     = 2;
	
	

	private int playerScore = 0;
	private int aiScore     = 0;
	private int utilValue   = 0;
	private int move;
	

	Board(){};
	
	Board(byte[][] newBoard, int move) {
		this.board = newBoard;
		this.move = move; 
	}

	/*
	 * Generates successors for all possible max and min states respectively, from the current state.
	 * Uses the dropChip method to make the hypothetical moves and generate the potential Boards.   
	 * Stores in ArrayList.
	 */
	
	ArrayList<Board> genMaxSuccessors(){
		ArrayList<Board> successors = new ArrayList<>();
		for(int col=0; col<SIZE; col++){
			if(isValidMove(col)){
				successors.add(dropChip(col, false));
			}
		}
		return successors;
	}
	ArrayList<Board> genMinSuccessors(){
		ArrayList<Board> successors = new ArrayList<>();
		for(int col=0; col<SIZE; col++){
			if(isValidMove(col)){
				successors.add(dropChip(col, true));
			}
		}
		return successors;
	}

	/*
	 * Checks to see if the top of the column is occupied meaning there 
	 * is no more room left in that column. 
	 */
	boolean isValidMove(int column){
		if(column >= SIZE || column<0)
			return false;
		
		if(board[0][column]!=BLANK)
			return false;
			
		else
			return true;
	}


	/*
	 * Returns an updated board with the new chip in place. 
	 * Used to generate new states, not for actual moves taking place. 
	 */
	private Board dropChip(int column, boolean player){
		byte[][] newBoard = new byte[SIZE][SIZE];
		Board temp;
		newBoard = this.copy();
		if(board[SIZE-1][column]==BLANK)
			if(player == true){
				newBoard[SIZE-1][column] = PLAYER;
			}
			else{
				newBoard[SIZE-1][column] = AI;
			}
		else{
			for(int row=1; row<SIZE; ++row){
				if(board[row][column]!= BLANK && board[row-1][column]==BLANK){
					if(player == true){
						newBoard[row-1][column] = PLAYER;
						break;
					}
					else{
						newBoard[row-1][column] = AI;
						break;
					}
				}

			}
		}
		//Giving a bonus point for moving towards the middle. 
		temp = new Board(newBoard,column);
		if( (SIZE/2)-1 <= column && column <= (SIZE/2)+1){
			if(player){
				temp.setPlayerScore(temp.getPlayerScore()+1);
				temp.setUtilVal(temp.getAiScore()-temp.getPlayerScore());
		}
			else{
				temp.setAiScore(temp.getAiScore()+1);
				temp.setUtilVal(temp.getAiScore()-temp.getPlayerScore());
			}
		}
		return temp;

	}

	/*
	 * Used when the player moves or when the AI is done deciding where it wants to move.
	 */
	 void movePieceOnBoard(int column, boolean player){
		if(board[SIZE-1][column]==BLANK)
			if(player == true){
				board[SIZE-1][column] = PLAYER;
			}
			else{
				board[SIZE-1][column] = AI;
			}
		else{
			for(int row=1; row<SIZE; ++row){
				if(board[row][column]!= BLANK){
					if(player == true){
						board[row-1][column] = PLAYER;
						break;
					}
					else{
						board[row-1][column] = AI;
						break;
					}
				}
			}
		}
	}



	private void calculateScoreForRow(){
		for(int row=0; row<SIZE; ++row){
			for(int col=1; col<SIZE; ++col){
				if(board[row][col] != BLANK){
					if (board[row][col]==PLAYER){
						if (board[row][col] == board[row][col-1]){
							playerScore+=2;
						}
					}
					else{
						if(board[row][col]== board[row][col-1]){
							aiScore+=2;
						}
					}
				}
			}

		}
	}
	private void calculateScoreForColumn(){
		for(int col = 0; col<SIZE; ++col){
			for(int row = 1; row<SIZE; ++row){
				if(board[row][col]!= BLANK){
					if(board[row][col]==PLAYER){
						if(board[row][col]==board[row-1][col]){
							playerScore+=2;
						}
					}
					else{
						if(board[row][col]==board[row-1][col]){
							aiScore+=2;
						}
					}
				}
			}
		}
	}
	private void calculateScoreForDiagonals(){
		for(int row=0; row<SIZE-1; ++row){
			for(int col=1; col<SIZE; ++col){
				if(board[row][col-1]!= BLANK){
					if(board[row][col-1]==PLAYER){
						if(board[row][col-1]==board[row+1][col]){
							playerScore++;
						}
					}
					else{
						if(board[row][col-1]==board[row+1][col]){
							aiScore++;
						}
					}
				}
			}
		}
		for(int row=0; row<SIZE-1; ++row){
			for(int col=1; col<SIZE; ++col){
				if(board[row][col]!= BLANK){
					if(board[row][col]==PLAYER){
						if(board[row][col]==board[row+1][col-1]){
							playerScore++;
						}
					}
					else{
						if(board[row][col]==board[row+1][col-1]){
							aiScore++;
						}
					}
				}
			}
		}

	}

	/*
	 * Searches all scoring possibilities to calculate the final score for the board.
	 * Used when instantiating a board and when the getWinner() method is called.
	 */
	 void calculateScoreForBoard(){
		calculateScoreForColumn();
		calculateScoreForRow();
		calculateScoreForDiagonals();
		utilValue = aiScore - playerScore;
	}

	/*
	 *Self-explanatory: checks the tops of all columns to ensure they are all full. 
	 */
	boolean isBoardFull(){
		boolean colFull = false;
		for(int i=0; i<SIZE; i++){
			if(board[0][i]!=BLANK){
				colFull = true;
			}
			else{
				colFull = false;
				break;
			}
		}
		return colFull;
	}

	/*
	 * When the board is full, it will calculate who has the highest score 
	 * and return a winner. 
	 */
	byte getWinner(){
		calculateScoreForBoard();
		if(isBoardFull()){
			if (aiScore>playerScore){
				return AI;
			}
			else if(aiScore < playerScore) 
				return PLAYER;
			
			else if (aiScore == playerScore)
				return 0;
		}
		return 0;
	}

	/*
	 * Helper method. Function is self-explanatory.
	 * Used in dropChip() method to ensure separate copies made. 
	 */
	private byte[][] copy(){
		byte[][] copy = new byte[SIZE][SIZE];
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE;j++){
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}
	/*
	 * Getters, setters, etc...
	 */

	int getMove(){
		return move;
	}

	byte[][] getBoard(){
		return board;
	}
	int getAiScore() {
		return aiScore;
	}
	int getPlayerScore() {
		return playerScore;
	}
	void setAiScore(int score){
		this.aiScore = score;
	}

	void setPlayerScore(int score){
		this.playerScore = score; 
	}

	void setUtilVal(int val){
		this.utilValue = val;
	}
	int getUtilValue() {
		return utilValue;
	}

	@Override
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append('\n');
		for (int x = 0; x < SIZE; x++) {
			result.append((x) + " ");
		}
		result.append(System.lineSeparator());
		for (int row = 0; row < SIZE; row++) {
			for (int col=0;col<SIZE;++col) {
				
				if (board[row][col] == PLAYER) {
					result.append("O ");
				} else if (board[row][col] == AI) {
					result.append("X ");
				} else {
					result.append(". ");
				}
			}
			result.append(System.lineSeparator());
		}
		return result.toString();
	}	
}
