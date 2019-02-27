package model;

public class Codecooler {

    private int id;
    private String login;
    private String password;
    private String userType;
    private String name;
    private String surname;
    //private List<Item> itemList;
    private int earnings;

    public Codecooler(int id, String login, String password, String userType, String name, String surname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.earnings = 0;
    }

    //
//    public List<Item> getItems() {
//        return itemsList;
//    }

//    public void addItem(Item item) {
//        itemsList.add(item);
//    }

    public void addEarnings(int cc) {
        earnings += cc;
    }

    public int getEarnings() {
        return earnings;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }
}
