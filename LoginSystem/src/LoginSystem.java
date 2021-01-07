import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;

public class LoginSystem {

    private File userData;
    private Scanner scanner;
    private HashMap<String, String> userDataMap;

    public LoginSystem() throws Exception {
        try {
            userData = new File("UserFile.txt");
        } catch (Exception e) {
            //Empty Catch
        }


        //HashMap username password pairs to make searching faster.
        userDataMap = new HashMap<>();
        scanner = new Scanner(userData);
        String[] check = {};

        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            check = nextLine.split(" ");
            userDataMap.put(check[0],check[1]);
        }

    }

    /*
     * Method to search and confirm if a user's username and password are in the
     * userData file param: username - from JTextField param: password - from
     * JTextField
     */
    public boolean login(String username, String password) throws Exception {

        if (userDataMap.containsKey(username) && Password.check(password, userDataMap.get(username))) {
            System.out.println("Successful Login");
            return true;
        }else {
            System.out.println("Unsuccessful Login");
            System.out.println(userDataMap.containsKey(username));
            return false;
        }
    }

    /*
     * Method to check if Username is taken param: username - from JTextField
     */
    public boolean userExists(String username) throws FileNotFoundException {
        System.out.println(userDataMap.containsKey(username));
        System.out.println(userDataMap.toString());
        return userDataMap.containsKey(username);
    }

    /*
     * Method to register a new user to userData 
     * param: username - from JTextField
     * param: password - from JTextField
     */
    public int register(String username, String password) throws Exception {
        if (userExists(username)){
            return 1;
        }
        if (username.contains(" ")||password.contains(" ")){
            return 2;
        }

        String hashedPass = Password.getSaltedHash(password);

        userDataMap.put(username, hashedPass);

        String writeString = (username + " " + hashedPass);
        PrintWriter pw = new PrintWriter(new FileWriter(userData, true));
        pw.println(writeString);
        pw.close();
        return 0;
    }


    

}
