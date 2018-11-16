
/**
 * Creates a graph of nodes
 * 
 * @author Michael Booth
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph implements GraphADT {
	private int graphSize; // Size of graph
	private ArrayList<LinkedList<Edge>> graphListEdge; // Holds a list of edges
	private ArrayList<LinkedList<Node>> graphListNode; // Holds a list of nodes

	// Constructor greats a graph of with n nodes
	public Graph(int n) {
		graphSize = n;
		graphListEdge = new ArrayList<LinkedList<Edge>>();
		graphListNode = new ArrayList<LinkedList<Node>>();

		for (int i = 0; i < n; i++) {
			Node tempNode = new Node(i);
			graphListEdge.add(new LinkedList<Edge>());
			graphListNode.add(new LinkedList<Node>());

			graphListNode.get(i).add(tempNode);
		}
	}

	// Inserts an edge between two nodes
	public void insertEdge(Node u, Node v, String edgeType) throws GraphException {
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (GraphException e) {
			throw new GraphException();
		}

		try {
			getEdge(u, v);
			throw new GraphException();
		} catch (GraphException e) {
			graphListEdge.get(u.getName()).add(new Edge(u, v, edgeType));
			graphListEdge.get(v.getName()).add(new Edge(v, u, edgeType));
		}

	}

	// Returns the node with the specified number
	public Node getNode(int name) throws GraphException {
		if (name >= graphSize) {
			throw new GraphException();
		} else {
			return graphListNode.get(name).getFirst();
		}
	}

	// Returns an iterator with all the edges of a node
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {

		try {
			getNode(u.getName());
		} catch (GraphException e) {
			throw new GraphException();
		}

		if (graphListEdge.get(u.getName()).size() != 0) {
			return graphListEdge.get(u.getName()).iterator();

		} else {
			return null;
		}
	}

	// Returns the edge between two nodes
	public Edge getEdge(Node u, Node v) throws GraphException {
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (GraphException e) {
			throw new GraphException();
		}

		Iterator<Edge> iteratorList = incidentEdges(u);

		if (iteratorList != null) {
			while (iteratorList.hasNext()) {
				Edge nextEdge = iteratorList.next();
				if (nextEdge.secondEndpoint() == v) {
					return nextEdge;
				}
			}
		}
		throw new GraphException();
	}

	// Checks if two nodes are adjacent
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (GraphException e) {
			throw new GraphException();
		}

		try {
			getEdge(u, v);
			return true;
		} catch (GraphException e) {
			return false;
		}

	}
}
