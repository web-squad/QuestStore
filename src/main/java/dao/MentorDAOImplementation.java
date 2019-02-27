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
            sqlStatement = connection.prepareStatement("SELECT * FROM Users u INNER JOIN Mentor m ON u.id=m.userid;");
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
        } catch (SQLException sqlError) {
            System.err.println( sqlError.getClass().getName()+": "+ sqlError.getMessage() );
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
            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN mentor m ON u.id = m.userid WHERE u.login LIKE ?;");
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

        } catch (SQLException sqlError) {
            System.err.println( sqlError.getClass().getName()+": "+ sqlError.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        return null;
    }


    public void addNewMentor(Mentor mentor) {
        int id = mentor.getId();
        String login = mentor.getLogin();
        String password = mentor.getPassword();
        String userType = mentor.getUserType();
        String name = mentor.getName();
        String surname = mentor.getSurname();
        String email = mentor.getEmail();

        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("INSERT INTO users (id, login, password, userType, name, surname) VALUES (?, ?, ?, ?, ?, ?)");

            sqlStatement.setInt(1, id);
            sqlStatement.setString(2, login);
            sqlStatement.setString(3, password);
            sqlStatement.setString(4, userType);
            sqlStatement.setString(5, name);
            sqlStatement.setString(6, surname);

            sqlStatement.executeUpdate();

            sqlStatement = connection.prepareStatement("INSERT INTO mentor (id, email, classid, userid) VALUES (?, ?, ?, ?)");
            sqlStatement.setInt(1, id);
            sqlStatement.setString(2, email);
            sqlStatement.setInt(3, 0);
            sqlStatement.setInt(4, id);

            sqlStatement.executeUpdate();
            System.out.println("Mentor " + name + " addes succesfully!");
            connection.commit();
        } catch (SQLException sqlError) {
            System.err.println( sqlError.getClass().getName()+": "+ sqlError.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    public void updateMentorData(String dataToChange, String changedData, String login){
        try{
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("UPDATE users SET ? = ? WHERE login LIKE ?");

            sqlStatement.setString(1, dataToChange);
            sqlStatement.setString(2, changedData);
            sqlStatement.setString(3, login);

        } catch (SQLException sqlError){
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
        }
    }

}
