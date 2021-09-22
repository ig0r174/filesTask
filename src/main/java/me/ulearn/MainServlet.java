package me.ulearn;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config ) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if( req.getParameter("download") == null ){

            req.setAttribute(
                    "directory",
                    new DirectoryParser(req.getParameter("path") == null ? "/" : req.getParameter("path"))
            );
            getServletContext().getRequestDispatcher("/directory.jsp").forward(req, resp);

        } else {

            String filePath = req.getParameter("path");
            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            ServletContext context = getServletContext();

            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            resp.setContentType(mimeType);
            resp.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            resp.setHeader(headerKey, headerValue);

            OutputStream outStream = resp.getOutputStream();

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
    public void destroy() {

    }

}
