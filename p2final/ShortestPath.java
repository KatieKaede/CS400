import java.util.List;

/**
 * This class implements ShortestPathInterface
 */
public class ShortestPath<NodeType, EdgeType> implements ShortestPathInterface<NodeType, Double> {

    private final List<String> path;
    private final List<Double> walkingTimes;
    private final double totalPathCost;
    public ShortestPath(List<String> path, List<Double> walkingTimes, Double totalPathCost) {
        this.path = path;
        this.walkingTimes = walkingTimes;
        this.totalPathCost = totalPathCost;
    }

    @Override
    public List<String> getPath() {
        return path;
    }

    @Override
    public List<Double> getWalkingTimes() {
        return walkingTimes;
    }

    @Override
    public double totalPathCost() {
        return totalPathCost;
    }
}
