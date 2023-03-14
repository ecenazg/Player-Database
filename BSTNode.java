//-----------------------------------------------------
// Title: BSTNode class
// Description: This class contains tree implementation and constructor.
//-----------------------------------------------------


// Simple Binary Tree representation
// lCount is an extra variable to increase kthSmallest method
// Player infos are stored in currentNode variable
public class BSTNode {
	public Player currentNode;
	public BSTNode left;
	public BSTNode right;
	public int lCount;
	
	public BSTNode(Player newPlayer) {
		this.currentNode = newPlayer;
		this.left = null;
		this.right = null;
		this.lCount = 0;
	}
}

