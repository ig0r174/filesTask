package services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class UserProfile {
    private final String mainDirectory = "/Users/igorpetrov/Documents/filesTaskDirectory";
    private final String login;
    private final String pass;
    private final String email;
    private final String homeDirectory;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.homeDirectory = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }
    public String getMainDirectory() {
        return mainDirectory;
    }

    public String getFullPath() throws IOException {
        String userHomeDirectory = getMainDirectory() + "/" + getHomeDirectory();

        if( !Files.isDirectory(Paths.get(userHomeDirectory)) )
            Files.createDirectories(Paths.get(userHomeDirectory));

        return userHomeDirectory + "/";
    }
}
