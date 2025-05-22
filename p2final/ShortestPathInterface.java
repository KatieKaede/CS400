import java.util.List;

/**
 * This interface models a class that returns information for the shortest path between two nodes (buildings)
 *
 */
public interface ShortestPathInterface<NodeType, EdgeType extends Number> {

    /**
     * getter method that returns a List of the nodes along the shortest path
     * @return a list of the nodes along the shortest path
     */
    public List<String> getPath();

    /**
     * getter method that returns a list of the walking times from building to building
     * @return a list of the walking times
     */
    public List<EdgeType> getWalkingTimes();

    /**
     * a getter method that returns the total cost (time) to walk from the start building to the destination
     * @return the total cost of the path
     */
    public double totalPathCost();
}
