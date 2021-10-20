package services;

import database.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserProfile> loginToProfile;
    private static final Map<String, UserProfile> sessionIdToProfile;
    private static final DBService dbService;

    static {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbService = new DBService();
        initializeDBService();
    }

    private static void initializeDBService() {
        try{
            ResultSet rs = dbService.getStatement().executeQuery("SELECT `Login`, `Pass`, `Email` FROM `Users`");
            while (rs.next()){

                loginToProfile.put(
                        rs.getString(1),
                        new UserProfile(rs.getString(1), rs.getString(2), rs.getString(3))
                );
                String login = rs.getString(1);
                System.out.println("Login: " + login);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createNewUser(String login, String email, String pass){
        try{
            String sql = String.format("INSERT INTO `Users` (`Login`, `Email`, `Pass`) values ('%s', '%s', '%s');", login, email, pass);
            dbService.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewUser(UserProfile userProfile) {
        createNewUser(userProfile.getLogin(), userProfile.getEmail(), userProfile.getPass());
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.getOrDefault(login, null);
    }

    public static UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.getOrDefault(sessionId, null);
    }

    public static void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public static void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
