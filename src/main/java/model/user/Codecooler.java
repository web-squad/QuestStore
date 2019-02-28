package model.user;

import model.Item;

import java.util.List;

public class Codecooler extends User {

    private int id;
    private List<Item> itemsList;
    private int earnings;
    private int roomId;
    private int teamId;
    private int codecoolerId;

    public Codecooler(int id, String login, String password, String userType, String name, String surname) {
        super(id, login, password, userType, name, surname);
        this.id = id;
        this.earnings = 0;
        this.roomId = 0;
        this.teamId = 0;
    }

    public Codecooler(String login, String password, String userType, String name, String surname) {
        super(login, password, userType, name, surname);
        this.earnings = 0;
        this.roomId = 0;
        this.teamId = 0;
    }

    public void addItem(Item item) {
        itemsList.add(item);
    }

    public void addEarnings(int cc) {
        earnings += cc;
    }

    public List<Item> getItems() {
        return itemsList;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public int getEarnings() {
        return earnings;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getCodecoolerId() {
        return codecoolerId;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setCodecoolerId(int codecoolerId) {
        this.codecoolerId = codecoolerId;
    }

//    public void save() {
//        userdao.addCodecooler(this)
//        userdao.setId(this)
//        codecolerdao.addCodecooler(this)
//    }

    public void update() {

    }

    public void delete() {

    }
}
