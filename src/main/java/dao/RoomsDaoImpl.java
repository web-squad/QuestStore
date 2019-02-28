package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.RoomsDAO;
import model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsDaoImpl implements RoomsDAO {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public RoomsDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Room getRoomById(int id) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement("SELECT * FROM room WHERE id = ?");
            pst.setInt(1, id);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {

                String name = recordFromDatabase.getString("name");
                return new Room(name, id);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public Room getRoomByName(String name) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM room WHERE name = ?");
            pst.setString(1, name);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                return new Room(name, id);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public void addRoom(Room room) {
        try {
            connection = connectionPool.takeOut();
            int id = room.getId();
            String name = room.getName();
            pst = connection.prepareStatement("INSERT INTO room VALUES(?, ?)");
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.executeUpdate();
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public void updateRoom(Room room) {
        try {
            connection = connectionPool.takeOut();
            int id = room.getId();
            String name = room.getName();
            pst = connection.prepareStatement("UPDATE room SET id = ?, name = ?");
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.executeUpdate();
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }
}
