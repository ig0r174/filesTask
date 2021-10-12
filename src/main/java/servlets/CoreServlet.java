package servlets;

import services.AccountService;
import services.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CoreServlet", value = "/CoreServlet", urlPatterns = {"/"})
public class CoreServlet extends HttpServlet {
    private final AccountService accountService;

    public CoreServlet() {
        this.accountService = new AccountService();
    }

    public CoreServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);

        /*
        UserProfile userProfile = new UserProfile("admin");
        accountService.addNewUser(userProfile);
        accountService.addSession(sessionId, userProfile);
        */

        response.sendRedirect(profile == null ? "/login" : "/home");
        //response.getWriter().println(profile == null ? "/login" : "/home");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
