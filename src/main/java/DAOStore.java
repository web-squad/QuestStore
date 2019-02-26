package main.java;

public interface DAOStore {
    void removeItem();
    String getItems();
    String getBasicItems();
    String getMagicItems();
    Item getItem();
    void updateItem();
    void addItem();
    void addBasicItem();
    void addMagicItem();
}
