import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginSystem {

    private File userData;
    private Scanner scanner;

    public LoginSystem() throws Exception {
        try {
            userData = new File("C:\\Users\\Steven\\Documents\\JavaWorkshop\\LoginSystem\\src\\UserFile.txt");
        } catch (Exception e) {

        }
    }

    /*
     * Method to search and confirm if a user's username and password are in the
     * userData file param: username - from JTextField param: password - from
     * JTextField
     */
    public boolean login(String username, String password) throws FileNotFoundException {
        String[] check = {};
        scanner = new Scanner(userData);

        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            check = nextLine.split(" ");
            if (check[0].equals(username) && check[1].equals(password)) {
                System.out.println("Successful Login");
                scanner.close();
                return true;
            }
        }
        scanner.close();
        System.out.println("Username or password not found");
        return false;
    }

    /*
     * Method to check if Username is taken param: username - from JTextField
     */
    public boolean userExists(String username) throws FileNotFoundException {
        String[] check = {};
        scanner = new Scanner(userData);

        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            check = nextLine.split(" ");
            if (check[0].equals(username)) {
                System.out.println("User Exists");
                scanner.close();
                return true;
            }
        }
        scanner.close();
        return false;
    }

    /*
     * Method to register a new user to userData param: username - from JTextField
     * param: password - from JTextField
     */
    public int register(String username, String password) throws IOException {
        if (userExists(username)){
            return 1;
        }
        if (username.contains(" ")||password.contains(" ")){
            return 2;
        }
        String writeString = (username + " " + password);
        PrintWriter pw = new PrintWriter(new FileWriter(userData, true));
        pw.println(writeString);
        pw.close();
        return 0;
    }


    

}
