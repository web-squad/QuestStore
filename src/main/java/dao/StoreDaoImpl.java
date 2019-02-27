package dao;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("DELETE FROM item WHERE name LIKE ? and description LIKE ? AND itemtype LIKE ?;");
            pst.setString(1, item.getName());
            pst.setString(2, item.getDescription());
            pst.setString(3, item.getItemType());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }


    public List<Item> getItems() {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM item ;");
            ResultSet recordFromDatabase = pst.executeQuery();
            List items = createListOfItems(recordFromDatabase);
            return items;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
            return null;
        }
    }

    public List<Item> getBasicItems() {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM item WHERE itemtype LIKE 'basic';");
            ResultSet recordFromDatabase = pst.executeQuery();
            List items = createListOfItems(recordFromDatabase);
            return items;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
            return null;
        }
    }

    private void closeDatabaseConnection() {
        try {
            connectionPool.takeIn(connection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private List createListOfItems(ResultSet recordFromDatabase) throws SQLException {
        List items = new ArrayList<Item>();
        while (recordFromDatabase.next()) {
            String name = recordFromDatabase.getString("name");
            String description = recordFromDatabase.getString("description");
            int price = recordFromDatabase.getInt("price");
            String itemType = recordFromDatabase.getString("itemtype");
            System.out.println(name);
            Item item = new Item(name, description, price, itemType);
            items.add(item);
        }
        return items;
    }

    public List<Item> getMagicItems() {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM item WHERE itemtype LIKE 'magic';");
            ResultSet recordFromDatabase = pst.executeQuery();
            List items = createListOfItems(recordFromDatabase);
            return items;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
            return null;
        }
    }


    public Item getItem(String name) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM item WHERE name LIKE ?;");
            pst.setString(1, name);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                String description = recordFromDatabase.getString("description");
                int price = recordFromDatabase.getInt("price");
                String itemtype = recordFromDatabase.getString("itemtype");
                Item item = new Item(name, description, price, itemtype);
                return item;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
            return null;
        }
    }


    public void updateItem(String name) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("UPDATE item SET name = '"+name+"', description = 'testdesc' WHERE name LIKE 'item3'");
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
            throw new UnsupportedOperationException("not implemented yet, ");
        }
    }



    public void addItem(Item item) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("INSERT INTO item (name, description, price, itemtype) VALUES (?, ?, ?, ?);");
            pst.setString(1, item.getName());
            pst.setString(2, item.getDescription());
            pst.setInt(3, item.getPrice());
            pst.setString(4, item.getItemType());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeDatabaseConnection();
        }
    }

    public List<Item> getCodecoolerItems() {
        return null;
    }


    public void addBasicItem() {
        throw new UnsupportedOperationException("this method is not implemented, and probably will never be :-) ");
    }

    public void addMagicItem() {
        throw new UnsupportedOperationException("this method is not implemented, and probably will never be :-) ");
    }
}
