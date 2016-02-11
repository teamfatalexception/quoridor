/* John Clark
CIS-303
HW8- MazeBuilder
*/

import java.util.*;

public class MazeBuilder{
    private static int columns;
    private static int rows;
    public static void main(String[] args){

	if(args.length != 2)
	    System.out.println(new Exception("Require two integer parameters"));

	rows = Integer.parseInt(args[0]);
	columns = Integer.parseInt(args[1]);
	Random rand = new Random();  // New random to use for walls
	int numElements = rows * columns; //Grid size of maze
	Maze maze = new Maze(rows,columns); 
       	DisjSets disj = new DisjSets(numElements);
	
	for(int i = 0; i < numElements-1;){
	        int xcor = rand.nextInt(numElements); //Get random integer between 0-20 for x-coordinate
		int ycor = rand.nextInt(numElements); //Get random integer between 0-20 for y-coordinate


        //If xcor and ycor are not in same equivalence class and they are neighbors union there roots and remove a wall.
	    if(!(disj.find(xcor) == disj.find(ycor)) && isNeighbor(xcor,ycor)){
		if(disj.find(ycor) == ycor)
		    disj.union(disj.find(xcor), disj.find(ycor));		 
		else
	           disj.union(disj.find(ycor), disj.find(xcor));
		
		 maze.kickOutWall(xcor/columns,xcor%columns,ycor/columns,ycor%columns);
		 i++;
		
	    }
	    	    		 	   		      	 	
	}
	        System.out.println(disj.toString());
		System.out.println(maze.toString());		  
    }      
    

       //Helper method to check if xcor and ycor are neighbors
       private static boolean isNeighbor(int x, int y) {
	   if(x <= 2 || y <= 2){
	       return (x == y-1 || x == y+1 || x == y+columns || x == y-columns);
	   }
	       
	   return (x == y-1 || x == y+1 || x == y+columns || x == y-columns) && !(( x%columns == columns -1 && y% columns == 0) || (y % columns == columns-1 && x % columns == 0)) ;
       }


}