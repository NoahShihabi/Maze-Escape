import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Main {
	
	public static char[][] theMaze;
	public static Runner runner;
	public static int[] pastSpot;
	public static int[] newSpot;
	public static char move;
	public static int[] startSpot;
	public static boolean messedUp;
	public static int sleepTime = 1000;
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
		// We are going to have 20 lines in the Runs.txt
		int numLines = 20;
		// Put all the values into an array
		int[] runs = new int[numLines];
		Scanner sc = new Scanner(new File("Runs.txt"));
		
		for (int i = 0; i < numLines; i++) {
			runs[i] = sc.nextInt();
		}
		sc.close();
		
		// Go through each maze in the Runs.txt	
		for (int i = 0; i < runs.length; i++) {
			
			int mazeNum = runs[i];
			MFrame maze = null;
			// Any maze at ten or above needs to have smaller icons
			if (mazeNum<10) {
				maze = new MFrame(new File(System.getProperty("user.dir") + "\\Maps\\Map" +
					"0" + mazeNum), 30);
			} else if (mazeNum<15) {
				maze = new MFrame(new File(System.getProperty("user.dir") + "\\Maps\\Map" + mazeNum), 30);
			} else {
				maze = new MFrame(new File(System.getProperty("user.dir") + "\\Maps\\Map" + mazeNum), 10);
			}
			
			maze.setVisible(true);
			// This will run a runner on a specific maze
			runIt(runs[i],maze);
			
			
			maze.setVisible(false);
			
		}
	}
	
	public static void runIt(int mazeNum, MFrame maze) throws InterruptedException, ExecutionException {
		
		// Have a variable allocated for the maze
		theMaze = maze.getMaze();
		// We have not messed up until we have
		messedUp = false;
		
		// Start the runner with the designated maze
		runner = new Runner(theMaze);
		// Take a second to start, and then start going through the maze
		int steps = 0;													
		Thread.sleep(sleepTime);
		long time = 0;
		long startTime = System.nanoTime();
		ExecutorService executor = Executors.newSingleThreadExecutor();
//		System.out.println("Start");
		// While we haven't gotten to the end and the number of steps taken <= 300
		while (!maze.getDone() && steps<=300) {										
			
			pastSpot = theSpot(maze);
			startSpot = maze.runnerSpot();
			move = 'N';
			
			try {
				// Do this to give the contestant's algorithm five seconds to choose a move to make
				Main thisClass = new Main();												
				Future<String> future = executor.submit(thisClass.new Task());				
	
		        try {
	//	            System.out.println("Started..");
		            System.out.print(future.get(300, TimeUnit.SECONDS));
	//	            System.out.println("Finished!");
		        } catch (TimeoutException e) {							
	//	            System.out.println("Terminated!");
		            future.cancel(true);
		        }
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("here");
				steps = -1;
				time = 50000;
				break;
			}
			
			if (messedUp) {
//				System.out.println("here");
				steps = -1;
				time = 50000;
				break;
			}

			// Make sure the move they return is legal
			if (!containsIt(move)) {										
				move = 'N';												
			}														
			
			// Update the map based on the move
			maze.updateIt(move);
			
			// Get number of steps taken
			steps = maze.getSteps();
			// Wait a second so you can visually see the character move
			if (!maze.getDone()) {
				Thread.sleep(sleepTime);
			}
			// Get the time since last time the runner made a step													
			long curTime = System.nanoTime();
//			System.out.println("\n" + curTime);
			// Add it to the total time
			time+=(curTime-startTime); 
			startTime = System.nanoTime();
//			System.out.println(startTime);
			// Set the place of the runner to where he is now
			newSpot = theSpot(maze);
			// Repaint the maze
			updateMaze();
		}
//		str.append("Map: " + runInfo[1] + "\n");
		// Calculate time taken
		double timeCalc = time/1000000000.0;
		System.out.println("Map: " + mazeNum);
		// If-Else to handle whether the Runner succeeded
		if (steps<300) {
			System.out.println("Success in " + steps + " steps!");
			System.out.println("Time taken: " + timeCalc + " seconds");
		}
		else {
			System.out.println("Failure!");
		}
		System.out.println();
		
		timeCalc-=(steps);
		Thread.sleep(3000);
		executor.shutdownNow();
		
		
		
	}

	private static boolean containsIt(char a) {						// Used for checking if an input char value is a legal move
		char[] moves = {'U','R','D','L','N'};
		for (int i = 0; i < moves.length; i++) {
			if (a==moves[i]) {
				return true;
			}
		}
		return false;
	}
	
	private static int[] theSpot(MFrame mazy) {
		int[] x = {mazy.runnerSpot()[0], mazy.runnerSpot()[1]};
		return x;
	}
	
	private static void updateMaze() {
		theMaze[pastSpot[0]][pastSpot[1]] = ' ';
		theMaze[newSpot[0]][newSpot[1]] = 'P';
	}
	
	class Task implements Callable<String> {						// Calls for the new move
	    public String call() throws Exception {
	    	try {
	    		move = runner.nextMove(startSpot[0], startSpot[1]);
	    	} catch (java.lang.ArrayIndexOutOfBoundsException e) {
	    		messedUp = true;
	    	}
	        return "";
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
