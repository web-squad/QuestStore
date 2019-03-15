package model;

import java.sql.Date;

public class Item {
    private int id;
    private String name;
    private String description;
    private int price;
    private String itemType;
    private Date date;
    private int quantity;

    public Item(int id, String name, String description, int price, String itemType, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
        this.date = date;
    }

    public Item(int id, String name, String description, int price, String itemType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
    }

    public int getId() {
        return this.id;
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

    public String getDateString() {
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }


}