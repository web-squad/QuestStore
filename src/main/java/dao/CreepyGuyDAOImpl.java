package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOCreepyGuy;
import model.user.CreepyGuy;

import java.sql.*;

public class CreepyGuyDAOImpl implements DAOCreepyGuy {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public CreepyGuyDAOImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public CreepyGuy getCreepyGuy(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            pst.setString(1, login);


            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String loginFromDB = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String userType = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new CreepyGuy(id, loginFromDB, password, userType, name, surname);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

}

