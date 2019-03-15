package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import dao.connectionPool.JDBCConnectionPool;


import dao.*;
import dao.interfaces.*;
import helpers.mime.MimeTypeResolver;
import helpers.cookie.CookieHelper;
import model.user.Codecooler;
import model.user.Mentor;
import model.user.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class MentorController implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";

    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper = new CookieHelper();
    private JDBCConnectionPool connectionPool;
    private LoginDAO loginDAO;
    private UserDAO userDAO;
    private CodecoolerDAO codecoolerDAO;
    private RoomsDAO roomsDAO;
    private MentorDAO mentorDAO;
    private DAOStore daoStore;
    private DAOQuests daoQuests;

    public MentorController(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.loginDAO = new LoginDAOImpl(connectionPool);
        this.userDAO = new UserDaoImpl(connectionPool);
        this.codecoolerDAO = new CodecoolerDaoImpl(connectionPool);
        this.roomsDAO = new RoomsDaoImpl(connectionPool);
        this.mentorDAO = new MentorDAOImplementation(connectionPool);
        this.daoStore = new StoreDaoImpl(connectionPool);
        this.daoQuests = new QuestsDaoImpl(connectionPool);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.cookie = getCookieBySessionCookieName(httpExchange);
        String response = "";
        String method = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        System.out.println("looking for: " + uri.getPath());
        String path = uri.getPath();

        String[] pathParts = path.split("/");
        String urlEnding = pathParts[pathParts.length - 1];
        int index = getIdFromURL(httpExchange, urlEnding);
        String indexOfStudentToEdit = pathParts[pathParts.length - 2];

        if (path.equals("/queststore/mentor/" + index)) {
            displayProfile(httpExchange);
        } else if (path.equals("/queststore/mentor/addNewStudent/" + index) ) {
            displayAddNewStudentPage(httpExchange);
        } else if (path.equals("/queststore/mentor/editStudent/" + indexOfStudentToEdit + "/" + index) ) {
            displayEditStudentPage(httpExchange);
        }  else if (path.equals("/queststore/mentor/logout/" + index)) {
            String sessionid = getSessionIdFromCookie(cookie);
            loginDAO.removeSessionid(sessionid);
            goToLogin(httpExchange);
        } else {
            goToLogin(httpExchange);
        }
    }

    private Optional<HttpCookie> getCookieBySessionCookieName(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private int getIdFromURL(HttpExchange httpExchange, String urlEnding) throws IOException {
        try {
            return Integer.parseInt(urlEnding);
        } catch(NumberFormatException e){
            goToLogin(httpExchange);
        }
        return 0;
    }

    private void goToLogin(HttpExchange httpExchange) throws IOException {
        System.out.println("location change");
        httpExchange.getResponseHeaders().set("Location", "/queststore/login");
        httpExchange.sendResponseHeaders(302,0);
    }

    private void displayProfile(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                Mentor mentor = mentorDAO.getMentorById(id);
                String response = generateResponseProfile(mentor);
                sendResponse(httpExchange, response);
            }
        }
    }

    private String generateResponseProfile(Mentor mentor) {
        mentor = userDAO.getMentorById(mentor.getId());
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/mentorprofile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("codecoolers", codecoolerDAO.getAllCodecoolers());
        String response = template.render(model);
        return response;
    }

    private void displayAddNewStudentPage(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                Mentor mentor = mentorDAO.getMentorById(id);
                String method = httpExchange.getRequestMethod();

                if (method.equals("POST")) {
                    InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String formData = br.readLine();
                    Map inputs = parseFormData(formData);
                    String login = "login";
                    String password = "password";
                    String userType = "codecooler";
                    String name = inputs.get("firstname").toString();
                    String surname = inputs.get("lastname").toString();
                    Codecooler codecooler = new Codecooler(666, login, password, userType, name, surname);
                    codecoolerDAO.addCodecoolerToUsersTable(codecooler);
                    int idFromUsers = codecoolerDAO.getCodecoolerIdByLoginPasswordNameSurname(codecooler);
                    codecooler = new Codecooler(idFromUsers, login, password, userType, name, surname);
                    codecoolerDAO.addCodecooler(codecooler);
                    System.out.println("Codecooler added to table users and table codecooler");

                    System.out.println("location change");
                    httpExchange.getResponseHeaders().set("Location", "/queststore/login");
                    httpExchange.sendResponseHeaders(302,0);
                }
                if (method.equals("GET")) {
                    String response = generateResponseAddNewStudent(mentor);
                    sendResponse(httpExchange, response);
                }
            }
        }
    }

    private void displayEditStudentPage(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                Mentor mentor = mentorDAO.getMentorById(id);
                String method = httpExchange.getRequestMethod();

                if (method.equals("POST")) {
                    System.out.println("post wlecial");
                    InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String formData = br.readLine();
                    Map inputs = parseFormData(formData);

                    String login = inputs.get("login").toString();
                    String password = inputs.get("password").toString();
                    String name = inputs.get("name").toString();
                    String surname = inputs.get("surname").toString();
                    int studentRoom = Integer.valueOf(inputs.get("studentRoom").toString());
                    int studentTeam = Integer.parseInt(inputs.get("studentTeam").toString());
                    int studentEarnings = Integer.parseInt(inputs.get("studentEarnings").toString());

                    URI uri = httpExchange.getRequestURI();
                    String path = uri.getPath();
                    String[] pathParts = path.split("/");
                    String urlEnding = pathParts[pathParts.length - 1];
                    String indexOfStudentToEdit = pathParts[pathParts.length - 2];

                    Codecooler codecooler = new Codecooler(Integer.parseInt(indexOfStudentToEdit), login, password, "codecooler", name, surname);
                    codecooler.setRoomId(studentRoom);
                    codecooler.setTeamId(studentTeam);
                    codecooler.setEarnings(studentEarnings);
                    codecoolerDAO.updateCodecooler(codecooler);
                    System.out.println("Codecooler updated in database");

                    System.out.println("location change");
                    httpExchange.getResponseHeaders().set("Location", "/queststore/login");
                    httpExchange.sendResponseHeaders(302,0);
                }
                if (method.equals("GET")) {
                    URI uri = httpExchange.getRequestURI();
                    String path = uri.getPath();
                    String[] pathParts = path.split("/");
                    String urlEnding = pathParts[pathParts.length - 1];
                    int index = getIdFromURL(httpExchange, urlEnding);
                    String indexOfStudentToEdit = pathParts[pathParts.length - 2];
                    Codecooler codecooler = codecoolerDAO.getCodecoolerById(Integer.parseInt(indexOfStudentToEdit));
                    String response = generateResponseEditStudent(codecooler);
                    sendResponse(httpExchange, response);
                }
            }
        }
    }

    private String generateResponseEditStudent(Codecooler codecooler) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/editStudent.twig");
        JtwigModel model = JtwigModel.newModel();
//        model.with("id", mentorDAO.getMentorById(mentor.getId()));
        model.with("codecooler", codecooler);
        String response = template.render(model);
        return response;
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
            System.out.println("mapkey:"+keyValue[0] + "value:"+value);

        }
        return map;
    }

    private String generateResponseAddNewStudent(Mentor mentor) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/addNewStudent.twig");
        JtwigModel model = JtwigModel.newModel();
//        model.with("id", mentorDAO.getMentorById(mentor.getId()));
        String response = template.render(model);
        return response;
    }

    private String getSessionIdFromCookie(Optional<HttpCookie> cookie) {
        String cookieValue = cookie.get().getValue();
        return cookieValue.substring(1, cookieValue.length()-1);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
