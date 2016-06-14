package hw2;

import java.util.List;

class MiniMax {
	private Board currentboard; 
	private int maxDepth;
	private int bestValue;
	private int move;
	private List<Node> children;

	MiniMax(Board newBoard, int maxDepth) {
		currentboard  = newBoard;
		this.maxDepth = maxDepth;

	}
	/*
	 * Performs miniMax search with alphaBeta pruning. 
	 * Recursively builds game tree until depth limit is reached, then evaluates nodes.  
	 */
	private int alphaBetaSearch(Node currentNode, int depth,int alpha,int beta, boolean maxPlayer){

		if (depth == 0){

			return bestValue = currentNode.getValue();
		}

		if(maxPlayer){

			bestValue = Integer.MIN_VALUE;

			for(Node child:currentNode.getChildren()){
				bestValue = Integer.max(bestValue, alphaBetaSearch(child, depth-1, alpha, beta, false));
				currentNode.setValue(bestValue);
				alpha = Integer.max(alpha, bestValue);

				if(beta<= alpha) 
					break;

			}
			return bestValue;
		}

		else{

			bestValue = Integer.MAX_VALUE;

			for(Node child:currentNode.getChildren()){
				bestValue = Integer.min(bestValue, alphaBetaSearch(child, depth-1, alpha, beta, true));
				currentNode.setValue(bestValue);
				beta = Integer.min(beta, bestValue);

				if (beta<=alpha) 
					break;

			}
			return bestValue;
		}

	}
	/*
	 * Performs miniMax search (no pruning). 
	 * Recursively builds game tree until depth limit is reached, then evaluates nodes.  
	 */
	private int miniMaxSearch(Node currentNode, int depth, boolean maxPlayer){

		if (depth == 0){

			return bestValue = currentNode.getValue();
		}

		if(maxPlayer){

			bestValue = Integer.MIN_VALUE;

			for(Node child:currentNode.getChildren()){
				bestValue = Integer.max(bestValue, miniMaxSearch(child, depth-1, false));
				currentNode.setValue(bestValue);
			}
			return bestValue;
		}

		else{

			bestValue = Integer.MAX_VALUE;

			for(Node child:currentNode.getChildren()){
				bestValue = Integer.min(bestValue, miniMaxSearch(child, depth-1, true));
				currentNode.setValue(bestValue);

			}
			return bestValue;
		}

	}
	
	/*
	 * Waits for alphaBetaSearch or miniMaxSearch to finish running and then acts on its suggestion. 
	 */
	void makeMove(int search){
		Node root = new Node(currentboard);
		if(search == 1)
			alphaBetaSearch(root, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		else if(search == 2){
			miniMaxSearch(root, maxDepth, true);
		}
		else {
			System.err.println("No algorithm chosen!");
		}
		children = root.getChildren();
		Node max = children.get(0);
		move = max.getMove();
		for(int i=1; i<children.size(); i++){
			if(children.get(i).getValue()>max.getValue()){
				max  = children.get(i);
				move = max.getMove();
			}
		}

		if(currentboard.isValidMove(move)){
			currentboard.movePieceOnBoard(move,false);
		}
		else{
			System.out.println("This shouldn't happen!");
			System.exit(1);
		}
	}
}
