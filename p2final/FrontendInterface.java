import java.io.IOException;
import java.util.Scanner;
/**
 * Interface for a class that implements the functionality of the frontend for the UW-Madison
 * Path Finder app. The class defines a sample constructor that accepts an instance of a
 * backend, and a scanner, and defines methods that allow a user to run and exit the app, view
 * dataset statistics, and find the shortest path between two UW-Madison buildings in the dataset.
 *
 * This interface expects any FileNotFoundException, and start/destination's existence related
 * exceptions to be thrown by the backend. InputMismatchExceptions, and exceptions thrown by the
 * backend are handled in the class that implements this interface.
 */
public interface FrontendInterface {

    /*
     * The constructor accepts a reference to the backend object and a Scanner to read user input
     * @param Backend instance of backend to use
     * @param Scanner Scanner to use
     */
    // public IndividualFrontendInterface(BackendInterface backend, Scanner scanner);

    /**
     * This method starts a new GUI instance of the app and handles user input. It will execute
     * commands based on user-input and will call other functions when necessary.
     */
    public void run();

    /**
     * Displays a list of commands that the user can choose from
     */
    public void runMainMenu();

    /**
     * Loads the data from the file that the user enters
     * @param file file to load data from
     */
    public void loadData(String file);

    /**
     * This method displays statistics of the dataset in the application window. The specific
     * stats shown are the total number of buildings (nodes), total walking time (weight) of the
     * graph.
     */
    public void displayStats();

    /**
     * Finds the route from the start point to the end point that the user specifies. This will call
     * a backend method that will find the shortest path (using Dijkstra's algorithm) to take.
     * @param startNode user-specified start point
     * @param endNode user-specified destination
     */
    public void findRoute(String startNode, String endNode  );

    /**
     * This method closes the application
     */
    public void exit();

}
