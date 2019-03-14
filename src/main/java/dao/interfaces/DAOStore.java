package dao.interfaces;

import model.Item;
import model.user.Codecooler;

import java.util.List;

public interface DAOStore {
    void removeItem(Item item);
    List<Item> getItems();
    List getBasicItems();
    List<Item> getMagicItems();
    Item getItemById(int id);
    void updateItem(Item item);
    void addItem(Item item);
    List<Item> getCodecoolerItems(Codecooler codecooler);
    void handleBuyingItem(Codecooler codecooler, Item item);
    List<Item> getCodecoolerItemsWithQuantity(Codecooler codecooler);
}
