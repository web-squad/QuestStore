package dao;

import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoomsDaoImpl implements DAORooms {

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

            pst = connection.prepareStatement("SELECT * FROM Room WHERE id = ?");
            pst.setInt(1, id);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                String name = recordFromDatabase.getString("name");
                return new Room(name, id);
            }
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            connectionPool.dead(connection);
        }
        return null;
    }

    public Room getRoomByName(String name) {
        try {
            connection = connectionPool.create();
            pst = connection.prepareStatement("SELECT * FROM Room WHERE name = ?");
            pst.setString(1, name);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                return new Room(name, id);
            }
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }
}
