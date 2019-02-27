package dao;

import dao.connectionPool.JDBCConnectionPool;
import model.user.Codecooler;
import model.user.CreepyGuy;
import model.user.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public UserDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void getUserIdAndType(String login, String password) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String userType = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                createUserByType(id, login, password, userType, name, surname);
            } else {
                System.out.println("User doesn't exist!");
            }
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            try {
                connectionPool.takeIn(connection);
            } catch (Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            }
        }
    }

    private void createUserByType(int id, String login, String password, String userType, String name, String surname) {
        if (userType.equals("creepyGuy")) {
            createCreepyGuy(id, login, password, userType, name, surname);
        } else if (userType.equals("codecooler")) {
            createCodecooler(id, login, password, userType, name, surname);
        }
        else if (userType.equals("mentor")) {
            createMentor(id, login, password, userType, name, surname);
        }
    }

    private CreepyGuy createCreepyGuy(int id, String login, String password, String userType, String name, String surname) {
        return new CreepyGuy(id, login, password, userType, name, surname);
    }

    private Codecooler createCodecooler(int id, String login, String password, String userType, String name, String surname) {
        return new Codecooler(id, login, password, userType, name, surname);
    }

    private Mentor createMentor(int id, String login, String password, String userType, String name, String surname) {
        return new Mentor(id, login, password, userType, name, surname);
    }
}
