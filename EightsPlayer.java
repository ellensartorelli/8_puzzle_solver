import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;


/*
 * Solves the 8-Puzzle Game (can be generalized to n-Puzzle)
 */

public class EightsPlayer {

	static Scanner scan = new Scanner(System.in);
	static int size=3; //size=3 for 8-Puzzle
	static int numiterations = 100;
	static int numnodes; //number of nodes generated
	static int nummoves; //number of moves required to reach goal
	static int nummovesTotal;


	public static void main(String[] args)
	{
		int boardchoice = getBoardChoice();
		int algchoice = getAlgChoice();

		int numsolutions = 0;

		Node initNode;

		if(boardchoice==0)
			numiterations = 1;

		for(int i=0; i<numiterations; i++){

			if(boardchoice==0)
				initNode = getUserBoard();
			else
				initNode = generateInitialState();//create the random board for a new puzzle

			boolean result=false; //whether the algorithm returns a solution

			switch (algchoice){
				case 0:
					result = runBFS(initNode); //BFS
					break;
				case 1:
					result = runAStar(initNode, 0); //A* with Manhattan Distance heuristic
					break;
				case 2:
					result = runAStar(initNode, 1); //A* with your new heuristic
					break;
			}


			//if the search returns a solution
			if(result){

				numsolutions++;


				System.out.println("Number of nodes generated to solve: " + numnodes);
				System.out.println("Number of moves to solve: " + nummoves);
				System.out.println("Number of solutions so far: " + numsolutions);
				System.out.println("_______");

			}
			else
				System.out.print(".");

		}//for



		System.out.println();
		System.out.println("Number of iterations: " +numiterations);

		if(numsolutions > 0){
			System.out.println("Average number of moves for "+numsolutions+" solutions: "+nummoves/numsolutions);
			System.out.println("Average number of nodes generated for "+numsolutions+" solutions: "+numnodes/numsolutions);
		}
		else
			System.out.println("No solutions in "+numiterations+" iterations.");

	}


	public static int getBoardChoice()
	{

		System.out.println("single(0) or multiple boards(1)");
		int choice = Integer.parseInt(scan.nextLine());

		return choice;
	}

	public static int getAlgChoice()
	{

		System.out.println("BFS(0) or A* Manhattan Distance(1) or A*Divide And Conquer>(2)");
		int choice = Integer.parseInt(scan.nextLine());

		return choice;
	}


	public static Node getUserBoard()
	{

		System.out.println("Enter board: ex. 012345678");
		String stbd = scan.nextLine();

		int[][] board = new int[size][size];

		int k=0;

		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				//System.out.println(stbd.charAt(k));
				board[i][j]= Integer.parseInt(stbd.substring(k, k+1));
				k++;
			}
		}


		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				//System.out.println(board[i][j]);
			}
			System.out.println();
		}


		Node newNode = new Node(null,0, board);

		return newNode;


	}




	/**
	 * Generates a new Node with the initial board
	 */
	public static Node generateInitialState()
	{
		int[][] board = getNewBoard();

		Node newNode = new Node(null,0, board);

		return newNode;
	}


	/**
	 * Creates a randomly filled board with numbers from 0 to 8.
	 * The '0' represents the empty tile.
	 */
	public static int[][] getNewBoard()
	{

		int[][] brd = new int[size][size];
		Random gen = new Random();
		int[] generated = new int[size*size];
		for(int i=0; i<generated.length; i++)
			generated[i] = -1;

		int count = 0;

		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				int num = gen.nextInt(size*size);

				while(contains(generated, num)){
					num = gen.nextInt(size*size);
				}

				generated[count] = num;
				count++;
				brd[i][j] = num;
			}
		}

		/*
		//Case 1: 12 moves
		brd[0][0] = 1;
		brd[0][1] = 3;
		brd[0][2] = 8;

		brd[1][0] = 7;
		brd[1][1] = 4;
		brd[1][2] = 2;

		brd[2][0] = 0;
		brd[2][1] = 6;
		brd[2][2] = 5;
		*/

		return brd;

	}

	/**
	 * Helper method for getNewBoard()
	 */
	public static boolean contains(int[] array, int x)
	{
		int i=0;
		while(i < array.length){
			if(array[i]==x)
				return true;
			i++;
		}
		return false;
	}


	/**
	 * TO DO:
     * Prints out all the steps of the puzzle solution and sets the number of moves used to solve this board.
     */
    public static void printSolution(Node node) {
    	

    	//make array list to store the path from the goal node to the root node
			ArrayList<Node> path = new ArrayList<Node>();
			
		//fill path array by adding each node and it's parent into the array until parent == null (root node)
			while(node.getparent() != null){
				path.add(node);
				node = node.getparent();
			}
		//add the root node that does not have a parent into array
			path.add(node);

		//iterate BACKWARDS (root to goal) through path and print the board for each step
			for(int i = path.size()-1; i >= 0; i--){
				System.out.println();
				path.get(i).print(path.get(i));
			}
		//set the number of moves to the path size minus 1 (minus 1 for root node/initial state)
			nummoves += path.size()-1;
//			nummovesTotal += nummoves;
    }




	/**
	 * TO DO:
	 * Runs Breadth First Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 * 
	 * BFS PASSES ALL TEST CASES
	 * 
	 */
	public static boolean runBFS(Node initNode)
	{
		//initialize frontier and explored
		Queue<Node> Frontier = new LinkedList<Node>();
		ArrayList<Node> Explored = new ArrayList<Node>();
		int maxDepth = 13;
		
		//add initial node to frontier and increment numNodes
		Frontier.add(initNode);
		numnodes++;
		
		
		//while frontier is not empty
		while(Frontier.peek() != null){
			//remove the first node to explore, mark as current
			Node current = Frontier.remove();
			//move current node to Explored
			Explored.add(current);
			
			//if we have found the goal, return it
			if(current.isGoal()){
				printSolution(current);
				return true;
			}
			//if we have exceeded max depth, return no solution
			else if(current.getdepth() > maxDepth){
//				System.out.println("Exceeded max depth");
				return false;
			}
			// if we have not reached goal or exceeded max depth, expand the node
			else{
				//expand node save all of the potential board states into array, boardOptions
				ArrayList<int[][]> boardOptions = current.expand();
				
				//get depth for all options: current/parent depth + 1
				int depth = current.getdepth() + 1;
				
				//iterate through array of board options and check to see if the board has already been seen
				//if the board has been seen, addNode bool will be set to false and it will not be added into the Frontier
				Boolean addNode = true;
				//for each boardOption called temp.... 
				for(int i = 0; i < boardOptions.size(); i++){
					int[][] temp = boardOptions.get(i);
					//if potential state has already been explored (if the board is equal to a board in Explored)
					for(Node exploredNode : Explored){
						if(Arrays.deepEquals(exploredNode.getboard(), temp)){
							//don't add state to Frontier
							addNode = false;
						}
					}
					//if state has not been explored
					if(addNode == true){
					//make node out of the board option
						Node option = new Node(current, depth, temp);
					//add new node to frontier
						Frontier.add(option);
					//increment num nodes
						numnodes++;
					}
					//reset add nodes to true as we continue to check the children of current
					addNode=true;
				}
			}
		}
	return false;
	}//BFS



	/***************************A* Code Starts Here ***************************/

	/**
	 * TO DO:
	 * Runs A* Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 * heuristic = 0 for Manhattan Distance, heuristic = 1 for your new heuristic
	 */
	public static boolean runAStar(Node initNode, int heuristic)
	{
 		
		//initialize Frontier priority queue
		//comparator defined as anonymous function
		 PriorityQueue<Node> Frontier = new PriorityQueue<Node>(1, new Comparator<Node>(){
			 public int compare (Node n1, Node n2){
//					System.out.println("Node 1: " + (n1.getgvalue() + n1.gethvalue()));
//					System.out.println("Node 2: " + (n2.getgvalue() + n2.gethvalue()));
				if(n1.getgvalue() + n1.gethvalue() < n2.getgvalue() + n2.gethvalue()){
			//System.out.println("\n returning n1");
					return -1;
				}
				else if(n1.getgvalue() + n1.gethvalue() == n2.getgvalue() + n2.gethvalue()){
					//System.out.println("\n tie???");
					return 0;
				}
				else{
					//System.out.println("\n returning n2");
					return 1;
				}
			 }
		 });
			 
		 //initialize Explored as queue
		Queue<Node> Explored = new LinkedList<Node>();
		int maxDepth = 13;
		
		//set fn for init, added to frontier and increment numnodes... 
		initNode.setgvalue(0);
		if(heuristic == 0){
			initNode.sethvalue(initNode.getManhattanDistanceSum());
		}else{
			initNode.sethvalue(initNode.divideAndConquerHueristic());
		}
		Frontier.add(initNode);
		numnodes++;
		
		int count = 0;

		//while frontier is not empty
		while(!Frontier.isEmpty()){
			//remove first node and set as current
			//System.out.print("frontier before: [");
//			for(Node x: Frontier){
//				System.out.print((x.getgvalue()+x.getManhattanDistanceSum()) +", ");
//			}
//			System.out.print("] \n");
			Node current = Frontier.poll();
//			System.out.print("frontier after: [");
//			for(Node x: Frontier){
//				System.out.print((x.getgvalue()+x.getManhattanDistanceSum()) +", ");
//			}
//			System.out.print("] \n");
			//add current to explore
			Explored.add(current);
//			System.out.println("Current pop "+"("+current.getgvalue()+", "+current.gethvalue()+")");
//			current.print(current);

			// if current is goal, return true
			if(current.isGoal()){
				printSolution(current);
				return true;
			}
			//if current's depth is greater than 13, return false
			else if(current.getdepth() > maxDepth){
//				System.out.println("Exceeded max depth");
				return false;
			}
			//if we have neither found the goal nor exceeded max depth
			else{
				// save all of the potential board states into array
				ArrayList<int[][]> boardOptions = current.expand();
				// get depth for all potential board states nodes
				int depth = current.getdepth() + 1;
				
				//iterate through array of board options and check to see if the board has already been seen
				//if the board has been seen, addNode bool will be set to false and it will not be added into the Frontier
				Boolean addNode = true;
				for(int i = 0; i < boardOptions.size(); i++){
					int[][] temp = boardOptions.get(i);
					//for each boardOption called temp.... 
					for(Node exploredNode : Explored){
						if(Arrays.deepEquals(exploredNode.getboard(), temp)){
//							System.out.println("repeats in explored");
							//don't add state to Frontier if the board is already in Explored
							addNode = false;
						}
					}
					//if state has not been explored
					if(addNode == true){
					//create a node for the state
						Node option = new Node(current, depth, temp);
					//set the states g value
						option.setgvalue(current.getgvalue()+1);
//						System.out.print("current ");
//						System.out.println(current.getgvalue()+1);
//						System.out.print("depth ");
//						System.out.println(depth);
//						System.out.println("heuristic "+heuristic);
					//set the h value depending on the heuristic chosen
						if(heuristic==0){
							option.sethvalue(option.getManhattanDistanceSum()); //double check 
//							System.out.println("mds "+option.getManhattanDistanceSum());
						}else{
							option.sethvalue(option.divideAndConquerHueristic());
						}
						
						// Check frontier to see if option already exists in frontier
						// if option already exits in frontier, then don't add option but instead update the fn
						boolean addToFrontier = true;
						for(Node frontierNode : Frontier){
							//if the node's board is in the frontier... 
							if(Arrays.deepEquals(option.getboard(), frontierNode.getboard())){
//								System.out.println("repeats in frontier");
								//if the node has a lower fn than the node in the frontier with the same board...
								if(option.getgvalue()+option.gethvalue() < frontierNode.getgvalue()+frontierNode.gethvalue()){
									//then reset the g and h of the frontier node to the g and h of the current node
									frontierNode.setgvalue(option.getgvalue());
									frontierNode.sethvalue(option.gethvalue());
									//reset the parent
									frontierNode.setparent(current);
									// do not add the current node to the Frontier bc it's board is already in there and we updated the g and h
									addToFrontier = false;
								}
							}
						}
						
						//if the current node is NOT in the frontier
						if(addToFrontier == true){
//							if(option.isGoal()){
//								System.out.println("added goal to frontier");
//								
//								printSolution(option);
//								return true;
//							}
							Frontier.add(option);
//							System.out.println("num nodes being incremented after adding node: "+numnodes);
							numnodes++;
						}
					
												
//						System.out.println(count++);
//						System.out.print("[");
//						for(Node x: Frontier){
//							System.out.println(("("+x.getgvalue()) +", "+x.gethvalue()+")");
//							x.print(x);
//						}
//						System.out.print("] \n");
						

					}
					addNode=true;
				}
			}
		}
	return false;
	}

}
