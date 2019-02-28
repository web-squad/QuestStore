package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.TeamsDAO;
import model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamsDaoImpl implements TeamsDAO {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public TeamsDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Team getTeamById(int id) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement("SELECT * FROM team WHERE id = ?");
            pst.setInt(1, id);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {

                String name = recordFromDatabase.getString("name");
                return new Team(id, name);
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

    public Team getTeamByName(String name) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM team WHERE name = ?");
            pst.setString(1, name);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                return new Team(id, name);
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

    public void addTeam(Team team) {
        try {
            connection = connectionPool.takeOut();
            int id = team.getId();
            String name = team.getName();
            pst = connection.prepareStatement("INSERT INTO team VALUES(?, ?)");
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

    public void updateTeam(Team team) {
        try {
            connection = connectionPool.takeOut();
            int id = team.getId();
            String name = team.getName();
            pst = connection.prepareStatement("UPDATE team SET id = ?, name = ?");
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
