package dao;
import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOStore;
import model.Item;
import model.user.Codecooler;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class StoreDaoImpl implements DAOStore {
    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public StoreDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public void removeItem(Item item) {
        try {
            openDatabaseConnection();
            deleteItemFromDatabase(item);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }

    private void deleteItemFromDatabase(Item item) throws SQLException {
        pst = connection.prepareStatement("DELETE FROM item WHERE id = ?;");
        pst.setInt(1, item.getId());
        pst.executeUpdate();
    }

    private void openDatabaseConnection() {
        connection = connectionPool.takeOut();
    }


    public List<Item> getItems() {
        try {
            openDatabaseConnection();
            return getListOfItemsFromDatabase("SELECT * FROM item;");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private List<Item> getListOfItemsFromDatabase(String sqlStatement) throws SQLException {
        pst = connection.prepareStatement(sqlStatement);
        ResultSet recordFromDatabase = pst.executeQuery();
        return createListOfItems(recordFromDatabase);
    }

    public List<Item> getBasicItems() {
        try {
            openDatabaseConnection();
            return getListOfItemsFromDatabase("SELECT * FROM item WHERE itemtype LIKE 'basic';");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private void closeDatabaseConnection() {
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private List<Item> createListOfItems(ResultSet recordFromDatabase) throws SQLException {
        List items = new ArrayList<Item>();
        while (recordFromDatabase.next()) {
            int id = recordFromDatabase.getInt("id");
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("price");
            String itemType = recordFromDatabase.getString("itemtype");
            Item item = new Item(id, name, description, price, itemType);
            items.add(item);
        }
        return items;
    }


    public List<Item> getMagicItems() {
        try {
            openDatabaseConnection();
            return getListOfItemsFromDatabase("SELECT * FROM item WHERE itemtype LIKE 'extra';");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }


    public Item getItemById(int id) {
        try {
            openDatabaseConnection();
            return getItemFromDatabase(id);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private Item getItemFromDatabase(int id) throws SQLException {
        pst = connection.prepareStatement("SELECT * FROM item WHERE id = ?;");
        pst.setInt(1, id);
        ResultSet recordFromDatabase = pst.executeQuery();
        if (recordFromDatabase.next()) {
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("price");
            String itemtype = recordFromDatabase.getString("itemtype");
            return new Item(id, name, description, price, itemtype);
        }
        return null;
    }

//    private Item getItemByName(String name) throws SQLException {
//        pst = connection.prepareStatement("SELECT * FROM item WHERE id = ?;");
//        pst.setInt(1, id);
//        ResultSet recordFromDatabase = pst.executeQuery();
//        if (recordFromDatabase.next()) {
//            String name = recordFromDatabase.getString("name");
//            String description = recordFromDatabase.getString("description");
//            int price = recordFromDatabase.getInt("price");
//            String itemtype = recordFromDatabase.getString("itemtype");
//            return new Item(id, name, description, price, itemtype);
//        }
//        return null;
//    }


    public void updateItem(Item item) {
        try {
            updateItemInDatabase(item);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }

    private void updateItemInDatabase(Item item) throws SQLException {
        int id = item.getId();
        openDatabaseConnection();
        pst = connection.prepareStatement("UPDATE item SET name=?, description=?, price=?, itemtype=? WHERE id='"+id+"'");
        pst.setString(1, item.getName());
        pst.setString(2, item.getDescription());
        pst.setInt(3, item.getPrice());
        pst.setString(4, item.getItemType());
        pst.executeUpdate();
    }


    public void addItem(Item item) {
        try {
            openDatabaseConnection();
            addItemToDatabase(item);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }

    private void addItemToDatabase(Item item) throws SQLException {
        pst = connection.prepareStatement("INSERT INTO item (name, description, price, itemtype) VALUES (?, ?, ?, ?);");
        pst.setString(1, item.getName());
        pst.setString(2, item.getDescription());
        pst.setInt(3, item.getPrice());
        pst.setString(4, item.getItemType());
        pst.executeUpdate();
    }


    public List<Item> getCodecoolerItems(Codecooler codecooler) {
        try {
            openDatabaseConnection();
            return getListOfItemsFromDatabase("SELECT item.* FROM item LEFT JOIN bought_items ON item.id = bought_items.itemid WHERE userid = "+codecooler.getId()+";");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }


    public void handleBuyingItem(Codecooler codecooler, Item item) {
        try {
            openDatabaseConnection();
            addBought_ItemToDatabase(codecooler, item);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }

    private void addBought_ItemToDatabase(Codecooler codecooler, Item item) throws SQLException {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("INSERT INTO bought_items (userid, itemid, date) VALUES (?, ?, ?)");
            pst.setInt(1, codecooler.getId());
            pst.setInt(2, item.getId());
            pst.setDate(3, item.getDate());
            pst.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public List<Item> getCodecoolerItemsWithQuantity(Codecooler codecooler) {
        try {
            openDatabaseConnection();
            return getListOfItemsFromDatabaseWithQuantity("SELECT item.*, count(bought_items.itemid) as quantity FROM item LEFT JOIN bought_items ON item.id = bought_items.itemid WHERE userid ="+codecooler.getId()+" group by item.id");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
        return null;
    }

    private List<Item> getListOfItemsFromDatabaseWithQuantity(String sqlStatement) throws SQLException {
        pst = connection.prepareStatement(sqlStatement);
        ResultSet recordFromDatabase = pst.executeQuery();
        return createListOfItemsWithQuantity(recordFromDatabase);
    }

    private List<Item> createListOfItemsWithQuantity(ResultSet recordFromDatabase) throws SQLException {
        List items = new ArrayList<Item>();
        while (recordFromDatabase.next()) {
            int id = recordFromDatabase.getInt("id");
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("price");
            String itemType = recordFromDatabase.getString("itemtype");
            Item item = new Item(id, name, description, price, itemType);
            item.setQuantity(recordFromDatabase.getInt("quantity"));
            items.add(item);
        }
        return items;
    }

}