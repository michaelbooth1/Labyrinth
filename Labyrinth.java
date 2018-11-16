
/**
 * Creates a labyrinth using a graph
 * 
 * @author Michael Booth
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class Labyrinth {
	private Graph graphLab; // Holds the graph used by the labyrinth
	private Node entrance; // Entrance node
	private Node exit; // Exit node
	private int brickBombs; // Number of brick bombs
	private int acidBombs; // Number of acid bombs

	// Constructor reads in the input file and creates the graph
	public Labyrinth(String inputFile) throws LabyrinthException {
		BufferedReader in;
		String line;
		String fileName = inputFile;
		Vector<String> v = new Vector<String>();
		Iterator<String> iter;

		try {
			in = new BufferedReader(new FileReader(fileName));
			line = in.readLine();

			while (line != null) {
				v.add(line);
				line = in.readLine();
			}
			in.close();

			iter = v.iterator();
			String scale = iter.next();
			String width = iter.next();
			String length = iter.next();
			brickBombs = Integer.parseInt(iter.next());
			acidBombs = Integer.parseInt(iter.next());

			graphLab = new Graph(Integer.parseInt(width) * Integer.parseInt(length));

			int counter = 0;
			while (iter.hasNext()) {
				if (counter % 2 == 0) {
					String tempLine = iter.next();
					for (int i = 0; i < tempLine.length(); i++) {
						char c = tempLine.charAt(i);
						Node startNode = graphLab.getNode(((counter / 2) * Integer.parseInt(width)) + (i - 1) / 2);
						Node endNode = graphLab.getNode(((counter / 2) * Integer.parseInt(width)) + (i + 1) / 2);
						switch (c) {
						case 'b':
							entrance = graphLab.getNode(((counter / 2) * Integer.parseInt(width)) + i / 2);
							break;
						case 'x':
							exit = graphLab.getNode(((counter / 2) * Integer.parseInt(width)) + i / 2);
							break;
						case 'h':
							graphLab.insertEdge(startNode, endNode, "wall");
							break;
						case 'H':
							graphLab.insertEdge(startNode, endNode, "thickWall");
							break;
						case 'm':
							graphLab.insertEdge(startNode, endNode, "metalWall");
							break;
						case '-':
							graphLab.insertEdge(startNode, endNode, "corridor");
							break;
						default:
							;
						}
					}
					counter++;
				} else {
					String tempLine = iter.next();
					for (int i = 0; i < tempLine.length(); i++) {
						char c = tempLine.charAt(i);
						Node startNode = graphLab.getNode(((counter / 2) * Integer.parseInt(width)) + i / 2);
						Node endNode = graphLab.getNode((((counter + 1) / 2) * Integer.parseInt(width)) + i / 2);
						switch (c) {
						case 'v':
							graphLab.insertEdge(startNode, endNode, "wall");
							break;
						case 'V':
							graphLab.insertEdge(startNode, endNode, "thickWall");
							break;
						case 'M':
							graphLab.insertEdge(startNode, endNode, "metalWall");
							break;
						case '|':
							graphLab.insertEdge(startNode, endNode, "corridor");
							break;
						default:
							;
						}
					}
					counter++;
				}
			}
		} catch (Exception e) {
			throw new LabyrinthException();
		}
	}

	// Returns the graph used
	public Graph getGraph() {
		return graphLab;
	}

	// Returns an iterator of the solution or null if not solution found
	public Iterator solve() throws GraphException {
		Stack<Node> graphStack = new Stack<Node>();
		Stack<Node> returnStack = visitNode(entrance, graphStack);
		if (returnStack != null) {
			return returnStack.iterator();
		} else {
			return null;
		}
	}

	// Helper method that implements a DFS traversal
	private Stack<Node> visitNode(Node n, Stack<Node> graphStack) throws GraphException {
		if (n.getMark() == false) {
			n.setMark(true);
			graphStack.push(n);
		}
		Iterator<Edge> iter = graphLab.incidentEdges(n);
		Edge nodeEdge = iter.next();
		Boolean found = false;
		while (!found) {
			if (nodeEdge.secondEndpoint().getMark() == false) {
				if (nodeEdge.getType() == "corridor") {
					found = true;
				} else if (nodeEdge.getType() == "wall") {
					if (brickBombs >= 1) {
						brickBombs--;
						found = true;
					} else {
						if (iter.hasNext()) {
							nodeEdge = iter.next();
						} else {
							Node tempNode = graphStack.pop();
							if (graphStack.isEmpty()) {
								return null;
							}
							Edge tempEdge = graphLab.getEdge(tempNode, graphStack.peek());
							if (tempEdge.getType() == "wall") {
								brickBombs++;
							} else if (tempEdge.getType() == "thickWall") {
								brickBombs = brickBombs + 2;
							} else if (tempEdge.getType() == "metalWall") {
								acidBombs++;
							}
							return (visitNode(graphStack.peek(), graphStack));
						}
					}
				} else if (nodeEdge.getType() == "thickWall") {
					if (brickBombs >= 2) {
						brickBombs = brickBombs - 2;
						found = true;
					} else {
						if (iter.hasNext()) {
							nodeEdge = iter.next();
						} else {
							Node tempNode = graphStack.pop();
							if (graphStack.isEmpty()) {
								return null;
							}
							Edge tempEdge = graphLab.getEdge(tempNode, graphStack.peek());
							if (tempEdge.getType() == "wall") {
								brickBombs++;
							} else if (tempEdge.getType() == "thickWall") {
								brickBombs = brickBombs + 2;
							} else if (tempEdge.getType() == "metalWall") {
								acidBombs++;
							}
							return (visitNode(graphStack.peek(), graphStack));
						}
					}
				} else if (nodeEdge.getType() == "metalWall") {
					if (acidBombs >= 1) {
						acidBombs--;
						found = true;
					} else {
						if (iter.hasNext()) {
							nodeEdge = iter.next();
						} else {
							Node tempNode = graphStack.pop();
							if (graphStack.isEmpty()) {
								return null;
							}
							Edge tempEdge = graphLab.getEdge(tempNode, graphStack.peek());
							if (tempEdge.getType() == "wall") {
								brickBombs++;
							} else if (tempEdge.getType() == "thickWall") {
								brickBombs = brickBombs + 2;
							} else if (tempEdge.getType() == "metalWall") {
								acidBombs++;
							}
							return (visitNode(graphStack.peek(), graphStack));
						}
					}
				}
			} else {
				if (iter.hasNext()) {
					nodeEdge = iter.next();
				} else {
					Node tempNode = graphStack.pop();
					if (graphStack.isEmpty()) {
						return null;
					}
					Edge tempEdge = graphLab.getEdge(tempNode, graphStack.peek());
					if (tempEdge.getType() == "wall") {
						brickBombs++;
					} else if (tempEdge.getType() == "thickWall") {
						brickBombs = brickBombs + 2;
					} else if (tempEdge.getType() == "metalWall") {
						acidBombs++;
					}
					return (visitNode(graphStack.peek(), graphStack));
				}
			}

		}

		if (nodeEdge.secondEndpoint() == exit) {
			return graphStack;
		}

		return visitNode(nodeEdge.secondEndpoint(), graphStack);
	}
}
