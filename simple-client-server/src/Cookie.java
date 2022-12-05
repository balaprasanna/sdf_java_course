import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cookie {

    public Cookie() {
    }

    public List<String> ReadFromFile() {
        List<String> fortuneList = new ArrayList<>();
        BufferedReader bfr;
        try {
            bfr = new BufferedReader(new FileReader("src/cookie.txt"));
            String line;     
            while (null != (line = bfr.readLine())) {
                fortuneList.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fortuneList;
    }

    public String RandomlyReturnACookie() {
        List<String> items = this.ReadFromFile();
        Collections.shuffle(items);
        return items.get(0);
    }
}
