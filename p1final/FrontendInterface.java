import java.io.File;
import java.io.FileNotFoundException;

public interface FrontendInterface {

    //    public Frontend(Backend backend, Scanner scanner);

    // Specifies and loads a data file
    //@param file to be loaded up
    public void dataFile() throws FileNotFoundException;

    // Lists the replacement for the ingredient selected
    //@return provides the information for users regarding replacements for ingredients 
    public void listReplacements();

    // Lists the number of ingredients and the number of categories
    public void listNumberAndCategory();

    // Command to exit the app
    public void exit();

    /**
     *Starts the main menu
     */
    public void startMain();

    /**
     *Starts the submenu 
     */
    public void startSubMenu();

}