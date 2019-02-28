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

public class main {
    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

//        RoomsDAO roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());
//
//        Room cl2 = roomsDao.getRoomByName("java");
//        System.out.println(cl2.getId());

//        DAORooms roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());

//        DAOQuests questsDao = new QuestsDaoImpl(pool);
//        List<Quest> basicQuests = new ArrayList<Quest>();
//        basicQuests = questsDao.getBasicQuests();
//
//        for (Quest bQuest: basicQuests) {
//            System.out.println(bQuest.toString());
//            System.out.println();
//        }
        UserDaoImpl userDao = new UserDaoImpl(pool);
        Codecooler codecooler = new Codecooler("user20", "123", "codecooler", "Gi", "Kowalska");
        userDao.addUser(codecooler);
//        Codecooler codecooler = userDao.getCodecoolerByUserId(2);
//        System.out.println(codecooler.getName());
    }
}
