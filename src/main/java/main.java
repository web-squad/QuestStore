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

//        MentorDAO mentorDao = new MentorDAOImplementation(pool);
//        Mentor mentor = new Mentor(2, "kamilus", "5555", "mentor", "Kamil", "Anno", "kamil@gmail.com");
//        mentorDao.addNewMentor(mentor);

        MentorDAO mentorDao = new MentorDAOImplementation(pool);
        mentorDao.updateMentorData("name", "Adam", "kondzio");
    }
}
