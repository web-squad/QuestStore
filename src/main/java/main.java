import com.sun.net.httpserver.HttpServer;
import controller.CodecoolerController;
import controller.Controller;
import controller.MentorController;
import dao.connectionPool.JDBCConnectionPool;


import java.net.InetSocketAddress;


public class main {
    public static void main(String[] args) throws Exception {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        HttpServer server = HttpServer.create(new InetSocketAddress(7000), 0);

        // set routes
        server.createContext("/queststore/login", new Controller(pool));
        server.createContext("/queststore/codecooler", new CodecoolerController(pool));
        server.createContext("/queststore/mentor", new MentorController(pool));
        server.createContext("/queststore/addNewStudent", new MentorController(pool));
        server.createContext("/static", new Static());
        //server.createContext("/static/img", new Static());



        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}