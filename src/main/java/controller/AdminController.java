package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.CreepyGuyDAOImpl;
import dao.LoginDAOImpl;
import dao.MentorDAOImplementation;
import dao.UserDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.*;
import helpers.cookie.CookieHelper;
import model.user.Mentor;
import model.user.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminController implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";

    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper = new CookieHelper();
    private JDBCConnectionPool connectionPool;
    private LoginDAO loginDAO;
    private UserDAO userDAO;
    private CodecoolerDAO codecoolerDAO;
    private RoomsDAO roomsDAO;
    private DAOCreepyGuy creepyGuyDAO;
    private MentorDAO mentorDAO;
    private DAOStore daoStore;
    private DAOQuests daoQuests;

    public AdminController(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.loginDAO = new LoginDAOImpl(connectionPool);
        this.userDAO = new UserDaoImpl(connectionPool);
        this.creepyGuyDAO = new CreepyGuyDAOImpl(connectionPool);
        this.mentorDAO = new MentorDAOImplementation(connectionPool);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.cookie = getCookieBySessionCookieName(httpExchange);

        URI uri = httpExchange.getRequestURI();
        System.out.println("looking for: " + uri.getPath());
        String path = uri.getPath();

        String[] pathParts = path.split("/");
        String urlEnding = pathParts[pathParts.length - 1];
        int index = getIdFromURL(httpExchange,urlEnding);

        if (path.equals("/queststore/admin/" + index)) {
            displayProfile(httpExchange);
        } else if (path.equals("/queststore/admin/addNewMentor/" + index)) {
            displayAddNewMentor(httpExchange);
        } else if (path.equals("/queststore/admin/logout/" + index)) {
            String sessionid = getSessionIdFromCookie(cookie);
            loginDAO.removeSessionid(sessionid);
            goToLogin(httpExchange);
        } else {
            goToLogin(httpExchange);
        }

    }

    private void displayAddNewMentor(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                String method = httpExchange.getRequestMethod();

                if (method.equals("POST")) {
                    System.out.println("post nowego mentora");

                    InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String formData = br.readLine();

                    System.out.println(formData);
                    Map inputs = parseFormData(formData);

                    //add user to DB
                    String firstName = inputs.get("firstname").toString();
                    String lastName = inputs.get("lastname").toString();
                    String login = inputs.get("login").toString();
                    String password = inputs.get("password").toString();
                    String mail = inputs.get("mail").toString();

                    //add mentor to DB
                    Mentor mentor = new Mentor(666, login, password, "mentor", firstName, lastName, mail);
                    mentorDAO.addNewMentor(mentor);

                    System.out.println("location change");
                    httpExchange.getResponseHeaders().set("Location", "/queststore/login");
                    httpExchange.sendResponseHeaders(302,0);
                }

                if (method.equals("GET")) {
                    User admin = creepyGuyDAO.getCreepyGuy("admin");
                    String response = generateResponseAddNewMentor(admin);
                    sendResponse(httpExchange, response);
                }
            }
        }
    }

    private Map parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private void displayProfile(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                User admin = creepyGuyDAO.getCreepyGuy("admin");
                String response = generateResponseProfile(admin);
                sendResponse(httpExchange, response);
            }
        }
    }

    private String generateResponseProfile(User admin) {
        admin = userDAO.getMentorById(admin.getId());
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/adminProfile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("admin", admin);
        model.with("mentors", mentorDAO.getListOfMentors());
        String response = template.render(model);
        return response;
    }

    private String generateResponseAddNewMentor(User admin) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/addNewMentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("admin", creepyGuyDAO.getCreepyGuy("admin"));
        String response = template.render(model);
        return response;
    }


    private String getSessionIdFromCookie(Optional<HttpCookie> cookie) {
        String cookieValue = cookie.get().getValue();
        return cookieValue.substring(1, cookieValue.length() - 1);
    }

    private int getIdFromURL(HttpExchange httpExchange, String urlEnding) throws IOException {
        try {
            return Integer.parseInt(urlEnding);
        } catch (NumberFormatException e) {
            goToLogin(httpExchange);
        }
        return 0;
    }

    private void goToLogin(HttpExchange httpExchange) throws IOException {
        System.out.println("location change");
        httpExchange.getResponseHeaders().set("Location", "/queststore/login");
        httpExchange.sendResponseHeaders(302, 0);
    }

    private Optional<HttpCookie> getCookieBySessionCookieName(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
