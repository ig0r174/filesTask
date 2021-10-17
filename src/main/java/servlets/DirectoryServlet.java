package servlets;

import me.ulearn.DirectoryParser;
import services.AccountService;
import services.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(name = "DirectoryServlet", value = "/DirectoryServlet", urlPatterns = {"/home"})
public class DirectoryServlet extends HttpServlet {

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

        UserProfile userProfile = accountService.getUserBySessionId(request.getSession().getId());
        String homeDirectory = userProfile.getFullPath();
        String filePath = request.getParameter("path") == null ? homeDirectory : homeDirectory + request.getParameter("path");

        if( accountService.getUserBySessionId(request.getSession().getId()) == null ){
            response.sendRedirect("/login");
        }

        //System.out.println(homeDirectory);
        //System.out.println(filePath);

        if (!filePath.startsWith(homeDirectory) || filePath.contains("..") ){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if( request.getParameter("download") == null ){

            request.setAttribute("homeDirectory", homeDirectory);
            request.setAttribute("login", userProfile.getLogin());
            request.setAttribute(
                    "directory",
                    new DirectoryParser(filePath, accountService.getUserBySessionId(request.getSession().getId()))
            );

            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

        } else {

            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            ServletContext context = getServletContext();

            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();

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
                && Arrays.asList(new String[]{"login", "signup"}).contains(uri[2]) )

            if(Objects.equals(uri[2], "login")){

                if (login == null || pass == null) {
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                UserProfile profile = accountService.getUserByLogin(login);
                if (profile == null || !profile.getPass().equals(pass)) {
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                accountService.addSession(request.getSession().getId(), profile);

            } else if(Objects.equals(uri[2], "signup")){

                if( login == null || pass == null || email == null) {
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                if( accountService.getUserByLogin(login) != null ){
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
                    return;
                }

                if( !email.contains("@") ){
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    return;
                }

                UserProfile profile = new UserProfile(login, pass, email);
                accountService.addNewUser(profile);
                accountService.addSession(request.getSession().getId(), profile);

            }

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
