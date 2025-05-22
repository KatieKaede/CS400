import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;

public class FrontendDeveloperTests {

    /**
     * Tests invalid input. The program should throw an error message telling the user that
     * invalid input has been entered. Program should keep running.
     */
    @Test
    public void InvalidInputTest() {
        try {
            TextUITester tester = new TextUITester("asdf\n4");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Invalid input")) {
                Assertions.fail("Program does not handle invalid input correctly");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown, program does not handle invalid" +
                    "input correctly");
        }
    }

    /**
     * Tests that the program outputs the correct total stats from the dataset.
     */
    @Test
    public void totalStatsTester() {
        try {
            TextUITester tester = new TextUITester("1\ncampus.dot\n2\n4");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Number of nodes: 160, Number of edges: 800, Total walking time: 110675.49999999997")) {
                Assertions.fail("Program did not output the correct contents of the dataset");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown");
        }
    }

    /**
     * Tests if the program succefully exits. Will input a command after exiting to make sure the program
     * does not output anymore after the exit command is called. Tests if an exit message is printed and if
     * anything is output after that exit message.
     */
    @Test
    public void exitTest() {
        try {
            TextUITester tester = new TextUITester("4\n1");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Exited Program")) {
                Assertions.fail("Program did not print output message");
            }
            if (output.contains("Enter file you would like to load")) {
                Assertions.fail("Program did not exit when called to exit");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown");
        }
    }

    /**
     * Tests the program when the user inputs data commands before entering the file with the data.
     * Program should output an error message telling the user to upload the file before accessing
     * it's data.
     */
    @Test
    public void loadDataEdgeCase() {
        try {
            TextUITester tester = new TextUITester("2\n4");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Must load file before accessing data")) {
                Assertions.fail("Program did not print output message");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown");
        }
    }


    /**
     * Tests to see if the program finds the correct route between the two buildings. Test method will
     * fail if the ouput doesn't contain the estimated time it takes to travel from one building
     * to the other.
     */
    @Test
    public void findRouteTest() {
        try {
            TextUITester tester = new TextUITester("1\ncampus.dot\n3\nMemorial Union\nScience Hall\n4");
            Scanner scnr = new Scanner(System.in);
            Backend<String, Double> backend = new Backend<>();
            Frontend frontend = new Frontend(backend, scnr);
            frontend.run();
            String output = tester.checkOutput();
            if (!output.contains("Memorial Union to Science Hall: 105.8")) {
                Assertions.fail("Program did not find the path between the two buildings");
            }
        } catch (Exception e) {
            Assertions.fail("An unexpected exception was thrown" + e);
        }

    }
}
