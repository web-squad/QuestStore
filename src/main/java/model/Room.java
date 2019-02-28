package model;

import model.user.Codecooler;
import model.user.Mentor;

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
        this.id = id;
        this.name = name;
        codecoolerList = new ArrayList<Codecooler>();
        teams = new HashMap<Integer,Team>();
    }

    public Room(String name) {
        this.name = name;
        codecoolerList = new ArrayList<Codecooler>();
        teams = new HashMap<Integer,Team>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeams(Map<Integer, Team> teams) {
        this.teams = teams;
    }

    public void setCodecoolerList(List<Codecooler> codecoolerList) {
        this.codecoolerList = codecoolerList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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
