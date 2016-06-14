package hw2;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

public class Node {
	/*
	 * Wrapper for game states as well as keeping track of bookkeeping (parent, children, value, alpha, beta).
	 */

	private int value;
	private Node parent;
	private Board currentState;
	private List<Node> children = new ArrayList<>();
	private int move;
	private boolean isMaxNode;
	
	Node(Node parent,Board currentState){
		this.currentState = currentState;
		this.parent       = parent; 
		this.move         = currentState.getMove();
	}
	
	Node(Board currentState){
		this.currentState = currentState;
		this.parent       = null;
		this.value        = Integer.MIN_VALUE;
		this.isMaxNode    = true;
		
		for(Board b:currentState.genMaxSuccessors()){
			children.add(new Node(this,b));
		}
	}
		
	Board getCurrentState(){
		return currentState;
	}
	
	Node getParent(){
		return parent;
	}
	
	int getValue(){
		currentState.calculateScoreForBoard();
		return currentState.getUtilValue();
	}
	
	void setValue(int newValue){
		this.value = newValue;
	}
	
	List<Node> getChildren(){
		if(parent == null){
			return children;
		}
		if(parent.isMaxNode == true){
			this.isMaxNode = false;
			for(Board b:currentState.genMinSuccessors()){
				children.add(new Node(this,b));
			}
			
		}
		else{
			this.isMaxNode = true; 
			for(Board b:currentState.genMaxSuccessors()){
				children.add(new Node(this,b));
			}
		}
		return children;
	}
	
	int getMove(){
		return move;
	}
	
	public String toString(){
		return currentState.toString();
	}
}
