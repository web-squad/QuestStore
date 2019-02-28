package model;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int coins;
    private String questType;

    public Quest(int id, String name, String description, int coins, String questType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coins = coins;
        this.questType = questType;
    }

    public int getId() {
        return id;
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

    public String getQuestType() {
        return questType;
    }

    public void setEarnings(int coins) {
        this.coins = coins;
    }

    public String toString() {
        return "id: "+ getId()+ "\nName: " + getName() + "\nDescription: " + getDescription() + "\nCoins: " + getCoins() + "\nQuest type: " + getQuestType();
    }
}
