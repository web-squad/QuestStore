import java.util.List;

public interface MentorDAO {

    List<Mentor> getListOfMentors();
    Mentor getMentor(String login);
    void addNewMentor();
}