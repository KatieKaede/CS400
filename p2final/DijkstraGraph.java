// --== CS400 File Header Information ==--
// Name: Katie Krause
// Email: klkrause5@wisc.edu
// Group and Team: E19
// Group TA: Lakshika Rathi
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
	// Create a priority queue to store and examine shorter costs
	PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();

        // Create placeholder map instance
        PlaceholderMap<NodeType, SearchNode> visitedNodes = new PlaceholderMap<>();

        // Initialize the queue with our start node
        priorityQueue.add(new SearchNode(nodes.get(start), 0, null));

        while (!priorityQueue.isEmpty()) {
            // poll gets the node with the lowest cost in the queue
            SearchNode current = priorityQueue.poll();

            // Check if the current node is the destination, if it is we return
            if (current.node.data.equals(end)) {
                return current;
            }

            if (!visitedNodes.containsKey(current.node.data)) {
                // Mark the current node as visited
                visitedNodes.put(current.node.data, current);

                // Retrieve the neighbors, we find the edge leaving our current to declare our successor
                List<BaseGraph<NodeType, EdgeType>.Edge> neighbors = nodes.get(current.node.data).edgesLeaving;

                // Iterate through the neighbors
                for (BaseGraph<NodeType, EdgeType>.Edge edge : neighbors) {
                    BaseGraph<NodeType, EdgeType>.Node neighborNode = edge.successor;

                    // Calculate new cost
                    double newCost = current.cost + edge.data.doubleValue();

                    // Create a new SearchNode for the neighbor
                    SearchNode neighbor = new SearchNode(neighborNode, newCost, current);

                    // Add it to the priority queue
                    priorityQueue.add(neighbor);
                }
            }
        }
        // throw exception if path doesn't exist/nodes aren't graph nodes
        throw new NoSuchElementException("No path from " + start + " to " + end + " or " + start + " " + end + " aren't graph nodes");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     * @throws NoSuchElementException when no path from start to end if found or when either isn't a node 
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        SearchNode endNode;

	try {
	    endNode = computeShortestPath(start, end);
	} catch (NoSuchElementException e) {
	    throw new NoSuchElementException(e.getMessage());
	}

        // Check if end node was null meaning empty path
        if (endNode == null) {
            return new ArrayList<>();
        }

        List<NodeType> pathData = new ArrayList<>();
        SearchNode current = endNode;

        // We traverse backwards so that the list begins with our starter destination
        while (current != null) {
            pathData.add(0, current.node.data);
            current = current.predecessor;
	}
        return pathData;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     * @throws NoSuchElementException when no path from start to end if found or when either isn't a node
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode result;
	try {
	    result = computeShortestPath(start, end);
	} catch (NoSuchElementException e) {
	    throw new NoSuchElementException(e.getMessage());
	}

        // Check if our node is null, meaning no path
        if (result == null) {
            return Double.NaN;
        }

        // else we return the cost
        return result.cost;
    }
    /**
     * This test should utilize DijkstraGraph's shortestPathCost when there are two different path options
     */
    @Test
    public void firstTestManual() {

        // Create an instance of BaseGraph
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // Insert nodes and edges into the graph from Gary's lecture
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "M", 5);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("F", "G", 9);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);

        // Check if shortest path DAHI has a cost of 17 with SearchNode I
	Assertions.assertEquals(17, graph.shortestPathCost("D", "I"));

	ArrayList<String> pathData = new ArrayList<>();
        DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("D", "I");

	while (current != null) {
            pathData.add(0, current.node.data);
            current = current.predecessor;
        }

	Assertions.assertEquals("[D, A, H, I]", pathData.toString());
    }

    /**
     * This test will observe two nodes and get the cost of the shortest path
     */
    @Test
    public void testTwoCheckShortest() {

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // Insert nodes and edges into the graph from Gary's lecture                                           
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "M", 5);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("F", "G", 9);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);

        // Check if two alternative nodes that have two different paths have the correct cost
	Assertions.assertEquals(7, graph.shortestPathCost("A", "E"));

	ArrayList<String> pathData = new ArrayList<>();
        DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("A", "E");

        while (current != null) {
            pathData.add(0, current.node.data);
            current = current.predecessor;
        }


        Assertions.assertEquals("[A, B, M, E]", pathData.toString());
    }

    /**
     * This test will try to find the shortest path of two nodes that are valid but are not connected
     * we expect a NoSuchElementException
     */
    @Test
    public void testThreeNoPath() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // Insert nodes and edges into the graph from Gary's lecture                                           
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "M", 5);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("F", "G", 9);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);

	Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("E", "G"));
    }
}
