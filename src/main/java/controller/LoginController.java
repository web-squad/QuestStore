package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.LoginDAOImpl;
import dao.UserDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.LoginDAO;
import dao.interfaces.UserDAO;
import helpers.cookie.CookieHelper;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.*;

public class LoginController implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private CookieHelper cookieHelper = new CookieHelper();
    private JDBCConnectionPool connectionPool;
    private LoginDAO loginDAO;
    private UserDAO userDAO;

    public LoginController(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.loginDAO = new LoginDAOImpl(connectionPool);
        this.userDAO = new UserDaoImpl(connectionPool);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Optional<HttpCookie> cookie = getCookieBySessionCookieName(httpExchange);
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            System.out.println("get");
            if (cookie.isPresent()) {
                String sessionid = getSessionIdFromCookie(cookie);
                if (loginDAO.isActiveSession(sessionid)) {
                    int id = loginDAO.getUserId(sessionid);
                    String userType = userDAO.getUserType(id);
                    redirect(httpExchange, userType, id);
                }
            }
        }

        if (method.equals("POST")) {
            System.out.println("post");
            String formData = getFormData(httpExchange);
            Map inputs = parseFormData(formData);
            String username = inputs.get("username").toString();
            String password = inputs.get("password").toString();

            if (userDAO.isLoginSuccessful(username, password)) {
                System.out.println("is succesful");
                String sessionId = generateSessionId();
                cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
                int id = userDAO.getUserId(username, password);
                String userType = userDAO.getUserType(id);
                loginDAO.activateSessionId(sessionId, id);
                redirect(httpExchange, userType, id);
            }
        }
        String response = generateResponse();
        sendResponse(httpExchange, response);
    }

    private Optional<HttpCookie> getCookieBySessionCookieName(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private String getSessionIdFromCookie(Optional<HttpCookie> cookie) {
        String cookieValue = cookie.get().getValue();
        return cookieValue.substring(1, cookieValue.length()-1);
    }

    private void redirect(HttpExchange httpExchange, String userType, int id) throws IOException {
        httpExchange.getResponseHeaders().set("Location", "/queststore/" + userType + "/" + id);
        httpExchange.sendResponseHeaders(302,0);
    }

    private String getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        System.out.println(formData);
        return formData;
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private String generateResponse() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/login.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        return response;
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

