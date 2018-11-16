/**
 * Creates an edge between two nodes
 * 
 * @author Michael Booth
 */
public class Edge {
	private Node startNode, endNode; // Starting and ending nodes
	private String nodeType; // Type of edge

	// Constructor creates an edge between the speicfied nodes
	public Edge(Node u, Node v, String type) {
		startNode = u;
		endNode = v;
		nodeType = type;
	}

	// Returns the first node in the edge
	public Node firstEndpoint() {
		return startNode;
	}

	// Returns the second node in the edge
	public Node secondEndpoint() {
		return endNode;
	}

	// Returns the type of edge
	public String getType() {
		return nodeType;
	}

	// Sets the type of edge
	public void setType(String type) {
		nodeType = type;
	}

}
