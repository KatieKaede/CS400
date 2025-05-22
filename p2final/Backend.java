import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

/**
 * This class will implement the interface methods of BackendInterface
 */
public class Backend<NodeType, EdgeType extends Number> implements BackendInterface<String,EdgeType> {

    private final DijkstraGraph<String, Double> graph;  // Assuming DijkstraGraph uses Double for edge weights
    private double totalCost;

    // Added this to calculate edges while parsing the file
    private int numEdges;

    public Backend() {
        this.graph = new DijkstraGraph<>(new PlaceholderMap<>());
    }

    /**
     * This class reads in a file from an inputted string
     *
     * @param filePath is the string that holds the dataset name
     * @throws FileNotFoundException if the file does not exist
     */
    @Override
    public void readData(String filePath) throws FileNotFoundException {
        try {
            String regex = "\"([^\"]+)\" -- \"([^\"]+)\" \\[seconds=(\\d+(\\.\\d+)?)\\];";
            Pattern pattern = Pattern.compile(regex);
            totalCost = 0;

            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String buildingOne = matcher.group(1);
                    String buildingTwo = matcher.group(2);
                    double edgeDistance = Double.parseDouble(matcher.group(3));

                    // populate the nodes and edges
                    graph.insertNode(buildingOne);
                    graph.insertNode(buildingTwo);
                    boolean newEdgeOne = graph.insertEdge(buildingOne, buildingTwo, edgeDistance);
                    boolean newEdgeTwo = graph.insertEdge(buildingTwo, buildingOne, edgeDistance);

                    if (newEdgeOne || newEdgeTwo) {
                        totalCost += edgeDistance;
                        numEdges++;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File Not Found " + filePath);
        }
    }

    /**
     * This method takes two nodes and finds the shortest path between the two nodes
     *
     * @param startBuilding       is the node indicating the starting point
     * @param destinationBuilding is the node indicating the destination point
     * @return a list indicating the nodes to get from the start node to the end node
     */
    @Override
    public ShortestPathInterface<String, Double> getShortestPath(String startBuilding, String destinationBuilding) {
        List<String> shortestPath= graph.shortestPathData(startBuilding,destinationBuilding);
        Double pathCost = graph.shortestPathCost(startBuilding,destinationBuilding);
        List<Double> pathEdges = new LinkedList<Double>();

        for (int i = 0; i < shortestPath.size() - 1; i++){
            pathEdges.add((graph.getEdge(shortestPath.get(i),shortestPath.get(i+1))));

        }
        return new ShortestPath<>(shortestPath,pathEdges,pathCost);
    }

    /**
     * this method returns a string of statistics including the number of nodes (buildings), the
     * number of edges, and the total walking time (sum of weights) for all edges in the graph.
     *
     * @return the string of statistics
     */
    @Override
    public String getStatistics() {
        // Number of buildings
        int nodeNum = graph.getNodeCount();

        return "Number of nodes: " + nodeNum + ", Number of edges: " + numEdges + ", Total walking time: " + totalCost/2;
    }

    public static void main(String[] args) {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));

        frontend.run();
    }
}
