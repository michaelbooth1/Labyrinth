/**
 * Creates a node that stores an int
 * 
 * @author Michael Booth
 */
public class Node {
	private int nodeName; // Node number
	private boolean nodeMark; // If node has been visted

	// Constructor create a node with the given value
	public Node(int name) {
		nodeName = name;
		nodeMark = false;
	}

	// Sets if the node has been visited
	public void setMark(boolean mark) {
		nodeMark = mark;
	}

	// Gets if the node has been visited
	public boolean getMark() {
		return nodeMark;
	}

	// Gets the nodes number
	public int getName() {
		return nodeName;
	}
}
