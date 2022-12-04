
import cartdb.DatabaseManager;

public class App {
    public static void main(String[] args) {
        DatabaseManager cartDBManager = new DatabaseManager("src/mydb");
        cartDBManager.connect();
        cartDBManager.close();
    }
}
