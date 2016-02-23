package quoridorFE;

public class BoardNode {
	public BoardNode north;
	public BoardNode south;
	public BoardNode left;
	public BoardNode right;
	
	public int xPos;
	public int yPos;
	
	public String pawn = "";
	
	public BoardNode(int xPos, int yPos){
		this(xPos, yPos, "", null, null, null, null);
	}
	
	public BoardNode(int xPos, int yPos, String pawn, BoardNode north, BoardNode south, BoardNode left, BoardNode right){
		this.xPos = xPos;
		this.yPos = yPos;
		this.pawn = pawn;
		this.north = north;
		this.south = south;
		this.left = left;
		this.right = right;
	}
	
	
}
