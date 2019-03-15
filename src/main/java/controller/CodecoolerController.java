package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.*;
import helpers.cookie.CookieHelper;
import model.Item;
import model.Quest;
import model.Room;
import model.user.Codecooler;
import model.user.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;

public class CodecoolerController implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private CookieHelper cookieHelper = new CookieHelper();
    private JDBCConnectionPool connectionPool;
    private LoginDAO loginDAO;
    private UserDAO userDAO;
    private CodecoolerDAO codecoolerDAO;
    private RoomsDAO roomsDAO;
    private MentorDAO mentorDAO;
    private DAOStore daoStore;
    private DAOQuests daoQuests;
    private Codecooler codecooler;
    private int codecoolerId;
    private String sessionid;

    public CodecoolerController(JDBCConnectionPool connectionPool) {
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
        Optional<HttpCookie> cookie = getCookieBySessionCookieName(httpExchange);
        if (cookie.isPresent()) {
            this.sessionid = getSessionIdFromCookie(cookie);
            if (loginDAO.isActiveSession(sessionid)) {
                this.codecoolerId = loginDAO.getUserId(sessionid);
                this.codecooler = codecoolerDAO.getCodecoolerById(codecoolerId);
                String path = getPath(httpExchange);
                gotToPath(httpExchange, path);
            }
        }
        goToLogin(httpExchange);
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

    private String getPath(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        return uri.getPath();
    }

    private void gotToPath(HttpExchange httpExchange, String path) throws IOException {
        int index = getIndexFromPath(httpExchange,path);
        if (path.equals("/queststore/codecooler/" + index)) {
            displayProfile(httpExchange);
        } else if (path.equals("/queststore/codecooler/experience/" + index)) {
            displayExperience(httpExchange);
        } else if (path.equals("/queststore/codecooler/store/" + index)) {
            displayStore(httpExchange);
        } else if (path.equals("/queststore/codecooler/wallet/" + index)) {
            displayWallet(httpExchange);
        } else if (path.equals("/queststore/codecooler/logout/" + index)) {
            loginDAO.removeSessionid(sessionid);
            goToLogin(httpExchange);
        } else {
            goToLogin(httpExchange);
        }
    }

    private int getIndexFromPath(HttpExchange httpExchange, String path) throws IOException {
        String[] pathParts = path.split("/");
        String urlEnding = pathParts[pathParts.length - 1];
        return getIdFromURL(httpExchange, urlEnding);
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
        String response = generateResponseProfile();
        sendResponse(httpExchange, response);
    }

    private String generateResponseProfile() {
        int roomid = codecooler.getRoomId();
        Room room = roomsDAO.getRoomById(roomid);
        List<Mentor> mentors = mentorDAO.getMentorsByRoomId(roomid);
        List<Quest> questList = daoQuests.getCodecoolerQuestsWithQuantity(codecooler);
        List<Item> itemList = daoStore.getCodecoolerItemsWithQuantity(codecooler);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/profile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("codecooler", codecooler);
        model.with("room", room);
        model.with("mentors", mentors);
        model.with("questList", questList);
        model.with("itemList", itemList);
        return template.render(model);
    }

    private void displayExperience(HttpExchange httpExchange) throws IOException {
        String response = generateResponseExperience();
        sendResponse(httpExchange, response);
    }

    private String generateResponseExperience() {
        List<Quest> questList = daoQuests.getCodecoolerQuestsWithQuantity(codecooler);
        List<Quest> questsBasic = new ArrayList<>();
        List<Quest> questExtra = new ArrayList<>();
        int quantity = 0;
        int total = 0;
        for(Quest quest : questList) {
            if(quest.getQuestType().equals("basic")) {
                questsBasic.add(quest);
            } else {
                questExtra.add(quest);
            }
            quantity += quest.getQuantity();
            total += quest.getTotal();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/experience.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("codecooler", codecooler);
        model.with("questsBasic", questsBasic);
        model.with("questExtra", questExtra);
        model.with("quantity", quantity);
        model.with("total", total);
        return template.render(model);
    }

    private void displayWallet(HttpExchange httpExchange) throws IOException {
        String response = generateResponseWallet();
        sendResponse(httpExchange, response);
    }

    private String generateResponseWallet() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/wallet.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("codecooler", codecooler);
        return template.render(model);
    }

    private void displayStore(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")) {
            String formData = getFormData(httpExchange);
            List<Integer> ids = parseFormDataStore(formData);
            List<Item> itemsToBuy = new ArrayList<>();
            for(int i : ids) {
                Item item = daoStore.getItemById(i);
                LocalDate locald = LocalDate.now();
                Date date = Date.valueOf(locald);
                item.setDate(date);
                itemsToBuy.add(item);
            }
            for(Item item : itemsToBuy) {
                daoStore.handleBuyingItem(codecooler, item);
            }
        }
        String response = generateResponseStore();
        sendResponse(httpExchange, response);
    }

    private String getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        System.out.println(formData);
        return formData;
    }

    private List<Integer> parseFormDataStore(String formData) throws UnsupportedEncodingException {
        //1=on&2=on
        List<Integer> decodedIds = new ArrayList<>();
        String[] notdecodedIds = formData.split("&");
        for (String id : notdecodedIds) {
            String[] notdecodedParts = id.split("=");
            decodedIds.add(Integer.valueOf(notdecodedParts[0]));
        }
        return decodedIds;
    }

    private String generateResponseStore() {
        List<Item> itemsBasic = daoStore.getBasicItems();
        List<Item> itemsMagic = daoStore.getMagicItems();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("queststore/templates/store.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("codecooler", codecooler);
        model.with("itemsBasic", itemsBasic);
        model.with("itemsMagic", itemsMagic);
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
