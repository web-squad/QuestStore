package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.LoginDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public LoginDAOImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean isActiveSession(String sessionid) {
        System.out.println(sessionid);
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM session WHERE sessionid = ?");
            pst.setString(1, sessionid);
            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                return true;
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return false;
    }

    public int getUserId(String sessionid) {
        int id = 0;
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT userid FROM session WHERE sessionid = ?");
            pst.setString(1, sessionid);
            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                id = recordFromDatabase.getInt("userid");
                return id;
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return id;
    }

    public void activateSessionId(String sessionid, int id) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("INSERT INTO session (sessionid, userid) VALUES (?, ?)");
            pst.setString(1, sessionid);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("session saved: " + sessionid);
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public void removeSessionid(String sessionid) {
        System.out.println(sessionid);
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("DELETE FROM session WHERE sessionid = ?");
            pst.setString(1, sessionid);
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
