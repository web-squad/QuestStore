package dao.interfaces;
import model.user.Mentor;

import java.util.List;

public interface MentorDAO {

    List<Mentor> getListOfMentors();
    Mentor getMentorByLogin(String login);
    void addNewMentor(Mentor mentor);
    Mentor getMentorByRoomId(int roomid);
}
