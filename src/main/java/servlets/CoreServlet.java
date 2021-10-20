package servlets;

import services.AccountService;
import services.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CoreServlet", value = "/CoreServlet", urlPatterns = {"/"})
public class CoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sessionId = request.getSession().getId();
        UserProfile profile = AccountService.getUserBySessionId(sessionId);
        response.sendRedirect(profile == null ? "/login" : "/home");

    }

}
