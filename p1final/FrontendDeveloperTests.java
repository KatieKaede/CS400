import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class with 5 test cases to verify that the FrontendDeveloper class works as expected.
 */
public class FrontendDeveloperTests {

    /**
     * Tests entering a data file.
     */
    @Test
    public void testDataFile() {
        Scanner myScanner = new Scanner(System.in);
        BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();

        FrontendDeveloper frontend = new FrontendDeveloper(backend, myScanner);


//        String userInput = myScanner.nextLine();
        try {
            TextUITester tester = new TextUITester("\ningredients.csv\n");
            frontend.dataFile();
            String output = tester.checkOutput();
            Assertions.assertTrue(output.contains("file path for the data set"));
//            TextUITester tester2 = new TextUITester("\ningredients.csv\n");
//            String output2 = tester2.checkOutput();
            Assertions.assertTrue(output.contains("successfully"), "case 2: ran unsuccessfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        myScanner.close();

        // test is true if the message thrown by the exception contains the word "null".
        // will have to include this in the error msg in the dataFile method.
        // Assertions.assertTrue(exception.getMessage().contains("null"));
    }

    /**
     * Verifies that the submenu starts appropriately. It should ask for a data file and output the number of categories
     * and ingredients.
     */
    @Test
    public void testSubMenu() {
        Scanner myScanner = new Scanner(System.in);
        BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();

        FrontendDeveloper frontend = new FrontendDeveloper(backend, myScanner);

        TextUITester tester = new TextUITester("ingredients.csv\n");

        frontend.startSubMenu();

        String output = tester.checkOutput();

        Assertions.assertTrue(output.contains("the file path"));
        Assertions.assertTrue(output.contains("categories in the data file provided"));



//        Exception exception = Assertions.assertThrows(NullPointerException.class,
//                ()->
//                        frontend.listReplacements(),
//                "Error. Null ingredient passed."
//        );

        // test is true if the message thrown by the exception contains the word "null".
        // will have to include this in the error msg in the listReplacements method.
//        Assertions.assertTrue(exception.getMessage().contains("null"));
        myScanner.close();
    }

    /**
     * Tests the case where the parameter of "listReplacement" does not exist in the csv.
     */
    @Test
    public void ingredientDoesNotExist() {
        Scanner myScanner = new Scanner(System.in);
        BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();

        FrontendDeveloper frontend = new FrontendDeveloper(backend, myScanner);

        TextUITester tester = new TextUITester("111");

        frontend.listReplacements();

        String output = tester.checkOutput();

        // Tests finding a replacement of the ingredient "111", which does not exist in the csv.
        Assertions.assertTrue(output.contains("not found"));
        myScanner.close();
    }

    /**
     * Verifies that there is at least one replacement outputted when testing listReplacements.
     */
    @Test
    public void verifyAReplacementOutputs() {
        Scanner myScanner = new Scanner(System.in);
        BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();

        FrontendDeveloper frontend = new FrontendDeveloper(backend, myScanner);
        TextUITester tester = new TextUITester("Canned Peaches");

        frontend.listReplacements();

        String output = tester.checkOutput();

        Assertions.assertTrue(output.contains("Canned Plums"));

        myScanner.close();
    }

    /**
     * Verifies that the start menu has an output and allows the user to enter values.
     */
    @Test
    public void verifyStartMenu() {
        Scanner myScanner = new Scanner(System.in);
        BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();

        FrontendDeveloper frontend = new FrontendDeveloper(backend, myScanner);
        TextUITester tester = new TextUITester("\n2");
        frontend.startMain();
        String output = tester.checkOutput();
        Assertions.assertTrue(output.contains("Welcome to Subsgredient"), "Intro message failed.");

        TextUITester tester2 = new TextUITester("\n2\n2");

        frontend.listReplacements();
        output = tester2.checkOutput();
        Assertions.assertTrue(output.contains("replace"), "Option 2 failed.");
        myScanner.close();
    }

}
