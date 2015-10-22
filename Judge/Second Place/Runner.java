

// Name: Joseph Dvorak



//yeah it's quick n dirty, but it works and was created with limited time.

//Possible future improvements: 1.) block off path behind David when in an intersection.
//					   			2.) look down intersections to avoid short dead ends.
//					   			3.) follow all possible paths to the end at the beginning and loop through arrays


public class Runner {
	private static char[][] maze;
	//private static char[] moves = {'U', 'D', 'R', 'L', 'N'};	// The different possible moves to make
	private char lastMove = ' '; //For storing last choice
	
	// This class will only be instantiated once during the entire execution
	// The method nextMove() is called each time for David to move 
	public Runner(char[][] theMaze) {
		maze = theMaze;
	}
	
	public char nextMove(int row, int col) throws InterruptedException {
		/* Do not modify the above line of code
		 * 
		 * row is the vertical location of the character
		 * col is the horizontal location of the character
		 * You find out what is at position i,j in the maze by calling maze[i][j]
		 * 'X' = Wall
		 * 'P' = The player
		 * ' ' = Open space
		 * 'E' = The end
		 * 
		 * This is the method in which you (the contestant) would return the char value 
		 * (nextSpot)that would determine what move the maze escapee makes.
		 * 
		 * 
		 * 
		 * Input your code below
		 * */
		char nextSpot = ' ';
		
		//First check for exit
		if (maze[row][col+1] == 'E') nextSpot = 'R';
		else if (maze[row+1][col] == 'E') nextSpot = 'D';
		else if (col > 0 && maze[row][col-1] == 'E') nextSpot = 'L';
		else if (maze[row-1][col] == 'E') nextSpot = 'D';
		
		else {
			//Now choose path in ranked order
			if (maze[row][col+1] == ' ' && lastMove != 'L') {
				nextSpot = 'R';
				lastMove = 'R';
			}
			else if (maze[row+1][col] == ' ' && lastMove != 'U') {
				nextSpot = 'D';
				lastMove = 'D';
			}
			else if (col > 0 && maze[row][col-1] == ' ' && lastMove != 'R') {
				nextSpot = 'L';
				lastMove = 'L';
			}
			else if (maze[row-1][col] == ' ' && lastMove != 'D') {
				nextSpot = 'U';
				lastMove = 'U';
			}
			else { //This is if we're in a dead end
				if (maze[row][col+1] == ' ') {
					nextSpot = 'R';
					lastMove = 'R';
				}
				else if (maze[row+1][col] == ' ') {
					nextSpot = 'D';
					lastMove = 'D';
				}
				else if (col > 0 && maze[row][col-1] == ' ') {
					nextSpot = 'L';
					lastMove = 'L';
				}
				else if (maze[row-1][col] == ' ') {
					nextSpot = 'U';
					lastMove = 'U';
				}
			}
		}
		
		
		return nextSpot;
	}

	
}
