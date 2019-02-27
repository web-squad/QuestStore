package dao;

import model.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MentorDAOImplementation implements MentorDAO {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement sqlStatement = null;

    public MentorDAOImplementation(JDBCConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public List<Mentor> getListOfMentors() {
        List<Mentor> mentors = new ArrayList<Mentor>();

        try{
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM User u INNER JOIN Mentor m ON u.id=m.userid;");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
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
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        return mentors;
    }

    public Mentor getMentorByLogin(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM public.\"user\" u INNER JOIN mentor m ON u.id = m.userid WHERE u.login LIKE ?;");
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
                return new Mentor(id, login2, password, userType, name, surname, email);
            }
            else {
                System.out.println("Wrong login!");
            }
            connection.commit();
            return null;

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
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
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("INSERT INTO User (login, password, userType, name, surname, email) VALUES (?, ?, ?, ?, ?, ?)");

            sqlStatement.setString(1, login);
            sqlStatement.setString(2, password);
            sqlStatement.setString(3, userType);
            sqlStatement.setString(4, name);
            sqlStatement.setString(4, surname);
            sqlStatement.setString(5, email);

            sqlStatement.executeUpdate();
            System.out.println("Mentor " + name + " addes succesfully!");
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

}
