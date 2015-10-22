

// Name: 
// School: 


public class Runner {
	private static char[][] maze;
	private static char[] moves = {'U', 'D', 'R', 'L', 'N'};// The different possible moves to make
	private static char lastMove= 'D';
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
		char nextSpot='R';
		
		
		if(maze[row][col+1] != 'X' && lastMove !='L')
		{
			
			lastMove='R';
			return 'R';
		}
		if(maze[row+1][col] != 'X' && lastMove !='U')
		{
			
			lastMove='D';
			return 'D';
		}
		
		if(col !=0 && maze[row][col-1]!='X' && lastMove !='R')
		{
			
			lastMove='L';
			return 'L';
		}
	
		
		if( row !=0 && maze[row-1][col] != 'X' && lastMove !='D')
		{

			lastMove='U';
			return 'U';
		}
		if(maze[row][col-1] !='X' && lastMove=='D')
		{
			lastMove='L';
			return 'L';
		}

		if(maze[row][col-1]=='X' && maze[row][col+1]=='X' && maze[row-1][col]=='X')
		{
			lastMove='D';
			return 'D';
		}
		if(maze[row+1][col]=='X' && maze[row][col+1]=='X' && maze[row-1][col]=='X')
		{
			lastMove='L';
			return 'L';
		}
		if(maze[row][col-1]=='X' && maze[row+1][col]=='X' && maze[row-1][col]=='X')
		{
			lastMove='R';
			return 'R';
		}
		if(maze[row][col-1]=='X' && maze[row][col+1]=='X' && maze[row+1][col]=='X')
		{
			lastMove='U';
			return 'U';
		}
	
			
		
						
			
			
		
		
		return nextSpot;
	}
	

}

