package hw2;

import java.util.Scanner;

class Main {

	public static void main(String[] args){
		System.out.println("Welcome to Simacongo!");
		System.out.println("Please Select Depth of Tree: ");
		System.out.println("(Note: Increasing the depth, increases the difficulty of the game)");
		
		Scanner in = new Scanner(System.in);  
		while(!in.hasNextInt()){
			in.nextLine();
			System.out.print("Please enter an integer!\n"); 
		}
		int maxDepth = in.nextInt(); 
		while (maxDepth<1 || maxDepth>8){
			System.out.print("Please enter an integer between 1 and 8!\n");
			maxDepth = in.nextInt();
		}
		
		System.out.println();
		System.out.println("Which algorithm do you want to run?");
		System.out.println("1. Alpha-Beta Search");
		System.out.println("2. Minimax Search");
		int  search  = in.nextInt(); 
		
		Board board  = new Board();
		MiniMax ai   = new MiniMax(board,maxDepth);
		boolean turn = true;
		int col;
		//The game driver
		while(!board.isBoardFull()){
			System.out.println(board.toString());
			if(turn){
				do{
					System.out.println("Which column do you want to drop your chip?");
					while(!in.hasNextInt()){
						in.nextLine();
						System.out.print("Please enter an integer!\n"); 
					}
					col = in.nextInt();
					if(!board.isValidMove(col)){
						System.out.println("You can't move there...try again!\n");
					}
				}while(!board.isValidMove(col));
				board.movePieceOnBoard(col, true);;
			}
			else {
				ai.makeMove(search);
			}
			turn = !turn;
		}
		//Returns the winner and prints out the final score
		byte winner = board.getWinner();
		System.out.println(board.toString());
		System.out.println("AI: " + board.getAiScore() + " | Player: " + board.getPlayerScore());
		
		if (winner == 1){
			System.out.println("Congratulations! You Won!");
		}
		else if (winner == 2){
			System.out.println("Sorry you lose! Play again!");
		}
		else {
			System.out.println("Tie Game! Play again");
		}
		in.close();
	}

}

