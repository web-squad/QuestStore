package model;

public class Item {
    String name;
    String description;
    int price;
    String itemType;

    public Item(String name, String description, int price, String itemType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }

    public String getItemType() {
        return this.itemType;
    }

    public void editPrice() {

    }

    public String itemToString() {
        return this.description;
    }
}