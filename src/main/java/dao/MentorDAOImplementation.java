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
//            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN Mentor m ON u.id=m.userid;");
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
//            connection.commit();
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
            sqlStatement = connection.prepareStatement("INSERT INTO users (login, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)");

            sqlStatement.setString(1, login);
            sqlStatement.setString(2, password);
            sqlStatement.setString(3, userType);
            sqlStatement.setString(4, name);
            sqlStatement.setString(5, surname);

            sqlStatement.executeUpdate();
            sqlStatement = connection.prepareStatement("INSERT INTO mentor (email, roomid, userid) VALUES (?, ?, ?)");

            CreepyGuyDAOImpl adminDAO = new CreepyGuyDAOImpl(connectionPool);

            sqlStatement.setString(1, email);
            sqlStatement.setInt(2, 1);
            sqlStatement.setString(3, adminDAO.getMentorByLogin(login).getLogin());
            sqlStatement.executeUpdate();

            System.out.println("Mentor " + name + " added succesfully!");
            connection.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }

    public Mentor getMentorByRoomId(int roomid) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN mentor m ON u.id = m.userid WHERE m.roomid = ?");
            sqlStatement.setInt(1, roomid);
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

    public List<Mentor> getMentorsByRoomId(int roomid) {
        List<Mentor> mentors = new ArrayList<>();
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN mentor m ON u.id = m.userid WHERE m.roomid = ?");
            sqlStatement.setInt(1, roomid);
            ResultSet recordFromDB = sqlStatement.executeQuery();

            while(recordFromDB.next()){
                int id = recordFromDB.getInt("id");
                String login2 = recordFromDB.getString("login");
                String password = recordFromDB.getString("password");
                String userType = recordFromDB.getString("userType");
                String name = recordFromDB.getString("name");
                String surname = recordFromDB.getString("surname");
                String email = recordFromDB.getString("email");
                mentors.add(new Mentor(id, login2, password, userType, name, surname, email));
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

    public Mentor getMentorById(int id) {
        try {
            connection = connectionPool.takeOut();
            //connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("SELECT users.*, mentor.email FROM users LEFT JOIN mentor ON users.id = mentor.userid WHERE mentor.userid = ?;");
            sqlStatement.setInt(1, id);
            ResultSet recordFromDB = sqlStatement.executeQuery();

            if(recordFromDB.next()){
                id = recordFromDB.getInt("id");
                String login = recordFromDB.getString("login");
                String password = recordFromDB.getString("password");
                String userType = recordFromDB.getString("userType");
                String name = recordFromDB.getString("name");
                String surname = recordFromDB.getString("surname");
                String email = recordFromDB.getString("email");
                return new Mentor(id, login, password, userType, name, surname, email);
            }
//            else {
//                System.out.println("Wrong login!");
//            }
            //connection.commit();
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
}
