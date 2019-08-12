package net.vortexdata.tsqpf.installers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class InstallConfig {

    public boolean create(String path, HashMap<String, String> defaultValues) {
        // Purge if file remains
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        Properties prop = new Properties();
        prop.putAll(defaultValues);

        File configFile = new File(path);
        configFile.getParentFile().mkdirs();
        try {
            FileOutputStream fileOut = new FileOutputStream(configFile, false);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();

            prop.store(fileOut, "Generated at: " + dateFormat.format(date));
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}