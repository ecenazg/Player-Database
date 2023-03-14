//-----------------------------------------------------
// Title: Player Database class
// Author: Ecenaz Güngör
//-----------------------------------------------------


import java.util.Scanner;

public class PlayerDatabase {

	// Whole data is stored this Tree root
	BSTNode root;
	
	// Boolean value to remove proper element from tree
	public boolean found;
	
	// kthSmallest value by Fee
	BSTNode kthSmallest;
	
	// Instance variable of same as k of kthSmallestElement
	int kth;
	
	// Output string should be separated with comma.
	// I concatenate all related Strings to this variable.
	// Last element should not be comma.
	// So I remove last 2 elements of this string (comma and space)
	// To increase performance, I may use StringBuilder
	String output;
	
	// Constructor, Empty Tree
	PlayerDatabase() {
		root = null;
	}
	
	// If root is empty then add new player as root
	public void addPlayer(String name, String surname, int transferFee) {
		Player newPlayer = new Player(name, surname, transferFee);
		if(root==null) { 
			root = new BSTNode(newPlayer);
			return;
		}
		
		// I need to protect(hold) the root of tree
		BSTNode current = root;
		
		// HW say, If the user attempts to add a player with an existing name and surname, 
		// you should overwrite old information
		// Since tree is constructed by transfer fee, overwrite is not directly possible
		// remove the old player first.
		if(searchByName(name, surname, current) == true)
			removePlayer(name, surname);
		
		// Always add new player
		addPlayer(newPlayer, current);
		
	}
	
	// Helper function of addPlayer. I do not exploit method signatures.
	// All operations have this type of helper functions
	private BSTNode addPlayer(Player newPlayer, BSTNode current) {
		if(current==null) { 
			return new BSTNode(newPlayer);
		}
		// New Player's Transfer fee is higher so goes to right node 
		if(newPlayer.getTransferFee()>current.currentNode.getTransferFee()) {
			current.right = addPlayer(newPlayer, current.right);
		} else if(newPlayer.getTransferFee()<current.currentNode.getTransferFee()) {
			// New Player's Transfer fee is lower so goes to left node 
			current.left = addPlayer(newPlayer, current.left);
			
			// This is a tricky part. 
			// KthSmallest function has better performance for huge inputs
			// I store left nodes count of all nodes
			current.lCount++;
		}
		
		return current;
	}
	
	// Remove method signature
	public void removePlayer(String name, String surname) {
		
		// I need to protect(hold) the root of tree
		BSTNode current = root;
		
		// HW say, If this player does not exist in the database give a warning message
		// and do nothing
		if(searchByName(name, surname, current) == false)
			System.out.println("Player does not exist. No change!");
		else {
			// Traverse over tree and remove the player.
			found = false;
			removePlayer(current, name, surname);
			// This is a helper function of kthSmallest Element
			// After each delete operation, I update all nodes' count of left nodes.
			updateLCounts();
		}
	}
	
	// Helper function of removePlayer. I do not exploit method signatures.
	
	private BSTNode removePlayer(BSTNode current, String name, String surname) {
		if (found || current == null)
			return current;
 
		if (current.currentNode.getName().equals(name) && 
				current.currentNode.getSurname().equals(surname)) {
			found = true;
			
			// Exceptionally, if I remove root node, Then root can be change
			if(current == root) {
				// One child case
				if (current.left == null)
					root = current.right;
				if (current.right == null)
					root = current.left;
				return current;
			}
			
			// One child case
			if (current.left == null)
				return current.right;
			if (current.right == null)
				return current.left;

			// Find rightmost node of its left node
			BSTNode cur = current.left;
			while (cur.right != null)
				cur = cur.right;
			
			// Change it to found node.
			cur.right = current.right;
			
			
			return current.left;
		}

		// Do nothing other nodes
		current.left = removePlayer(current.left, name, surname);
		current.right = removePlayer(current.right, name, surname);

		return current;
	}
	// updateLCounts update count of left nodes after remove operation.
	// Details will be mentioned in FindKSmallest method
	private void updateLCounts() {
		BSTNode current = root;
		updateLCounts(current);
	}
	
	// Number of left nodes of a node
	// Function returns number of subnodes of input + 1(node itself)
	private int updateLCounts(BSTNode current) {
		if(current == null)
			return 0;
		if(current.left==null) {
			current.lCount = 0;
		}
		int l = updateLCounts(current.left);
		int r = updateLCounts(current.right);
		current.lCount = l;
		return r+l+1;
	}
	
	// Search over all tree and return false or true.
	// This method is a regular binary tree traversal.
	// BST's features do not help me, because bst is formed by transfer fees, not name or surname
	public void searchByName(String name, String surname) {
		if(root==null)
			System.out.println("False");
		BSTNode current = root;
		boolean result = searchByName(name,surname,current);
		
		if(result)
			System.out.println("True");
		else
			System.out.println("False");
	}
	
	// If a node has same name and surname with given input, it returns true
	// Only one true is enough. So I used logical Or to find TRUE element;
	public boolean searchByName(String name, String surname, BSTNode current) {
		if(current==null)
			return false;
		if(current.currentNode.getName().equals(name) && 
				current.currentNode.getSurname().equals(surname)) {
			return true;
		}
		return searchByName(name, surname, current.left) || 
				searchByName(name, surname, current.right);
	}
	
	

	public void searchByRange(int minFee, int maxFee) {
		if(root==null)
			return;
		BSTNode current = root;
		output = "";
		inorderTraverse(current, minFee, maxFee);
		System.out.println(output.substring(0, output.length()-2));
	}
	
	// Super simple inorder traverse
	// I am not sure edge cases. I assumed minFee and maxFee are controlled inclusively.
	private void inorderTraverse(BSTNode current, int minFee, int maxFee) {
		if(current == null)
			return;
		inorderTraverse(current.left, minFee, maxFee);
		// If conditions are met, then add player info to output variable.
		if(minFee<=current.currentNode.getTransferFee() && 
				current.currentNode.getTransferFee()<=maxFee) {
			output+=current.currentNode.PrintWithoutFee()+", ";
		}
		inorderTraverse(current.right, minFee, maxFee);
		
	}

	// Print players ascending order.
	// Inorder traversal on a BST is provide ascending order naturally.
	public void printAllPlayers() {
		output = "";
		BSTNode current = root;
		printAllPlayers(current);
		System.out.println(output.substring(0, output.length()-2));
	}
	
	// Inorder traversal
	private void printAllPlayers(BSTNode current) {
		if(current == null)
			return;
		
		printAllPlayers(current.left);
		output+=current.currentNode+", ";
		printAllPlayers(current.right);
	}
	
	
	public void FindKSmallest(int k) {
		if(root==null)
			System.out.println("Empty Tree");
		BSTNode current = root;
		
		found = false;
		kth = k;
		
		if(root.lCount<100) {
			// Inorder kth Element
			FindKSmallestInorder(current);
		} else {
			// Optimal For Big Inputs
			FindKSmallestOptimal(k, current);
		}
		System.out.println(kthSmallest.currentNode.PrintWithoutFee());
	}
	
	// Modified inorder traversal
	private void FindKSmallestInorder(BSTNode current) {
		if(found || current == null)
			return;
		
		FindKSmallestInorder(current.left);
		kth--;
		if(!found && kth==0) {
			kthSmallest = current;
			found = true;
			return;
		}
		FindKSmallestInorder(current.right);
	}

	private void FindKSmallestOptimal(int k, BSTNode current) {
		if(current==null)
			return;
		
		int count = current.lCount + 1;
		// if count of left nodes + 1 is k then I find the kthsmallest node.
		// return.
        if (count == k) {
        	kthSmallest = current;
            return;
        }
        // If count of left nodes is bigger, I need to search right side of this node.
        if (count > k)
        	FindKSmallestOptimal(k, current.left);
 
        // Else I need to search right side and update k value.
        FindKSmallestOptimal(k - count, current.right);
	}
	
	// Driver class
	public static void main(String[] args) {
		PlayerDatabase pd = new PlayerDatabase();
		
		System.out.println("Enter Operation Number");
		
		Scanner scanner = new Scanner(System.in);
		int op = -1;
		String name, surname, line;
		String[] nameSurname, minMaxFee;
		int k, minFee, maxFee;

		// 4th and 6th operations do not have transfer fee info
		while(op!=7) {
			try {
				op = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
			switch(op) {
				case 1:
					line = scanner.nextLine();
					String[] entries = line.trim().split(",");
					for(String entry:entries) {
						String[] parsed = entry.trim().split(" ");
						pd.addPlayer(parsed[0], parsed[1], Integer.parseInt(parsed[2]));
					}
				break;
				case 2:
					line = scanner.nextLine().trim();
					nameSurname = line.trim().split(" ");
					name = nameSurname[0].trim();
					surname = nameSurname[1].trim();
					pd.removePlayer(name, surname);
				break;
				case 3:
					line = scanner.nextLine().trim();
					nameSurname = line.trim().split(" ");
					name = nameSurname[0].trim();
					surname = nameSurname[1].trim();
					pd.searchByName(name, surname);
				break;
				
				case 4:
					line = scanner.nextLine().trim();
					minMaxFee = line.trim().split(" ");
					minFee = Integer.parseInt(minMaxFee[0]);
					maxFee = Integer.parseInt(minMaxFee[1]);
					pd.searchByRange(minFee, maxFee);
				break;
				
				case 5:
					pd.printAllPlayers();
				break;
				
				case 6:
					line = scanner.nextLine().trim();
					k = Integer.parseInt(line);
					pd.FindKSmallest(k);
				break;
				
				case 7:
				break;
				
			}
		}
		scanner.close();

	}

}

