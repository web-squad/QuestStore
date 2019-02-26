package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class {
    String name;
    Mentor mentor;
    List<Codecooler> codecoolerList;
    Map<String,Team> teams;

    public Class() {
        codecoolerList = new ArrayList<Codecooler>();
        teams = new HashMap<String, Team>();
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

    public Codecooler getCodecoolerById(int id) {
        for(Codecooler codecooler : codecoolerList) {
            if(codecooler.getId());
        }
    }

    public Map<String, Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.put(Team.getName(), team);
    }

    public Team getTeamByName(String name) {
        teams.get(name);
    }
}
