package servlets;

import services.AccountService;
import services.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CoreServlet", value = "/CoreServlet", urlPatterns = {"/"})
public class CoreServlet extends HttpServlet {

    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        getServletContext().setAttribute("accountService", accountService);
        //System.out.println(accountService);

        //System.out.println(sessionId);
        //System.out.println(profile);

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
