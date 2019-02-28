package dao.interfaces;

import model.Team;

public interface TeamsDAO {
    Team getTeamById(int id);

    Team getTeamByName(String name);

    void addTeam(Team team);

    void updateTeam(Team team);
}
