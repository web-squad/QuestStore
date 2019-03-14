package dao.interfaces;
import model.user.Mentor;

import java.util.List;

public interface MentorDAO {

    List<Mentor> getListOfMentors();
//    model.Mentor getMentorByLogin(String login);
    void addNewMentor(Mentor mentor);
    void updateMentorData (Mentor mentor);

}
