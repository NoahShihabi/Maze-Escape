import java.util.ArrayDeque;

// Name: 
// School: 


public class Runner {
	private static char[][] maze;
	private static char[] moves = {'U', 'D', 'R', 'L', 'N'};	// The different possible moves to make
	static boolean found;
	static ArrayDeque<Character> ans;
	// This class will only be instantiated once during the entire execution
	// The method nextMove() is called each time for David to move 
	public Runner(char[][] theMaze) {
		found = false;
		maze = theMaze;
		ans = new ArrayDeque<Character>();
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
		
		if (!found) {
			boolean[][] been = new boolean[maze.length][maze[0].length];
			int[][] places = new int[maze.length][maze[0].length];
			int eR = 0;
			int eC = 0;
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[0].length; j++) {
					if (maze[i][j] == 'X' || maze[i][j]=='P') {
						been[i][j] = true;
						if (maze[i][j] == 'X') places[i][j] = -2;
					}
					if (maze[i][j] == 'E') {
						eR = i;
						eC = j;
					}
				}
			}
			ArrayDeque<Ints> q = new ArrayDeque<Ints>();
			q.add(new Ints(row,col));
			int[] dr = {-1,0,1,0};
			int[] dc = {0,1,0,-1};
			boolean cont = true;
			while (q.size()>0) {
				Ints cur = q.poll();
				for (int i = 0; i < 4; i++) {
					int nR = cur.r+dr[i];
					int nC = cur.c+dc[i];
					if (nR<0 || nR>=maze.length) continue;
					if (nC<0 || nC>=maze[0].length) continue;
					if (!been[nR][nC]) {
						q.add(new Ints(nR,nC));
						been[nR][nC] = true;
						places[nR][nC] = places[cur.r][cur.c]+1;
						if (maze[nR][nC]=='E') cont = false;
					}
				}
				if (!cont) break;
			}
			found = true;
			int r = eR;
			int c = eC;
			char[] lets = {'D','L','U','R'};
			while (!(r==row && c==col)) {
//				System.out.printf("%d %d %d %d\n",eR,eC,r,c);
				int val = nMove(r,c,places);
				r+=dr[val];
				c+=dc[val];
				ans.push(lets[val]);
			}
		}
//		System.out.println(ans.size());
		nextSpot = ans.pop();
		
		
		return nextSpot;
	}
	
	int nMove(int r, int c, int[][] maze) {
		int[] dr = {-1,0,1,0};
		int[] dc = {0,1,0,-1};
		for (int i = 0; i < 4; i++) {
			int nR = r+dr[i];
			int nC = c+dc[i];
			if (nR<0 || nR>=maze.length) continue;
			if (nC<0 || nC>=maze[0].length) continue;
			if (maze[nR][nC]==maze[r][c]-1) return i;
		}
		return 0;
	}

	
}

class Ints implements Comparable<Ints> {
	int r, c;
	
	public Ints(int i, int j) {
		r = i;
		c = j;
	}
	
	@Override
	public int compareTo(Ints arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
