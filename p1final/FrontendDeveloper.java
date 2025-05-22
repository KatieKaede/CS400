import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class implements the methods from the Frontend interface.
 */
public class FrontendDeveloper implements FrontendInterface {

    BackendDeveloperPlaceholder backend = new BackendDeveloperPlaceholder();
    Scanner scanner = new Scanner(System.in);
    public FrontendDeveloper(BackendDeveloperPlaceholder backend, Scanner scanner) {
        this.backend = backend;
        this.scanner = scanner;
    }

    /**
     * Specifies and loads a data file
     */
    public void dataFile() throws FileNotFoundException {
        BackendDeveloperPlaceholder backendPlaceholder = new BackendDeveloperPlaceholder();

        // get file path
        System.out.println("Enter the file path for the data set: ");
        Scanner myScanner = new Scanner(System.in);
        String filePath = myScanner.nextLine();

        try {
            backendPlaceholder.loadData(filePath);
            System.out.println("File path read successfully");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        myScanner.close();
    }


    /**
     * Lists the replacement for the ingredient selected
     * @throws NullPointerException
     */
    public void listReplacements() throws NullPointerException {
        String output = ""; // outputs list of replacements
        String[] replacement = new String[3];

        Scanner myScanner = new Scanner(System.in);

        System.out.println("Enter the ingredient to replace: ");
        String ingredientName = myScanner.nextLine();

        // null ingredient
        if (ingredientName.equals(null)) {
            throw new NullPointerException("Error. Null ingredient passed.");
        }

        // invalid ingredient
        if (ingredientName.equals("111")) {
            output = ("Ingredient not found.");
        }

        if (ingredientName.equals("Canned Peaches")) {
            replacement[0] = "Canned Plums";
            replacement[1] = "Canned Sliced Pineapple";
            replacement[2] = "Canned Apricots";
        }

        for (int i=0;i<3;i++) {
            if (replacement[i] != null) {
                output = output + "Replacement " + i+1 + ": " + replacement[i] + "\n";
            }
        }
        myScanner.close();

        System.out.println(output);

    }

    // Lists the number of ingredients and the number of categories
    public void listNumberAndCategory() {
        BackendDeveloperPlaceholder backendPlaceholder = new BackendDeveloperPlaceholder();

        int numCategories = backendPlaceholder.getCategoryCount();
        int numIngredients = backendPlaceholder.getIngredientCount();

        System.out.println(numIngredients + " ingredients, " + numCategories + " categories in the data file provided.");
    }

    /**
     * Command to exit the app
     */
    public void exit() {
        System.exit(0);
        System.out.println("exited successfully.");
    }


    /**
     * Starts the main menu
     */
    public void startMain() {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Welcome to Subsgredient. Use this app to find an ingredient substitution.");
        System.out.println("Select a command:\n" + "1. Upload data file \n2. List ingredient replacements \n" +
                "3. List number of ingredients and categories \n4. Exit\n");

        String choice = myScanner.nextLine();

        if (choice.equals("1")) {
            try {
                dataFile();
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Start Main.");
            }
        }
        else if (choice.equals("2")) listReplacements();
        else if (choice.equals("3")) listNumberAndCategory();
        else if (choice.equals("4")) exit();

        myScanner.close();
    }

    /**
     * Starts the submenu: Get file and list number of ingredients and categories
     */
    public void startSubMenu() {

        Scanner myScanner = new Scanner(System.in);
        try {
        dataFile();}
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            exit();
        }
        listNumberAndCategory();

        myScanner.close();
    }

}
