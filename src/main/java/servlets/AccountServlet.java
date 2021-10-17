package servlets;

import services.AccountService;
import services.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import services.MessagesService;

@WebServlet(name = "LoginServlet", value = "/LoginServlet", urlPatterns = {"/login", "/signup", "/api"})
public class AccountServlet extends HttpServlet {

    private AccountService accountService;

    private void SetService(HttpServletRequest request, HttpServletResponse response) {

        try {
            this.accountService = (AccountService) getServletContext().getAttribute("accountService");
            if( this.accountService == null )
                throw new IllegalStateException("Account");
        } catch (Exception e) {
            try {
                response.sendRedirect("/");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SetService(request, response);

        if( accountService.getUserBySessionId(request.getSession().getId()) == null ){
            if(Arrays.asList(new String[]{"/login", "/signup"}).contains(request.getRequestURI()))
                getServletContext().getRequestDispatcher(request.getRequestURI() + ".jsp").forward(request, response);
            else
                response.sendRedirect("/login");
        } else {
            response.sendRedirect("/home");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        SetService(request, response);

        String[] uri = request.getRequestURI().split("/");
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");

        //System.out.println(Arrays.toString(new String[]{login, pass, email}));

        if( uri.length == 3
            && Objects.equals(uri[1], "api")
            && Arrays.asList(new String[]{"login", "signup"}).contains(uri[2]) ) {

            if (Objects.equals(uri[2], "login")) {

                if (login == null || pass == null) {
                    response.getWriter().println(new MessagesService("error", "You need to enter all required fields"));
                    return;
                }

                UserProfile profile = accountService.getUserByLogin(login);
                if (profile == null || !profile.getPass().equals(pass)) {
                    response.getWriter().println(new MessagesService("error", "User with that login was not found or password is incorrect"));
                    return;
                }

                accountService.addSession(request.getSession().getId(), profile);
                response.getWriter().println(new MessagesService("redirect", "/home"));

            } else if (Objects.equals(uri[2], "signup")) {

                if (login == null || pass == null || email == null) {
                    response.getWriter().println(new MessagesService("error", "You need to enter all required fields"));
                    return;
                }

                if (accountService.getUserByLogin(login) != null) {
                    response.getWriter().println(new MessagesService("error", "User with this login is already registered"));
                    return;
                }

                if (!email.contains("@")) {
                    response.getWriter().println(new MessagesService("error", "Email has bad format"));
                    return;
                }

                UserProfile profile = new UserProfile(login, pass, email);
                accountService.addNewUser(profile);
                accountService.addSession(request.getSession().getId(), profile);
                response.getWriter().println(new MessagesService("redirect", "/home"));


            }

        } else response.getWriter().println(new MessagesService("error", "You have sent an unknown request"));

    }

    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.getWriter().println(new MessagesService("error", "You are not logged in"));
        } else {
            accountService.deleteSession(sessionId);
            response.getWriter().println(new MessagesService("redirect", "/login"));
        }

    }

}
