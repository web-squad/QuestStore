package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;
    private int id;
    private Mentor mentor;
    private List<Codecooler> codecoolerList;
    private Map<Integer,Team> teams;

    public Room(String name, int id) {
        this.name = name;
        this.id = id;
        codecoolerList = new ArrayList<Codecooler>();
        teams = new HashMap<Integer,Team>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public List<Codecooler> getCodecoolerList() {
        return codecoolerList;
    }

    public void addCodecooler(Codecooler codecooler) {
        codecoolerList.add(codecooler);
    }

//    public Codecooler getCodecoolerById(int id) {
//        for(Codecooler codecooler : codecoolerList) {
//            if(codecooler.getId() == id) {
//                return codecooler;
//            }
//        }
//    }

    public Map<Integer, Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.put(team.getId(), team);
    }

    public Team getTeamById(int id) {
        return teams.get(id);
    }
}
