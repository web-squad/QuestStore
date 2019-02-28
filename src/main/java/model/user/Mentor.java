package model.user;

import model.Room;

public class Mentor extends User{
    private Room mentorClass;
    private String email;

    public Mentor(int id, String login, String password, String userType, String name, String surname, String email){
        super(id, login, password, userType, name,surname);
        this.email = email;
    }

    public Mentor(int id, String login, String password, String userType, String name, String surname){
        super(id, login, password, userType, name,surname);
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
