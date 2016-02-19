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
                               new VertWall(), new HorWall());
        for (int col = 1; col < cols; col++)
	    cell[0][col] = new Cell(cell[0][col-1].right, new HorWall(), 
	                            new VertWall(), new HorWall());
        // do the rest of the rows
        for (int row = 1; row < rows; row++) {
            cell[row][0] = new Cell (new VertWall(), cell[row-1][0].down,
                                     new VertWall(), new HorWall());
            for (int col = 1; col < cols; col++)
                cell[row][col] = new Cell(cell[row][col-1].right, 
                                          cell[row-1][col].down,
		                 new VertWall(), new HorWall());
	}
        // kick out left and top wall on cell(0,0)
	cell[0][0].left = new VertDownWall();
	cell[0][0].up = new HorDownWall();
        // kick out bottom and right wall on cell(rows-1, cols-1)
	cell[rows-1][cols-1].down = new HorDownWall();
	cell[rows-1][cols-1].right = new VertDownWall();
    }

    public void kickOutWall(int rowA, int colA, int rowB, int colB) {
	//        System.out.println("Taking out wall between " +
	//			   "("+rowA+","+colA+") and (" +
	//			   rowB+","+colB+")");
	if (rowA == rowB) {
	    int col = Math.min(colA, colB);
	    cell[rowA][col].right = new VertDownWall();
	    return;
	}
        // colA == colB
        int row = Math.min(rowA, rowB);
        cell[row][colA].down = new HorDownWall();
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
        result = " " + result.substring(1, result.length()-2) + " \n";
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


        public Cell() {
        }

        public Cell( Wall left, Wall up, Wall right, Wall down) {
    	this.left = left;
    	this.up = up;
    	this.right = right;
    	this.down = down;
        }
    }   
    // END INNER CLASSES

    public static void main (String [] args) {
	Maze maze = new Maze(Integer.parseInt(args[0]),
                             Integer.parseInt(args[1]));
	System.out.println(maze);
    }

}


