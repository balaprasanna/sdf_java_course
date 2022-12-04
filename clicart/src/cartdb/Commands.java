package cartdb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Commands {

    public static String EXIT = "exit";
    public static String LOGIN = "login";
    public static String ADD = "add";
    public static String SAVE = "save";
    public static String LIST = "list";
    public static String USERS = "users";

    public static Set<String> VALID_COMMANDS = new HashSet<String>(
            Arrays.asList(
                    EXIT,
                    LOGIN,
                    ADD,
                    SAVE,
                    LIST,
                    USERS));

    public static boolean isValidCommand(String input) {
        // validate the first word only.
        String[] parts = input.split(" ");
        return VALID_COMMANDS.contains(parts[0]);
    }
}
