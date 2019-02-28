package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.MentorDAO;
import model.user.Mentor;
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
            sqlStatement = connection.prepareStatement("SELECT * FROM Users u INNER JOIN Mentor m ON u.login=m.usrlogin;");
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
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
        }
        return mentors;
    }

    public Mentor getMentorByLogin(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN mentor m ON u.login = m.usrlogin WHERE u.login LIKE ?;");
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
        String name = mentor.getName();
        String surname = mentor.getSurname();
        String email = mentor.getEmail();

        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);

            sqlStatement = connection.prepareStatement("INSERT INTO mentor (email, roomid, userid) VALUES (?, ?, ?)");
            sqlStatement.setString(1, email);
            sqlStatement.setInt(2, 0);
            sqlStatement.setInt(3, 2 );

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


    public void updateMentorData (String dataToChange, String changedData, String login){
        try{
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);

            sqlStatement = connection.prepareStatement("UPDATE users SET " + dataToChange + " = ? WHERE login LIKE ?");
            sqlStatement.setString(1,changedData);
            sqlStatement.setString(2, login);

            sqlStatement.executeUpdate();
            dataToChange = "usrlogin";
            sqlStatement = connection.prepareStatement("UPDATE mentor SET " + dataToChange + " = ? WHERE usrlogin LIKE ?");
            sqlStatement.setString(1,changedData);
            sqlStatement.setString(2, login);
            sqlStatement.executeUpdate();

            connection.commit();

        } catch (SQLException sqlError){
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
        }   try{
            connectionPool.takeIn(connection);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void deleteMentorBylogin (String login){
        try{
            connection = connectionPool.takeOut();
            sqlStatement = connection.prepareStatement("DELETE FROM users WHERE login LIKE ?");

            sqlStatement.setString(1, login);
        }   catch (SQLException sqlError){
            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
        }   try{
            connectionPool.takeIn(connection);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
