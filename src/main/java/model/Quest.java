package model;

public class Quest {

    private String name;
    private String description;
    private int earnings;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public String toString() {
        return "Name: " + getName() + "\nDescription: " + getDescription() + "\nEarnings: " + getEarnings();
    }
}
