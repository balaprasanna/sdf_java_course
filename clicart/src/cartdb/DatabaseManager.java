package cartdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DatabaseManager {
    
    public static final String DEFALT_DB_LOCATION = "src/db";


    private HashMap<String, ArrayList<String>> userMap;
    private ArrayList<String> currentUser;
    private String currentUserName;
    private String baseFolder;

    // Constructors

    public DatabaseManager() {
        this.baseFolder = DEFALT_DB_LOCATION;
        this.setup();
    }

    public DatabaseManager(String baseFolder) {
        this.baseFolder = baseFolder;
        this.setup();
    }

    public void createBaseFolderIfnotExisit() {
        Path p = Paths.get(this.baseFolder);
        if (!Files.isDirectory(p)) {
            try {
            Files.createDirectories(p);
            } catch (Exception e) {
                System.out.println("Error: "+ e.getMessage());
            }
        }
    }

    public HashMap<String, ArrayList<String>> initUserMap() {
        // Load a fresh user map
        HashMap<String, ArrayList<String>> um = new HashMap<String, ArrayList<String>>();
        
        // load existing db from
        File dir = new File(this.baseFolder);
        File[] allFiles = dir.listFiles();
        for (File f : allFiles) {
            try {
                FileReader freader =  new FileReader(f);
                BufferedReader bfr = new BufferedReader(freader);
                String line;

                String userName = f.getName().replace(".db", "");

                ArrayList<String> oldItemsFromFile = new ArrayList<String>();
                while (null != (line = bfr.readLine())) {
                    oldItemsFromFile.add(line);
                }
                um.put(userName, oldItemsFromFile);
                bfr.close();
                freader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return um;

    }

    public void setup(){
        // Configure the DB folder
        this.createBaseFolderIfnotExisit();
        
        // Load previous values from File
        this.userMap = this.initUserMap();
    }

    public void connect() {
        System.out.println("Welcome to Shopping cart Shell !!! >");
        Scanner sc = new Scanner(System.in);
        String input;
        while (null != (input = sc.nextLine())) {
            input = input.trim();

            if (input.equalsIgnoreCase("exit"))
                return;

            if (!Commands.isValidCommand(input))
                System.out.println("Invalid command=> " + input);
            else
                this.processQuery(input);
        }
        sc.close();
    }

    public ArrayList<String> GetUserObject(String givenUser) {
        return this.userMap.getOrDefault(givenUser, new ArrayList<String>());
    }

    public void processQuery(String q) {
        System.out.println(String.format("Processing: %s", q));
        
        // Switch on commands & call the appropriate func
        Scanner lsc = new Scanner(q);
        String command = lsc.next();
        
        // login <user>
        if (command.equalsIgnoreCase(Commands.LOGIN)) {
            // Load the fileDB for the given user
            // Create the file if not found
            // Set the current user Object to be loggen in user
            String givenUser = lsc.nextLine().trim();
            this.currentUserName = givenUser;
            this.currentUser = this.GetUserObject(givenUser);
            System.out.println("Loggin as "+ givenUser);
        }

        // lists
        if (command.equalsIgnoreCase(Commands.LIST)) {
            for (String item : this.currentUser) {
                System.out.println("-> "+item);
            }
        }

        // add x,y,z
        if (command.equalsIgnoreCase(Commands.ADD)) {
            // Add items to the current user's array list
            String[] itemToAdd = lsc.nextLine().trim().split(",");

            // Get current user
            for (String item : itemToAdd) {
                this.currentUser.add(item.trim());
            }
            System.out.println("Items added successfully !!");
        }

        // save
        if (command.equalsIgnoreCase(Commands.SAVE)) {
            // Dump the user ArrayList to DB file
            new FileHanlder(this.baseFolder).Save(this.currentUserName, currentUser);
        }

        if (command.equalsIgnoreCase(Commands.USERS)) {
            // List all the file names in the db directory
            new FileHanlder(this.baseFolder).listfilesInDir();
        }
        
        lsc.close();
    }

    public void close() {
        System.out.println("Closing connection to Shell.");
        System.out.println("BYE !!!");
    }

}
