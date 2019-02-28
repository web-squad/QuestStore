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
            sqlStatement = connection.prepareStatement("SELECT * FROM quest \n" +
                    "WHERE quest_type LIKE 'basic';");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
            ResultSet recordsFromDB = sqlStatement.executeQuery();

            while (recordsFromDB.next()) {
                int id = recordsFromDB.getInt("id");
                String name = recordsFromDB.getString("name");
                String description = recordsFromDB.getString("description");
                int coins = recordsFromDB.getInt("coins");
                String quest_type = recordsFromDB.getString("quest_type");

                basicQuests.add(new Quest(id, name, description, coins, quest_type));
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
        List<Quest> extraQuests = new ArrayList<Quest>();

        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM quest \n" +
                    "WHERE quest_type LIKE 'extra';");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
            ResultSet recordsFromDB = sqlStatement.executeQuery();

            while (recordsFromDB.next()) {
                int id = recordsFromDB.getInt("id");
                String name = recordsFromDB.getString("name");
                String description = recordsFromDB.getString("description");
                int coins = recordsFromDB.getInt("coins");
                String quest_type = recordsFromDB.getString("quest_type");

                extraQuests.add(new Quest(id, name, description, coins, quest_type));
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

        return extraQuests;
    }

    public void addQuest(Quest quest) {

        String name = quest.getName();
        String description = quest.getDescription();
        int coins = quest.getCoins();
        String quest_type = quest.getQuestType();

        try{
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("INSERT INTO quest (name, description, coins, quest_type) VALUES (?, ?, ?, ?);");

            sqlStatement.setString(1, name);
            sqlStatement.setString(2, description);
            sqlStatement.setInt(3, coins);
            sqlStatement.setString(4, quest_type);


            sqlStatement.executeUpdate();
            System.out.println("Quest " + name + "added succesfully ");
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void addBasicQuest(Quest quest) {

    }

    public void addExtraQuest(Quest quest) {

    }

    public void updateQuest(Quest quest) {

    }
}
