package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOQuests;
import model.Quest;
import model.user.Codecooler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestsDaoImpl implements DAOQuests {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement preStatement = null;

    public QuestsDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Quest> getBasicQuests() {

        try {
            openDataBaseConnection();
//            connection.commit();
            return getBasicQuestsSQL("SELECT * FROM quest " +
                    "WHERE quest_type LIKE 'basic';");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }
        return null;
    }

    private void closeDataBaseConnection() {
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private List<Quest> getBasicQuestsSQL(String sqlStatement) throws SQLException {
        preStatement = connection.prepareStatement(sqlStatement);
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
        ResultSet recordsFromDB = preStatement.executeQuery();

        return createBasicQuests(recordsFromDB);
    }

    private List<Quest> createBasicQuests(ResultSet recordsFromDB) throws SQLException {
        List<Quest> basicQuests = new ArrayList<Quest>();
        while (recordsFromDB.next()) {
            int id = recordsFromDB.getInt("id");
            String name = recordsFromDB.getString("name");
            String description = recordsFromDB.getString("description");
            int coins = recordsFromDB.getInt("coins");
            String quest_type = recordsFromDB.getString("quest_type");

            basicQuests.add(new Quest(id, name, description, coins, quest_type));
        }
        return basicQuests;
    }

    public List<Quest> getExtraQuests() {

        try {
            openDataBaseConnection();
//            connection.commit();
            return getExtraQuestsSQL("SELECT * FROM quest " +
                    "WHERE quest_type LIKE 'extra';");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }
        return null;
    }

    private List<Quest> getExtraQuestsSQL(String sqlStatement) throws SQLException {
        preStatement = connection.prepareStatement("SELECT * FROM quest " +
                "WHERE quest_type LIKE 'extra';");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
        ResultSet recordsFromDB = preStatement.executeQuery();

        return createExtraQuests(recordsFromDB);
    }

    private void openDataBaseConnection() throws SQLException {
        connection = connectionPool.takeOut();
        connection.setAutoCommit(false);
    }

    private List<Quest> createExtraQuests(ResultSet recordsFromDB) throws SQLException {
        List<Quest> extraQuests = new ArrayList<Quest>();
        while (recordsFromDB.next()) {
            int id = recordsFromDB.getInt("id");
            String name = recordsFromDB.getString("name");
            String description = recordsFromDB.getString("description");
            int coins = recordsFromDB.getInt("coins");
            String quest_type = recordsFromDB.getString("quest_type");

            extraQuests.add(new Quest(id, name, description, coins, quest_type));
        }
        return extraQuests;
    }

    public void addQuest(Quest quest) {

        try{
            openDataBaseConnection();
            addQuestToDB(quest);
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }

    }

    private void addQuestToDB(Quest quest) throws SQLException {
        preStatement = connection.prepareStatement("INSERT INTO quest (name, description, coins, quest_type) VALUES (?, ?, ?, ?);");

        preStatement.setString(1, quest.getName());
        preStatement.setString(2, quest.getDescription());
        preStatement.setInt(3, quest.getCoins());
        preStatement.setString(4, quest.getQuestType());

        preStatement.executeUpdate();
        System.out.println("Quest " + quest.getName() + " added succesfully ");
    }

    public void updateQuest(Quest quest) {
        try{
            updateQuestInDB(quest);
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }
    }

    private void updateQuestInDB(Quest quest) throws SQLException {
        int id = quest.getId();
        openDataBaseConnection();
        preStatement = connection.prepareStatement("UPDATE quest SET name=?, description=?, coins=?, quest_type=?");
        preStatement.setString(1, quest.getName());
        preStatement.setString(2, quest.getDescription());
        preStatement.setInt(3, quest.getCoins());
        preStatement.setString(4, quest.getQuestType());
        preStatement.executeUpdate();
    }

    public List<Quest> getCodecoolerQuests(Codecooler codecooler) {
        try {
            openDataBaseConnection();
            System.out.println("Codecooler id :" + codecooler.getId());
            return getBasicQuestsSQL(""); // missing SQL query, work in progress...

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }

        return null;
    }

    public void addCompleteQuest(int userId, int questId) {
        try {
            openDataBaseConnection();
            addCompleteQuestToDB(userId,questId);
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }
    }

    private void addCompleteQuestToDB(int userId, int questId) throws SQLException {
        preStatement = connection.prepareStatement("INSERT INTO public.completed_quests(" +
                "            userid, questid, date)" +
                "    VALUES (?, ?, current_timestamp);");

        preStatement.setInt(1, userId);
        preStatement.setInt(2, questId);

        preStatement.executeUpdate();

        System.out.println("Complete quest successfully");
    }
}
