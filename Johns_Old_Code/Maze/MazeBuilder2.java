/* John Clark
CIS-303
HW8- MazeBuilder
*/

import java.util.*;

public class MazeBuilder2{
    private static int columns;
    private static int rows;
    public static void main(String[] args){

	if(args.length != 2)
	    System.out.println(new Exception("Require two integer parameters"));

	rows = Integer.parseInt(args[0]);
	columns = Integer.parseInt(args[1]);
	Random rand = new Random();
	int numElements = rows * columns;
	Maze maze = new Maze(rows,columns);
	System.out.print(maze.toString());
	DisjSets disj = new DisjSets(numElements);
	
	while(disj.setNumber() != -1){
	    	int xcor = rand.nextInt(numElements);
		int ycor = rand.nextInt(numElements);

	    if(!(disj.find(xcor) == disj.find(ycor)) && isNeighbor(xcor,ycor)){
		
		if(disj.find(ycor) == ycor)
		    disj.union(disj.find(xcor), disj.find(ycor));		 
		else
	           disj.union(disj.find(ycor), disj.find(xcor));
		
		 maze.kickOutWall(xcor/columns,xcor%columns,ycor/columns,ycor%columns);
		
	    }
	    	System.out.println(disj.toString());
		System.out.println(maze.toString());	    		 	   		      	 	
	}
		  
    }      
    

       private static boolean isNeighbor(int x, int y) {
	   return (x == y-1 || x == y+1 || x == y+columns || x == y-columns) && !(( x%columns == columns -1 && y% columns == 0) || (y % columns == columns-1 && x % columns == 0)) ;
       }


}