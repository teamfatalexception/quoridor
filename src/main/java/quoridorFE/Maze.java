package Quoridor_Board;

import java.util.Scanner;

public class Maze {

    // field
    Cell [][] cell;
    int rows;
    int cols;

    public Maze (int rows, int cols) {
        this.rows = rows;
	this.cols = cols;
	cell = new Cell[rows][cols];
        // do the first row
        cell[0][0] = new Cell (new VertWall(), new HorWall(), 
                               new VertDownWall(), new HorDownWall());
        cell[8][0] = new Cell (new VertWall(), new HorDownWall(), 
                new VertDownWall(), new HorWall());
        
        for (int col = 1; col < cols; col++)
	    cell[0][col] = new Cell(new VertDownWall(), new HorWall(),new VertDownWall(), new HorDownWall());
        
        cell[0][8] = new Cell (new VertDownWall(), new HorWall(), new VertWall(), new HorDownWall());
        // do the rest of the rows
        for (int row = 1; row < rows; row++) {
            cell[row][0] = new Cell (new VertWall(), new HorDownWall(),
                                     new VertDownWall(), new HorDownWall());
            for (int col = 1; col < cols; col++)
                cell[row][col] = new Cell(new VertDownWall(), 
                                         new HorDownWall(),
		                 new VertDownWall(), new HorDownWall());
	}
        
        for (int row = 1; row < rows; row++){
    	    cell[row][8] = new Cell(new VertDownWall(), new HorDownWall(),new VertWall(), new HorDownWall());
        
        }
        
        for (int i = 1; i < rows; i++){
        	cell[8][i] = new Cell(new VertDownWall(), new HorDownWall(), new VertDownWall(), new HorWall());	
        }
        	
        	
        cell[8][0] = new Cell (new VertWall(), new HorDownWall(), 
                new VertDownWall(), new HorWall());
        cell[8][8] = new Cell (new VertDownWall(), new HorDownWall(), 
                new VertWall(), new HorWall());
  
    }

    public void placeWall(int col, int row, String direction){
    	
    	if (row == 8){
    		System.out.println("Invalid Move");
    	}
    	else if (col == 8){
    		System.out.println("Invalid Move");
    	}
    	else if (direction.equalsIgnoreCase("v")){
    		cell[row][col] = new Cell(new VertDownWall(),new HorDownWall(),new VertWall(),new HorDownWall());
    		cell[row +1][col] = new Cell(new VertDownWall(),new HorDownWall(),new VertWall(),new HorDownWall());
    	}
    	else if (direction.equalsIgnoreCase("h")){
    		cell[row][col] = new Cell(new VertDownWall(),new HorDownWall(),new VertDownWall(),new HorWall());
    		cell[row][col + 1] = new Cell(new VertDownWall(),new HorDownWall(),new VertDownWall(),new HorWall());
    		System.out.println(new HorWall().toString());
    	}
    	else{
    		System.out.println("Invalid Move");
    		
    	}
    		
    	
    	
    	 
    }
    
    
    public String toString() {
        String result = "";
	// print top walls
        for (int col = 0; col < cols; col++)
	    result += "+" + cell[0][col].up;
	result += "+\n";
        // rest of rows
        for (int row = 0; row < rows; row++) {
            result += cell[row][0].left;
	    for (int col = 0; col < cols; col++) 
		result += "   " + cell[row][col].right;
	    result += "\n";
	    for (int col = 0; col < cols; col++) 
	        result += "+" + cell[row][col].down;
	    result += "+\n";
	}
        // take out the upper left and lower right "corner"
        //result = " " + result.substring(1, result.length()-2) + " \n";
	return result;
    }


    // INNER CLASSES
    abstract class Wall {
        public abstract String toString();
    }

    class HorWall extends Wall {
        public String toString() {
            return "---";
        }
    }

    class HorDownWall extends HorWall {
        public String toString() {
	    return "   ";
        }
    }

    class VertWall extends Wall {
        public String toString() {
	    return "|";
	}
    }

    class VertDownWall extends Wall {
        public String toString() {
	    return " ";
	}
    }

    
    


    class Cell {

        // fields
        public Wall left;
        public Wall up;
        public Wall right;
        public Wall down;

        public Cell( Wall left, Wall up, Wall right, Wall down) {
    	this.left = left;
    	this.up = up;
    	this.right = right;
    	this.down = down;
        }

    }   
    // END INNER CLASSES

    public static void main (String [] args) {
	Maze maze = new Maze(9,9);
	System.out.println(maze);
	Scanner scan = new Scanner(System.in);
	
	
	while(true) {
		System.out.print("> ");
		// read message from user
		String msg = scan.nextLine();
		String[] words = msg.split("\\s+");
		maze.placeWall(Integer.parseInt(words[0]), Integer.parseInt(words[1]), words[2]);
		System.out.println(maze);
		
    }
    }
}


