package cartdb;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHanlder {
    
    private String baseDir;
    public FileHanlder(String folder) {
        this.baseDir = folder;
    }

    public void Save(String filename, ArrayList<String> items) {
        String fname = String.format("%s/%s.db",this.baseDir, filename);
        try {
            FileWriter fw = new FileWriter(fname, true);
            for (String line: items)
                fw.write(line + "\n");            
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void listfilesInDir(){
        File dir = new File(this.baseDir);
        File[] fnames = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File fobj, String fname) {
                return fname.endsWith(".db");
            }
            
        });
        
        for (File f : fnames) {
            System.out.println(":: " + f.getName().replace(".db", ""));
        }
    }
}
