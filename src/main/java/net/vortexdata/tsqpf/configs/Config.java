package net.vortexdata.tsqpf.configs;


import net.vortexdata.tsqpf.installers.InstallConfig;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * Config fields prototype
 *
 * @author Sandro Kierner
 * @since 2.0.0
 */
public class Config implements ConfigInterface {

    protected String path = "";
    protected HashMap<String, String> values;
    protected HashMap<String, String> defaultValues;

    public Config(String path) {
        this.path = path;
    }

    public boolean load() {

        HashMap<String, String> values = new HashMap<String, String>();
        File file = new File(path);
        // net.vortexdata.tsqpf.Test if config exists
        if (!file.exists()) {
            // Return empty map if config is empty.
            values = null;
            InstallConfig installConfig = new InstallConfig();
            installConfig.create(this);
        } else {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(path));

                // Load all keys & values
                Set<Object> keys = prop.keySet();
                for (Object k : keys) {
                    values.put((String) k, prop.getProperty((String) k));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.values = values;
        return true;

    }

    @Override
    public HashMap<String, String> getValues() {
        return values;
    }

    @Override
    public HashMap<String, String> getDefaultValues() {
        return defaultValues;
    }

    @Override
    public String getPath() {
        return path;
    }

    public String getProperty(String key) {
        if (values == null || values.isEmpty())
            return defaultValues.get(key);
        else
            return values.get(key);
    }

}
