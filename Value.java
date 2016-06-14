package hw2;

public class Value {
	/*
	 * Wrapper class so that miniMax can keep track of the score and the move associated with that score. 
	 */
	
	private int score;
	private int move;
	
	public Value(int value, int move){
		this.score = value;
		this.move = move;
	}
	public int getValue(){
		return score;
	}
	public int getMove(){
		return move;
	}
	
	public void setMove(int move){
		this.move = move;
	}
	public void setVal(int value){
		this.score = value;
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append('[');
		b.append("score: " +this.score);
		b.append(", ");
		b.append("move: "+this.move +']');
		return b.toString();
	}
}
