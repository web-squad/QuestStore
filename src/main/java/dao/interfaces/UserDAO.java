package dao.interfaces;

import model.user.Codecooler;
import model.user.Mentor;

public interface UserDAO {
    String getUserType(String login, String password);

    Codecooler getCodecoolerByUserId(int userId);

    Mentor getMentorByUserId(int userId);

    void addCodecooler(Codecooler codecooler);

    void setId(Codecooler codecooler);

    void updateCodecooler(Codecooler codecooler);

    void deleteCodecooler(Codecooler codecooler);
}
