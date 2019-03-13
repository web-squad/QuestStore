package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import dao.connectionPool.JDBCConnectionPool;


import dao.*;
import dao.interfaces.*;
import helpers.mime.MimeTypeResolver;
import helpers.cookie.CookieHelper;
import model.user.Mentor;
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
//        this.codecoolerDAO = new CodecoolerDaoImpl(connectionPool);
//        this.roomsDAO = new RoomsDaoImpl(connectionPool);
        this.mentorDAO = new MentorDAOImplementation(connectionPool);
//        this.daoStore = new StoreDaoImpl(connectionPool);
//        this.daoQuests = new QuestsDaoImpl(connectionPool);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.cookie = getCookieBySessionCookieName(httpExchange);

        URI uri = httpExchange.getRequestURI();
        System.out.println("looking for: " + uri.getPath());
        String path = uri.getPath();

            String[] pathParts = path.split("/");
            String urlEnding = pathParts[pathParts.length - 1];
            int index = getIdFromURL(httpExchange, urlEnding);

            if (path.equals("/queststore/mentor/" + index)) {
                displayProfile(httpExchange);
            } else if (path.equals("/queststore/mentor/addNewStudent/" + index) ) {
                displayAddNewStudentPage(httpExchange);

//        }  else if (path.equals("/queststore/codecooler/wallet") ) {
//
//        }  else if (path.equals("/queststore/codecooler/store") ) {
//
//        }  else if (path.equals("/queststore/login") ) {
            } else {
                goToLogin(httpExchange);
            }

    }

    private Optional<HttpCookie> getCookieBySessionCookieName(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }
//    private void handleFile(HttpExchange httpExchange, String path) throws IOException {
//        ClassLoader classLoader = getClass().getClassLoader();
//        URL fileURL = classLoader.getResource("." + path);
//        if (fileURL == null) {
//            send404(httpExchange);
//        } else {
//            sendFile(httpExchange, fileURL);
//        }
//    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
        File file = new File(fileURL.getFile());
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();
        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        // send the file
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer,0,count);
        }
        os.close();
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
        String response = template.render(model);
        return response;
    }

    private void displayAddNewStudentPage(HttpExchange httpExchange) throws IOException {
        if (cookie.isPresent()) {
            String sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                int id = loginDAO.getUserId(sessionid);
                Mentor mentor = mentorDAO.getMentorById(id);
                String response = generateResponseAddNewStudent(mentor);
                sendResponse(httpExchange, response);
            }
        }
    }

    private String generateResponseAddNewStudent(Mentor mentor) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/addNewStudent.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("id", mentorDAO.getMentorById(mentor.getId()));
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
