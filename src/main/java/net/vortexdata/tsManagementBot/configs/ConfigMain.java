package net.vortexdata.tsManagementBot.configs;

import net.vortexdata.tsManagementBot.installers.*;

import java.io.*;
import java.util.*;

public class ConfigMain extends ConfigField implements Config {



    public ConfigMain() {
        super("configs/main.properties");
        // Default Values
        defaultValues.put("serverAddress", "127.0.0.1");
        defaultValues.put("queryPort", "10011");
        defaultValues.put("queryUser", "admin");
        defaultValues.put("queryPassword", "password");
        defaultValues.put("virtualServer", "1");
        defaultValues.put("botNickname", "Server Management Bot");
    }

    public void load() {
        ConfigLoader cLoader = new ConfigLoader();
        values = cLoader.load(path);
        if (values.size() < defaultValues.size()) {
            InstallConfig iConfig = new InstallConfig();
            iConfig.create(path, defaultValues);
        }
    }

    public String getProperty(String key) {
        return values.get(key);
    }

    public HashMap<String, String> getValues() {
        return null;
    }

    public HashMap<String, String> getDefaultValues() {
        return null;
    }

    public String getPath() {
        return null;
    }

}
