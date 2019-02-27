package model;

import user.User;

public class Mentor extends User {
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

    public String toString(){
        String mentorInfo = "Login: " + this.getLogin() + "\nPassword: " + this.getPassword() + "\nUser type: " + this.getUserType() + "\nName: " + this.getName() + "\nSurname: " + this.getSurname() + "\nEmail: " + this.getEmail();
        return mentorInfo;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
