package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOQuests;
import model.Quest;
import model.user.Codecooler;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
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
            connection.commit();
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
            Date date = recordsFromDB.getDate("date");
            basicQuests.add(new Quest(id, name, description, coins, quest_type, date));
        }
        return basicQuests;
    }

    public List<Quest> getExtraQuests() {

        try {
            openDataBaseConnection();
            connection.commit();
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
            Date date = recordsFromDB.getDate("date");
            extraQuests.add(new Quest(id, name, description, coins, quest_type, date));
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
        preStatement = connection.prepareStatement("INSERT INTO quest (name, description, coins, quest_type, date) VALUES (?, ?, ?, ?, ?);");

        preStatement.setString(1, quest.getName());
        preStatement.setString(2, quest.getDescription());
        preStatement.setInt(3, quest.getCoins());
        preStatement.setString(4, quest.getQuestType());
        preStatement.setDate(5, quest.getDate());

        preStatement.executeUpdate();
        System.out.println("Quest " + quest.getName() + " added succesfully ");
    }

    public void updateQuest(Quest quest) {
        try {
            openDataBaseConnection();
            updateQuestInDataBase(quest);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDataBaseConnection();
        }
    }

    private void updateQuestInDataBase(Quest quest) throws SQLException {
        preStatement = connection.prepareStatement("UPDATE quest SET name = ?, description = ?, coins=?, quest_type=?, date=? WHERE id=?");
        preStatement.setString(1, quest.getName());
        preStatement.setString(2, quest.getDescription());
        preStatement.setInt(3, quest.getCoins());
        preStatement.setString(4, quest.getQuestType());
        preStatement.setInt(5, quest.getId());
        preStatement.setDate(5, quest.getDate());
        preStatement.executeUpdate();
        connection.commit();
    }

    public List<Quest> getCodecoolerQuests(Codecooler codecooler) {
        try {
            openDatabaseConnection();
            return getListOfQuestsFromDatabase("SELECT quest.*, completed_quests.date FROM quest LEFT JOIN completed_quests ON quest.id = completed_quests.questid WHERE userid = "+codecooler.getId()+";");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private void openDatabaseConnection() {
        connection = connectionPool.takeOut();
    }

    private void closeDatabaseConnection() {
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private List<Quest> getListOfQuestsFromDatabase(String sqlStatement) throws SQLException {
        preStatement = connection.prepareStatement(sqlStatement);
        ResultSet recordFromDatabase = preStatement.executeQuery();
        return createListOfQuests(recordFromDatabase);
    }

    private List<Quest> createListOfQuests(ResultSet recordFromDatabase) throws SQLException {
        List quests = new ArrayList<Quest>();
        while (recordFromDatabase.next()) {
            int id = recordFromDatabase.getInt("id");
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("coins");
            String type = recordFromDatabase.getString("quest_type");
            Date date = recordFromDatabase.getDate("date");
            Quest quest = new Quest(id, name, description, price, type, date);
            quests.add(quest);
        }
        return quests;
    }

    private void addCompletedQuestToDatabase(Codecooler codecooler, Quest quest) throws SQLException {
        preStatement = connection.prepareStatement("INSERT INTO completed_quests (userid, itemid, date) VALUES (?, ?, ?)");
        preStatement.setInt(1, codecooler.getId());
        preStatement.setInt(2, quest.getId());
        preStatement.setDate(3, quest.getDate());
        preStatement.executeUpdate();
    }

    public List<Quest> getCodecoolerQuestsWithQuantity(Codecooler codecooler) {
        try {
            openDatabaseConnection();
            return getListOfQuestsFromDatabaseWithQuantity("SELECT quest.*, count(completed_quests.questid) as quantity, count(completed_quests.questid)*coins as total FROM quest LEFT JOIN completed_quests ON quest.id = completed_quests.questid WHERE userid ="+codecooler.getId()+" group by quest.id");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private List<Quest> getListOfQuestsFromDatabaseWithQuantity(String sqlStatement) throws SQLException {
        preStatement = connection.prepareStatement(sqlStatement);
        ResultSet recordFromDatabase = preStatement.executeQuery();
        return createListOfQuestsWithQuantity(recordFromDatabase);
    }

    private List<Quest> createListOfQuestsWithQuantity(ResultSet recordFromDatabase) throws SQLException {
        List quests = new ArrayList<Quest>();
        while (recordFromDatabase.next()) {
            int id = recordFromDatabase.getInt("id");
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("coins");
            String type = recordFromDatabase.getString("quest_type");
            Quest quest = new Quest(id, name, description, price, type);
            quest.setQuantity(recordFromDatabase.getInt("quantity"));
            quest.setTotal(recordFromDatabase.getInt("total"));
            quests.add(quest);
        }
        return quests;
    }

}