package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private String name;
    private List<Codecooler> codecoolerList;

    public Team(int id, String name) {
        this.name = name;
        this.codecoolerList = new ArrayList<Codecooler>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCodecooler(Codecooler codecooler) {
        codecoolerList.add(codecooler);
    }

//    public Codecooler getCodecoolerById(Codecooler codecooler) {
//        for (Codecooler c : codecoolerList) {
//            if (c.getId() == codecooler.getId()) {
//                return c;
//            }
//        }
//    }

    public int getNumOfCodecoolers() {
        return  codecoolerList.size();
    }
}
