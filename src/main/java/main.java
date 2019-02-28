import dao.*;
import dao.RoomsDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOQuests;
import dao.interfaces.*;
import model.Mentor;
import model.Quest;
import model.Room;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        RoomsDAO roomsDao = new RoomsDaoImpl(pool);
        Room cl = roomsDao.getRoomById(1);
        System.out.println(cl.getName());

        Room cl2 = roomsDao.getRoomByName("java");
        System.out.println(cl2.getId());

//        DAORooms roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());

//        MentorDAO mentorDao = new MentorDAOImplementation(pool);
//        Mentor mentor = mentorDao.getMentorByLogin("kondzio");
//        System.out.println(mentor);

        MentorDAO mentorDao = new MentorDAOImplementation(pool);
        Mentor mentor = new Mentor(2, "aegaaeg", "555345", "mentor", "Ada", "Kol", "qwfqf@gmail.com");
        mentorDao.addNewMentor(mentor);

//        MentorDAO mentorDao = new MentorDAOImplementation(pool);
//        mentorDao.updateMentorData("login", "kamilus", "kamalanitus");
//        DAOQuests questsDao = new QuestsDaoImpl(pool);
//        List<Quest> basicQuests = new ArrayList<Quest>();
//        basicQuests = questsDao.getBasicQuests();


//        MentorDAO mentorDao = new MentorDAOImplementation(pool);
//        Mentor mentor = mentorDao.getMentorByLogin("kondzio");
//        System.out.println(mentor);

//        MentorDAO mentorDao = new MentorDAOImplementation(pool);
//        Mentor mentor = new Mentor(2, "kamilus", "5555", "mentor", "Kamil", "Anno", "kamil@gmail.com");
//        mentorDao.addNewMentor(mentor);

//         MentorDAO mentorDao = new MentorDAOImplementation(pool);
//         mentorDao.updateMentorData("name", "Adam", "kondzio");

        DAOQuests questsDao = new QuestsDaoImpl(pool);
        List<Quest> basicQuests = new ArrayList<Quest>();
        basicQuests = questsDao.getBasicQuests();

        for (Quest bQuest: basicQuests) {
            System.out.println(bQuest.toString());
            System.out.println();
        }
    }
}
