package servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(name = "LoginServlet", value = "/LoginServlet", urlPatterns = {"/login", "/signup", "/api"})
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Arrays.asList(new String[]{"/login", "/signup"}).contains(request.getRequestURI()))
            getServletContext().getRequestDispatcher(request.getRequestURI() + ".jsp").forward(request, response);
        else
            response.sendRedirect("/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");

        if( uri.length == 3
            && Objects.equals(uri[1], "api")
            && Arrays.asList(new String[]{"login", "signup"}).contains(uri[2]) )
            response.getWriter().println(uri[2]);
        else
            response.getWriter().println("You have sent an unknown request");

        /*

        if(Arrays.asList(new String[]{"/api/"}).contains(request.getRequestURI()))
            getServletContext().getRequestDispatcher(request.getRequestURI() + ".jsp").forward(request, response);
        else
            response.sendRedirect("/login");

         */
    }
}
