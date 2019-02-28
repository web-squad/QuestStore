package dao.interfaces;

import model.user.Codecooler;
import model.user.CreepyGuy;
import model.user.Mentor;
import model.user.User;

public interface UserDAO {
    String getUserType(String login, String password);

    CreepyGuy getCreepyGuyByLoginAndPassword(String login, String password);

    Codecooler getCodecoolerByLoginAndPassword(String login, String password);

    Mentor getMentorByLoginAndPassword(String login, String password);

    Codecooler getCodecoolerById(int id);

    Mentor getMentorById(int id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
