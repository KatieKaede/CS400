import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Frontend implements FrontendInterface{

    private BackendInterface<String, Double> backend;

    private Scanner scnr;

    private boolean dataLoaded;

    public Frontend(BackendInterface<String, Double> backend, Scanner scnr){
        this.scnr = scnr;
        this.backend = backend;
    }
    @Override
    public void run() {
        while(true){
            runMainMenu();
            String input = scnr.nextLine();

            switch (input) {
                case "1":
                    System.out.println("Enter the name of the file to load: ");
                    String fileName = scnr.nextLine();
                    loadData(fileName);
                    dataLoaded = true;
                    break;
                case "2":
                    if(dataLoaded) {
                        displayStats();
                    } else {
                        System.out.println("Must load file before accessing data");
                    }
                    break;
                case "3":
                    if(dataLoaded) {
                        System.out.println("Enter start building: ");
                        String startDestination = scnr.nextLine();
                        System.out.println("Enter destination building: ");
                        String endDestination = scnr.nextLine();
                        findRoute(startDestination, endDestination);
                    } else {
                        System.out.println("Must upload file before any operations can be performed.");
                    }
                    break;
                case "4":
                    exit();
                    return;
                default:
                    System.out.println("Invalid input, please enter one of the provided options. ");
                    break;
            }
        }

    }

    @Override
    public void runMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Load Data from File");
        System.out.println("2. Display Total Statistics");
        System.out.println("3. Find Route");
        System.out.println("4. Exit");
        System.out.println("Select an option by entering an integer 1 to 4:");
    }

    @Override
    public void loadData(String file) {
        try {
            backend.readData(file);
            System.out.println("Data loaded from file: " + file);
        } catch (FileNotFoundException e) {
            System.out.print("File not found error, please enter a valid file");
        }
    }

    @Override
    public void displayStats() {
        try {
            System.out.println("Total Statistics of Dataset: " + backend.getStatistics());
        } catch (IllegalArgumentException e) {
            System.out.println("Must load file before accessing data.");
        }

    }
    @Override
    public void findRoute(String startNode, String endNode) {
        try {
            ShortestPath<String, Double> route = (ShortestPath<String, Double>) backend.getShortestPath(startNode, endNode);
            System.out.println("Route from " + startNode + " to " + endNode + ":");
            for (int i = 0; i < route.getPath().size() - 1; i++) {
                System.out.println(route.getPath().get(i) + " to " + route.getPath().get(i + 1) + ": " +
                        route.getWalkingTimes().get(i) + " seconds.");
            }
            System.out.println("Total time: " + route.totalPathCost() + " seconds.");
        } catch (IllegalArgumentException e) {
            System.out.println("Must upload file before any operations can be performed.");
        }
    }

    @Override
    public void exit() {
        System.out.println("Exited Program");
    }
}
