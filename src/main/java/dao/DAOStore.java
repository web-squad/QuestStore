package dao;

import model.Item;

import java.util.List;

public interface DAOStore {
    void removeItem(Item item);
    List<Item> getItems();
    List<Item> getBasicItems();
    List<Item> getMagicItems();
    Item getItem(String name);
    void updateItem(String name);
    void addItem(Item item);
    void addBasicItem();
    void addMagicItem();
    List<Item> getCodecoolerItems();
}
