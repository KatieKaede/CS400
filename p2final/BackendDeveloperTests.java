import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class BackendDeveloperTests {

    /**
     * Test if readData method can handle valid files
     */
    @Test
    public void testReadDataValidFile() {
        Backend<String, Double> backend = new Backend<>();
        String validFilePath = "campus.dot";
        Assertions.assertDoesNotThrow(() -> backend.readData(validFilePath));
    }

    /**
     * Test if readData can handle non-existent files
     */
    @Test
    public void testReadDataInvalidFile() {
        // Test handling FileNotFoundException when reading data from an invalid file
        Backend<String, Double> backend = new Backend<>();
        String badFilePath = "fake.csv";
        Assertions.assertThrows(FileNotFoundException.class, () -> backend.readData(badFilePath));
    }

    /**
     * This test will ensure that getShortestPath returns a list of nodes that follow the quickest route from
     * the start building to the destination building
     */
    @Test
    public void testGetShortestPath() throws FileNotFoundException {
        // Test the getShortestPath method with a specific scenario
        Backend<String, Double> backend = new Backend<>();

        backend.readData("campus.dot");

        ShortestPathInterface<String, Double> shortestPath = backend.getShortestPath("Memorial Union", "Helen C White Hall");
        List<String> path1 = shortestPath.getPath();
        Assertions.assertEquals(Arrays.asList("Memorial Union", "Helen C White Hall"), path1);

        ShortestPathInterface<String, Double> result2 = backend.getShortestPath("Wendt Commons", "1410 Engineering Dr");
        List<String> path2 = result2.getPath();
        Assertions.assertEquals(Arrays.asList("Wendt Commons", "1410 Engineering Dr"), path2);
    }

    /**
     * This method will test the getStatistics method to see if it's appropriately returning a string with
     * the key words we are expecting
     *
     * @throws FileNotFoundException for reading the dot file
     */
    @Test
    public void testGetStatistics() throws FileNotFoundException {
        // Test getting statistics from the backend
        Backend<String, Double> backend = new Backend<>();

        // Created a smaller sample file to ensure that statistics are adding correctly
        backend.readData("sample.dot");

        // Add assertions to check if the statistics string is as expected
        Assertions.assertEquals("Number of nodes: 4, Number of edges: 4, Total walking time: 70.0", backend.getStatistics());
    }

    /**
     * This method will test that getShortestPath will throw a NoSuchElementException when we have an empty graph
     * We are expecting this from BaseGraph
     * We also expect that getStatistics
     */
    @Test
    public void testEmptyGraph() {
        // Test scenarios with an empty graph
        Backend<String, Double> backend = new Backend<>();

        // Add assertions to check the behavior when the graph is empty
        Assertions.assertEquals("Number of nodes: 0, Number of edges: 0, Total walking time: 0.0", backend.getStatistics());
    }

    /**
     * This integration tests that the Frontend displayStats() works as intended.
     * We use TextUITester to load the data file (switch 1) then simulate the stats (switch 2)
     * frontend.run() should contain switch cases that run these methods
     */
    @Test
    public void statsDisplayIntegrationTest() {
        Backend<String, Double> backend = new Backend<>();

        try {
            TextUITester tester = new TextUITester("1\nsample.dot\n2\n4");
            Scanner scnr = new Scanner(System.in);
            FrontendInterface frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            Assertions.assertTrue(output.contains("Number of nodes: 4, Number of edges: 4, Total walking time: 70.0"));

        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown " + e);
        }
    }

    /**
     * This integration will check that the program we are running is executed and ended correctly
     * Frontend's run() switch 4 will call for exit() and we will expect to see an exit message
     */
    @Test
    public void exitIntegrationTest() {
        Backend<String, Double> backend = new Backend<>();

        try {
            TextUITester tester = new TextUITester("4\n1");
            Scanner scnr = new Scanner(System.in);
            FrontendInterface frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            Assertions.assertTrue(output.contains("Exited Program"));

        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown " + e);
        }
    }

    /**
     * This method will test that selecting option 4 will safely close the program
     * with a specific String
     */
    @Test
    public void frontendTest1() {
        try {
            TextUITester tester = new TextUITester("4\n1");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            Assertions.assertTrue(output.contains("Exited Program"));
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown " + e);
        }
    }

    /**
     * This method will test the frontend's findRout method by selecting switch 3
     * We expect that without loading in a dot file, our program will instruct us to upload a file
     * Before proceeding
     */
    @Test
    public void frontendTest2() {
        try {
            TextUITester tester = new TextUITester("3\n4");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Must upload file before any operations can be performed.")) {
                Assertions.fail("Program did not print output message");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown");
        }
    }
}