package services;

import database.DBService;
import database.UsersDAO;
import database.UsersDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserProfile> sessionIdToProfile;
    private static final DBService dbService;
    private static final UsersDAO usersDao;

    static {
        sessionIdToProfile = new HashMap<>();
        dbService = new DBService();
        usersDao = new UsersDAO();
    }

    public static void addNewUser(UserProfile userProfile) {
        usersDao.addUser(userProfile.getLogin(), userProfile.getPass(), userProfile.getEmail());
    }

    public static UserProfile getUserByLogin(String login) {
        UsersDataSet userData = usersDao.getUserByLogin(login);
        return userData == null ? null : new UserProfile(userData.getLogin(), userData.getPass(), userData.getEmail());
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
