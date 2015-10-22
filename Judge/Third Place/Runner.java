import java.util.ArrayList;

// Name: 				
// School: 				
// Rank:				Emperor
// Name of Program:		If a human who only had short term memory were trying to complete the maze and couldn't see the entire thing at once so yeah
// Awesomeness Level:	Winning
// Msg to Noah:			I hope you have the capability to speed up these trials because this might take a while


public class Runner {
	private static char[][] maze;
	private static char[] moves = {'U', 'D', 'R', 'L', 'N'};	// The different possible moves to make
	public static char lastMove = 'N';
	public static ArrayList<String> mem = new ArrayList<String>();
	//public static ArrayList<String> mem2 = new ArrayList<String>();
	public static int steps = 0;
	
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
		char nextMove = ' ';
		
		
		
		//get the info
		char l, r, u, d;
		l = 'X';
		r = 'X';
		u = 'X';
		d = 'X';
		if (row != 0) {
			u = maze[row-1][col];
		}
		if (row != maze.length) {
			d = maze[row+1][col];
		}
		if (col != 0) {
			l = maze[row][col-1];
		}
		if (col != maze[row].length) {
			r = maze[row][col+1];
		}
		
		
		//if exit
		if (r == 'E') return 'R';
		if (l == 'E') return 'L';
		if (u == 'E') return 'U';
		if (d == 'E') return 'D';
		
		
		//do the decision making process stuff
		if (l == 'X' || 'L' == lastMove) {
			if (r == 'X' || 'R' == lastMove) {
				if (u == 'X' || 'U' == lastMove) {
					if (d == 'X' || 'D' == lastMove) {
						if ('L' == lastMove) nextMove = 'L';
						else if ('R' == lastMove) nextMove = 'R';
						else if ('D' == lastMove) nextMove = 'D';
						else if ('U' == lastMove) nextMove = 'U';
						else { nextMove = 'N'; }
					}
					else {
						nextMove = 'D';
					}
				}
				else {
					if (d == 'X' || 'D' == lastMove) {
						nextMove = 'U';
					}
					else {
						if (Math.random() < 0.5) nextMove = 'U';
						else { nextMove = 'D'; }
					}
				}
			}
			else {
				boolean up = false;
				boolean down = false;
				if (u != 'X' && 'U' != lastMove) {
					up = true;
				}
				if (d != 'X' && 'D' != lastMove) {
					down = true;
				}
				if (up && down) {
					if (Math.random()< 0.3333333) nextMove = 'U';
					else if (Math.random()< 0.66666667) nextMove = 'D';
					else { nextMove = 'R'; }
				}
				else if (up) {
					if (Math.random() < 0.5) nextMove = 'U';
					else { nextMove = 'R'; }
				}
				else if (down) {
					if (Math.random() < 0.5) nextMove = 'D';
					else { nextMove = 'R'; }
				}
				else { nextMove = 'R'; }
			}
		}
		else {
			boolean up = false;
			boolean down = false;
			boolean right = false;
			if (u != 'X' && 'U' != lastMove) {
				up = true;
			}
			if (d != 'X' && 'D' != lastMove) {
				down = true;
			}
			if (r != 'X' && 'R' != lastMove) {
				right = true;
			}
			if (up && down && right) {
				if (Math.random()< 0.25) nextMove = 'L';
				else if (Math.random()< 0.5) nextMove = 'R';
				else if (Math.random()< 0.75) nextMove = 'U';
				else { nextMove = 'D'; }
			}
			else if (up && down) {
				if (Math.random()< 0.3333333) nextMove = 'U';
				else if (Math.random()< 0.66666667) nextMove = 'D';
				else { nextMove = 'L'; }
			}
			else if (up && right) {
				if (Math.random()< 0.3333333) nextMove = 'U';
				else if (Math.random()< 0.66666667) nextMove = 'R';
				else { nextMove = 'L'; }
			}
			else if (down && right) {
				if (Math.random()< 0.3333333) nextMove = 'D';
				else if (Math.random()< 0.66666667) nextMove = 'R';
				else { nextMove = 'L'; }
			}
			else if (up) {
				if (Math.random() < 0.5) nextMove = 'U';
				else { nextMove = 'L'; }
			}
			else if (down) {
				if (Math.random() < 0.5) nextMove = 'D';
				else { nextMove = 'L'; }
			}
			else if (right) {
				if (Math.random() < 0.5) nextMove = 'R';
				else { nextMove = 'L'; }
			}
			else if (up == false && down == false && right == false) {
				nextMove = 'L'; 
			}
		}
		
		
		
		//the results and stuff
		
		
		steps++;
		String n = "" + nextMove;
		mem.add(n);
		if (mem.size() > 20) {
			mem.remove(0);
		}
		if (steps>=20) {
			boolean pathAgain = true;
			int topCount = 19;
			for (int i = 0; i < 10; i++) {
				if (!checkReverse(mem.get(i), mem.get(topCount))) {
					pathAgain = false;
				}
				topCount--;
			}
			if (pathAgain == true) {
				nextMove = returnReverse(nextMove);
			}
		}
		
		if (nextMove == 'U') lastMove = 'D';
		else if (nextMove == 'D') lastMove = 'U';
		else if (nextMove == 'L') lastMove = 'R';
		else if (nextMove == 'R') lastMove = 'L';
		
		
		if (steps == 299) {
			System.out.println("Success in -1/12 moves!");
			System.out.println("Total runtime: 23 hours yesterday\n(Disregard the following two statements)");
		}
//		return maze[maze.length+1][maze.length];
		return nextMove;
	}
	
	public boolean checkReverse(String a, String b) {
		if (a.equals("L") && b.equals("R")) return true;
		if (a.equals("R") && b.equals("L")) return true;
		if (a.equals("U") && b.equals("D")) return true;
		if (a.equals("D") && b.equals("U")) return true;
		return false;
	}
	public char returnReverse(char a) {
		if (a == 'L') return 'R';
		if (a == 'R') return 'L';
		if (a == 'U') return 'D';
		if (a == 'D') return 'U';
		return 'N';
	}
	
}
