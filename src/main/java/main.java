import dao.RoomsDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import model.Room;

public class main {
    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        RoomsDAO roomsDao = new RoomsDaoImpl(pool);
        Room cl = roomsDao.getRoomById(1);
        System.out.println(cl.getName());

        Room cl2 = roomsDao.getRoomByName("java");
        System.out.println(cl2.getId());
    }
}
