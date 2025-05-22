import java.io.FileNotFoundException;
import java.util.List;

/**
 * This interface models a class for a backend interface for the UW Path Finder app
 * 
 * @author noahkornfeld
 *
 * @param <NodeType> the type of the nodes in the graph
 * @param <EdgeType> the type of the edges in the graph
 */
public interface BackendInterface<NodeType, EdgeType extends Number> {

  // commented out constructor for a class that implements this interfae
  // public IndividualBackendInterface(GraphADT <NodeType, EdgeType> graph)

  /**
   * This class reads in a file from an inputed string
   * 
   * @param filePath is the string that holds the dataset name
   * @throws FileNotFoundException if the file does not exist
   */
  public void readData(String filePath) throws FileNotFoundException;

  /**
   * This method takes two nodes and finds the shortest path between the two nodes
   * 
   * @param startBuilding       is the node indicating the starting point
   * @param destinationBuilding is the node indicating the destination point
   * @return a list indicating the nodes to get from the start node to the end node
   */
 public List<NodeType> getShortestPath(NodeType startBuilding, NodeType destinationBuilding);

  /**
   * this method returns a string of statistics including the number of nodes (buildings), the
   * number of edges, and the total walking time (sum of weights) for all edges in the graph.
   * 
   * @return the string of statistics
   */
  public String getStatistics();
}
