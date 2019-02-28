package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOQuests;
import model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestsDaoImpl implements DAOQuests {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement sqlStatement = null;

    public QuestsDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Quest> getBasicQuests() {
        List<Quest> basicQuests = new ArrayList<Quest>();

        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM quest;");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
            ResultSet recordsFromDB = sqlStatement.executeQuery();

            while (recordsFromDB.next()) {
//                int id = recordsFromDB.getInt("id");
                String name = recordsFromDB.getString("name");
                String description = recordsFromDB.getString("description");
                int coins = recordsFromDB.getInt("coins");

                basicQuests.add(new Quest(name, description, coins));
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return basicQuests;
    }

    public List<Quest> getExtraQuests() {
        return null;
    }

    public void addQuest() {

    }

    public void addBasicQuest() {

    }

    public void addExtraQuest() {

    }

    public void updateQuest() {

    }
}
