import dao.*;
import model.Quest;
import model.Room;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool(
                "org.postgresql.Driver", "jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

//        DAORooms roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());

        DAOQuests questsDao = new QuestsDaoImpl(pool);
        List<Quest> basicQuests = new ArrayList<Quest>();
        basicQuests = questsDao.getBasicQuests();

        for (Quest bQuest: basicQuests) {
            System.out.println(bQuest.toString());
            System.out.println();
        }
    }
}
