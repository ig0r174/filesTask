package services;

import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class APIService {
    private String type;
    private Map<String, String[]> parametersMap;

    public APIService(String type, Map<String, String[]> parametersMap){
        this.type = type;
        this.parametersMap = parametersMap;
    }

    public void processData() {

        if(Objects.equals(type, "login")){

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

        } else if(Objects.equals(type, "signup")){

        } else {
            throw new NullPointerException("Unknown request");
        }
        //System.out.println(Arrays.stream(parameterMap.get("login")).toArray()[0]);
    }
}
