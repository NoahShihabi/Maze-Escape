


import java.util.ArrayList;



// Team member name(s): 
// School: 


public class Runner {
	private static char[][] maze;
	private static char[] moves = {'U', 'D', 'R', 'L'};	// The different possible moves to make
	
	private static ArrayList<Character> steps;
	private static int stepNum;
	private static boolean beenDone;
	private static boolean[][] canDo;
	
	
	public Runner(char[][] theMaze) {
		maze = theMaze;
		beenDone = false;
		stepNum = 0;
		steps = new ArrayList<Character>();
	}
	
	public char nextMove(int row, int col) throws InterruptedException {
		/* Do not modify the above line of code
		 * 
		 * row is the vertical location of the character
		 * col is the horizontal location of the character
		 * You find out what is at position i,j in the maze by calling maze[i][j]
		 * 
		 * This is the method in which you (the contestant) would return the char value that would 
		 * determine what move the maze escapee makes.
		 * 
		 * Input your code below
		 * */
		char nextSpot = 'N';
		

		if (!beenDone) {
			canDo = new boolean[maze.length][maze[0].length];
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[0].length; j++) {
					if (maze[i][j]=='X' || maze[i][j]=='P') {
						canDo[i][j] = false;
					}
					else {
						canDo[i][j]=true;
					}
				}
			}
			
			recur(maze,canDo,row,col,new ArrayList<Character>());
			beenDone = true;
//			System.out.println(steps.size());
			if (steps.size()>0) {
				nextSpot =steps.get(0);
				stepNum++;
			} else {
				
			}
		} else {
			nextSpot = steps.get(stepNum);
//			System.out.println(nextSpot + "P");
			stepNum++;
		}
		return nextSpot;
	}
	
	public static void recur(char[][] zMaze, boolean[][] canGo, int r, int c, ArrayList<Character> s) {
		boolean dead = true;
		if (r>0 && canGo[r-1][c]==true) {
			dead = false;
			ArrayList<Character> nList = newList(s);
//			char[][] nMaze = newCharArray(zMaze);
			boolean[][] go = newBoolArray(canGo);
			nList.add('U');
//			System.out.println("Check U" + r + " " + c);
			if (zMaze[r-1][c]=='E') {
				if (steps.size()==0 || nList.size()<steps.size()) {
					steps = newList(nList);
					return;
				}
				return;
			} else {
//				nMaze[r-1][c]='P';
//				nMaze[r][c]=' ';
				go[r][c]=false;
				recur(zMaze,go,r-1,c,nList);
			}
			
		}
		if (r<(zMaze.length-1) && canGo[r+1][c]) {
			dead = false;
			ArrayList<Character> nList = newList(s);
//			char[][] nMaze = newCharArray(zMaze);
			boolean[][] go = newBoolArray(canGo);
			nList.add('D');
//			System.out.println("Check D" + r + " " + c);
			if (zMaze[r+1][c]=='E') {
				if (steps.size()==0 || nList.size()<steps.size()) {
					steps = newList(nList);
					return;
				}
				return;
			} else {
//				nMaze[r+1][c]='P';
//				nMaze[r][c]=' ';
				go[r][c]=false;
				recur(zMaze,go,r+1,c,nList);
			}
		}
		if (c>0 && canGo[r][c-1]) {
			dead = false;
			ArrayList<Character> nList = newList(s);
//			char[][] nMaze = newCharArray(zMaze);
			boolean[][] go = newBoolArray(canGo);
			nList.add('L');
//			System.out.println("Check L" + r + " " + c);
			if (zMaze[r][c-1]=='E') {
				if (steps.size()==0 || nList.size()<steps.size()) {
					steps = newList(nList);
					return;
				}
				return;
			} else {
//				nMaze[r][c-1]='P';
//				nMaze[r][c]=' ';
				go[r][c]=false;
				recur(zMaze,go,r,c-1,nList);
			}
		}
		if (c<(zMaze[0].length-1) && canGo[r][c+1]) {
			dead = false;
			ArrayList<Character> nList = newList(s);
//			char[][] nMaze = newCharArray(zMaze);
			boolean[][] go = newBoolArray(canGo);
			nList.add('R');
//			System.out.println("Check R" + r + " " + c);
			if (zMaze[r][c+1]=='E') {
				if (steps.size()==0 || nList.size()<steps.size()) {
					steps = newList(nList);
					return;
				}
				return;
			} else {
//				nMaze[r][c+1]='P';
//				nMaze[r][c]=' ';
				go[r][c]=false;
				recur(zMaze,go,r,c+1,nList);
			}
		}
		if (dead) {
			try {
				deadEnd(r, c, s.get(s.size()-1));
			} catch (ArrayIndexOutOfBoundsException e) {
//				System.out.println("here");
			}
		}
	}
	
	public static ArrayList<Character> newList(ArrayList<Character> x) {
		ArrayList<Character> s = new ArrayList<Character>();
		for (Character c : x) {
			s.add(c);
		}
		return s;
	}

	private static void deadEnd(int x, int y, char lastMove) {
		boolean hor = (lastMove=='R' || lastMove=='L');
		if (hor) {
			int val = (lastMove=='R') ? -1 : 1;
			while(true) {
//				System.out.println(lastMove + " " + x + " " + y);
				if (y==-1 || y>=canDo[0].length) return;
				if (!canDo[x][y] || canDo[x-1][y] || canDo[x+1][y]) {
					return;
				} else {
					canDo[x][y] = false;
					y+=val;
				}
			}
		} else {
			int val = (lastMove=='U') ? 1 : -1;
			while(true) {
//				System.out.println(lastMove + " " + x + " " + y);
				if (x==-1 || x>=canDo.length) return;
				if (!canDo[x][y] || canDo[x][y-1] || canDo[x][y+1]) {
					return;
				} else {
					canDo[x][y] = false;
					x+=val;
				}
			}
		}
	}
	
	public static boolean[][] newBoolArray(boolean[][] x) {
		boolean[][] newer = new boolean[x.length][x[0].length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				if (canDo[i][j]==false || x[i][j]==false) {
					newer[i][j] = false;
				} else {
					newer[i][j] = true;
				}
			}
		}
		return newer;
	}

	public static void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
