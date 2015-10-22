import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MFrame extends JFrame {
	private static int width, height;						// Horizontal and vertical lengths of the maze
	private static char[][] zMaze;							// The maze in a 2D char array
	private static BufferedImage wall,space,player,escape;	// The BufferedImage of all the different icons required
	private static int[] runner;							// An integer array for the location of the character escaping the maze
	private static boolean done;							// A boolean variable used for ending the maze escaping
	private static int steps;								// A int varaible to count the number of steps
	private static JLabel label;							// A JLabel variable for putting an image into the JFrame
	private static JLabel label2;							// A JLabel variable to display the number of steps taken
	public static int len;									// The height and width of each tile
	
	// The constructor to the object is below, it has a File parameter for the specific map it will display
	public MFrame(File theFile, int lep) throws IOException, FileNotFoundException {
		len = lep;
		setTitle("The Maze");
		zMaze = makeMaze(theFile);
		height = (zMaze.length*len)+30;
		width = zMaze[0].length*len+15;
		
		label = new JLabel();
		label2 = new JLabel();
		
		final Container contents = getContentPane();
	    contents.setLayout (new BorderLayout ());
	    label2 = new JLabel ("Steps: " + steps); contents.add (label2, BorderLayout.PAGE_END);
		
		done = false;
		
		readImgs();
		BufferedImage bi = paintMaze();
		paintFrame(bi);
		
		setBounds( 50, 50, width+6, height+28);
		
		steps = 0;
		
//		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	// The method below is for updating the map each time the contestant's program inputs a move
	public void updateIt(char move) {
		if (move=='U') {											// The specific move that is input (in this case, up)
			if ((runner[0]-1>=0)) {									// Checks to see if the character isn't in the top row already
				if (zMaze[runner[0]-1][runner[1]]==' ') {			// If the spot directly above the character currently is empty
					zMaze[runner[0]-1][runner[1]] = 'P';			// Modifies the 2D array zMaze (a global variable)
					zMaze[runner[0]][runner[1]] = ' ';				// Another modification, these modifications update the 
																	// character's position on the map
					runner[0]-=1;									// Updates the coordinates of the character's position in the
																	// integer array runner (a global variable)
				} else if (zMaze[runner[0]-1][runner[1]]=='E') {	// If the spot directly above the character currently is the end
					zMaze[runner[0]-1][runner[1]] = 'P';		
					zMaze[runner[0]][runner[1]] = ' ';
					runner[0]-=1;
					done = true;									// This if statement does the exact same as the above one except
																	// it changes the value of the variable done to true and thus ends
																	// the maze escaping
				}
			}
		}
		else if (move=='D') {										// This does the same thing for the input to go down
			if (runner[0]+1<zMaze.length) {
				if (zMaze[runner[0]+1][runner[1]]==' ') {
					zMaze[runner[0]+1][runner[1]] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[0]+=1;
				}
				else if (zMaze[runner[0]+1][runner[1]]=='E') {
					zMaze[runner[0]+1][runner[1]] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[0]+=1;
					done = true;
				}
			}
		}
		else if (move=='R') {										// Here is if the input is calling for to go to the right
			if (runner[1]+1<zMaze[0].length) {
				if (zMaze[runner[0]][runner[1]+1]==' ') {
					zMaze[runner[0]][runner[1]+1] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[1]+=1;
				}
				else if (zMaze[runner[0]][runner[1]+1]=='E') {
					zMaze[runner[0]][runner[1]+1] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[1]+=1;
					done = true;
				}					
			}
				
		}
		else if (move=='L') {										// Input calling to go to the left
			if (runner[1]-1>=0) {
				if (zMaze[runner[0]][runner[1]-1]==' ') {
					zMaze[runner[0]][runner[1]-1] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[1]-=1;
				}
				else if (zMaze[runner[0]][runner[1]-1]=='E') {
					zMaze[runner[0]][runner[1]-1] = 'P';
					zMaze[runner[0]][runner[1]] = ' ';
					runner[1]-=1;
					done = true;
				}
			}
				
		}
		
		BufferedImage bi = paintMaze();								// This updates the image of the maze based on the edited
																	// 2D array zMap
		label.setIcon(new ImageIcon(bi));							// Change the image in the JLabel to the newly made maze image
		steps++;													// Increment the number of steps taken by one
		label2.setText("Steps: " + steps + "   Last move: " + move);
		
	}
	
	// The below is a method to add a JLabel to this Object and have the map image on the Object
	private void paintFrame(BufferedImage map) {					
		label.setIcon(new ImageIcon(map));
		label2.setText("Steps: " + steps);
		this.add(label);
	}
	
	// This reads the map from a text file and puts it into a 2D char array and returns the char array
	private char[][] makeMaze(File theMap) throws FileNotFoundException {
		Scanner sc = new Scanner(theMap);
		ArrayList<String> lines = new ArrayList<String>();
		while (sc.hasNext()) {
			lines.add(sc.nextLine());
		}
		char[][] map = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = lines.get(i).charAt(j);
			}
		}
		return map;
	}
	
	// Assigns the appropriate images to their respective global variables
	private static void readImgs() throws IOException {
		if (len==30) {
			wall = ImageIO.read(new File("Icons\\Wall.png"));
			space = ImageIO.read(new File("Icons\\Open Space.png"));
			player = ImageIO.read(new File("Icons\\Player.png"));
			escape = ImageIO.read(new File("Icons\\End.png"));
		} else {
			wall = ImageIO.read(new File("SIcons\\Wall.png"));
			space = ImageIO.read(new File("SIcons\\Open Space.png"));
			player = ImageIO.read(new File("SIcons\\Player.png"));
			escape = ImageIO.read(new File("SIcons\\End.png"));
		}
	}
	
	// Creates a BufferedImage by piecing together the different images based on the map layout
	private static BufferedImage paintMaze() {
		// Each image is lenxlen pixels so the entire finished map should be height*len by width*len
		BufferedImage bi = new BufferedImage(zMaze[0].length*len, zMaze.length*len, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		
		// You can place separate images into a BufferedImage by specifying the location in the BufferedImage 
		// at which you want to place the image
		for (int i = 0; i < zMaze.length; i++) {
			int yVal = i*len;							// The vertical location of the next image to be put into the BufferedImage
			for (int j = 0; j < zMaze[0].length; j++) {
				int xVal = j*len;						// The horizontal location of the next image to be put into the BufferedImage
				ImageIcon icon;							// ImageIcons can be put into the BufferdImage
				
				// The below conditionals will specificy what image will be put into the BufferdImage at location xVal, yVal
				if (zMaze[i][j]=='X') {
					icon = new ImageIcon(wall);
				}
				else if (zMaze[i][j]==' ') {
					icon = new ImageIcon(space);
				}
				else if (zMaze[i][j]=='P') {
					icon = new ImageIcon(player);
					runner = new int[2];				// Initialize the array for the location of the character
					runner[0] = i;						// The row the character is in
					runner[1] = j;						// The column the character is in
				}
				else {
					icon = new ImageIcon(escape);
				}
				icon.paintIcon(null, g, xVal, yVal);	// Input the ImageIcon into the BufferedImage at location xVal, yVal
				
			}
		}
		return bi;										// Return the BufferedImage
	}
	
	// A getter method for the 2D char array of the maze
	public char[][] getMaze() {
		char[][] newMaze = new char[zMaze.length][zMaze[0].length];
		for (int i = 0; i < zMaze.length; i++) {
			for (int j = 0; j < zMaze[0].length; j++) {
				newMaze[i][j] = zMaze[i][j];
			}
		}
		return newMaze;
	}
	
	// A getter method of the location of the character in an integer array
	public int[] runnerSpot() {
		return runner;
	}
	
	// A getter method of the boolean value done
	public boolean getDone() {
		return done;
	}
	
	// A getter method of the number of steps taken
	public int getSteps() {
		return steps;
	}
	
	
}
