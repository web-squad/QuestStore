package dao.interfaces;

import model.Item;

import java.util.List;

public interface DAOStore {
    void removeItem(Item item);
    List<Item> getItems();
    List getBasicItems();
    List<Item> getMagicItems();
    Item getItemById(int id);
    void updateItem(Item item);
    void addItem(Item item);
    void addBasicItem();
    void addMagicItem();
    List<Item> getCodecoolerItems();
}
