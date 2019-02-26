package user;

public abstract class User {
    private int id;
    private String login;
    private String password;
    private String userType;
    private String name;
    private String surname;


    public User(int id, String login, String password, String userType, String name, String surname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
