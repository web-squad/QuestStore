package dao;

import user.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MentorDAOImplementation implements MentorDAO {
    private Connection c = null;
    private PreparedStatement sqlStatement = null;
    private Statement stmt = null;

    public List<Mentor> getListOfMentors() {
        List<Mentor> mentors = new ArrayList<Mentor>();

        Connection c = null;
        try{
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
//            sqlStatement = c.prepareStatement("SELECT * FROM User where userType LIKE 'mentor' ORDER BY id");
//            ResultSet recordsFromDB = sqlStatement.executeQuery();

            sqlStatement = c.prepareStatement("SELECT * FROM User u INNER JOIN Mentor m ON u.id=m.userid;");
            ResultSet recordsFromDB = sqlStatement.executeQuery();

            while(recordsFromDB.next()){
                int mentorId = recordsFromDB.getInt("id");
                String login = recordsFromDB.getString("login");
                String password = recordsFromDB.getString("password");
                String userType = recordsFromDB.getString("userType");
                String name = recordsFromDB.getString("name");
                String surname = recordsFromDB.getString("surname");
                String email = recordsFromDB.getString("email");

                mentors.add(new Mentor(mentorId, login, password, userType, name, surname, email));
            }

        } catch (SQLException sqlError){
            sqlError.printStackTrace();
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
            System.exit(0);
        } finally {
            try {
                sqlStatement.close();
                c.close();
            } catch (SQLException e) {
                System.out.println("Unable to close connection!");
            }
        }
        return mentors;
    }

    public Mentor getMentor(String login) {
        try {
            c = DriverManager.getConnection("jdbc:postgresql://locahost:5432/QuestStore", "admin", "123");
            sqlStatement = c.prepareStatement("SELECT * FROM User u INNER JOIN Menor m ON u,id=m.userid WHERE login LIKE ?");
            sqlStatement.setString(1,login);
            ResultSet recordFromDB = sqlStatement.executeQuery();

            if(recordFromDB.next()){
                int id = recordFromDB.getInt("id");
                String login2 = recordFromDB.getString("login");
                String password = recordFromDB.getString("password");
                String userType = recordFromDB.getString("userType");
                String name = recordFromDB.getString("name");
                String surname = recordFromDB.getString("surname");
                String email = recordFromDB.getString("email");
                return new Mentor(id, login, password, userType, name, surname, email);
            }
            else {
                System.out.println("Wrong login!");
            }
            return null;

        } catch (SQLException sqlError){
            sqlError.printStackTrace();
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
        } finally {
            try{
                sqlStatement.close();
                c.close();
            } catch (SQLException e) {
                System.out.println("Unable to close connection!");
            }
        }
        return null;
    }


    public void addNewMentor(Mentor mentor) {
        String login = mentor.getLogin();
        String password = mentor.getPassword();
        String userType = mentor.getUserType();
        String name = mentor.getName();
        String surname = mentor.getSurname();
        String email = mentor.getEmail();

        try {
            c = DriverManager.getConnection("jdbc:postgresql://locahost:5432/QuestStore", "admin", "123");
            sqlStatement = c.prepareStatement("INSERT INTO User (login, password, userType, name, surname, email) VALUES (?, ?, ?, ?, ?, ?)");

            sqlStatement.setString(1, login);
            sqlStatement.setString(2, password);
            sqlStatement.setString(3, userType);
            sqlStatement.setString(4, name);
            sqlStatement.setString(4, surname);
            sqlStatement.setString(5, email);

            sqlStatement.executeUpdate();
            System.out.println("Mentor " + name + " addes succesfully!");
        } catch (SQLException sqlError){
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
            System.exit(0);
        } finally {
            try{
                sqlStatement.close();
                c.close();
            } catch (SQLException closeError){
                System.out.println("Unable to close connection!");
            }
        }
    }

}
