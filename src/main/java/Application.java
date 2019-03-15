import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.CodecoolerController;
import controller.Controller;
import controller.LoginController;
import controller.MentorController;
import dao.connectionPool.JDBCConnectionPool;


import java.net.InetSocketAddress;


public class Application {
    public static void main(String[] args) throws Exception {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        HttpServer server = HttpServer.create(new InetSocketAddress(7000), 0);

        // set routes
        server.createContext("/queststore", new Controller());
        server.createContext("/queststore/login", new LoginController(pool));
        server.createContext("/queststore/codecooler", new CodecoolerController(pool));
        server.createContext("/queststore/mentor", new MentorController(pool));
        server.createContext("/queststore/admin", new AdminController(pool));
        server.createContext("/queststore/admin/addNewMentor", new AdminController(pool));
        server.createContext("/queststore/addNewStudent", new MentorController(pool));
        server.createContext("/queststore/editStudent", new MentorController(pool));
        server.createContext("/static", new Static());

        // start listening
        server.start();
    }
}