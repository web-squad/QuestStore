package dao.interfaces;

import model.user.Codecooler;

import java.util.List;

public interface CodecoolerDAO {
    List<Codecooler> getAllCodecoolers();

    List<Codecooler> getCodecoolersByTeamId(int teamid);

    List<Codecooler> getCodecoolersByRoomId(int roomid);

    Codecooler getCodecoolerById(int id);

    int getCodecoolerIdByLoginPasswordNameSurname(Codecooler codecooler);

    void addCodecooler(Codecooler codecooler);

    void addCodecoolerToUsersTable(Codecooler codecooler);

    void updateCodecooler(Codecooler codecooler);

    void deleteCodecooler(Codecooler codecooler);
}
