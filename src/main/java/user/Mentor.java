package user;

public class Mentor extends User{
    private Class mentorClass;
    private String email;

    public Mentor(int id, String login, String password, String userType, String name, String surname, String email){
        super(id, login, password, userType, name,surname);
        this.email = email;
    }

    private String getEmail(){
        return email;
    }
}
