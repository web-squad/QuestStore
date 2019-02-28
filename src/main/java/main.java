import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import dao.RoomsDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.*;
import dao.interfaces.DAOQuests;
import dao.interfaces.RoomsDAO;
import model.Quest;
import model.Room;
import model.user.Codecooler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");


    }
}
