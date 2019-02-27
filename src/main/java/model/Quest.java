package model;

public class Quest {

    private String name;
    private String description;
    private int coins;

    public Quest(String name, String description, int coins) {
        this.name = name;
        this.description = description;
        this.coins = coins;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCoins() {
        return coins;
    }

    public void setEarnings(int coins) {
        this.coins = coins;
    }

    public String toString() {
        return "Name: " + getName() + "\nDescription: " + getDescription() + "\nCoins: " + getCoins();
    }
}
