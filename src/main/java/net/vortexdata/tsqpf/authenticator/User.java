package net.vortexdata.tsqpf.authenticator;

import java.util.HashMap;

public class User {

    private UserGroup group;
    private String username;
    private String password;
    private String salt;

    HashMap<String, String> info = new HashMap<>();

    public User(String username, String password, UserGroup group, HashMap<String, String> info, String salt) {
        this.username = username;
        this.password = password;
        this.group = group;
        this.info = info;
    }

    public UserGroup getGroup() {
        return group;
    }

    public String getUsername() {
        return username;
    }

    public String serialize() {
        return this.username + ";" + this.password + ";" + this.group.toString() + ";" + this.info.get("fullName") + ";" + this.info.get("telephone") + ";" + this.info.get("address") + ";" + this.info.get("country") + ";" + salt + ";";
    }

    public String getInfo(String key) {
        return info.get(key);
    }

}
