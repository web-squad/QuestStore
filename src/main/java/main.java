import dao.*;
import model.Mentor;
import model.Room;

public class main {
    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool(
                "org.postgresql.Driver", "jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

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
    }
}
