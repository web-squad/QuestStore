package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.MentorDAO;
import model.user.Mentor;
import model.user.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class MentorDAOImplementation implements MentorDAO {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement sqlStatement = null;

    public MentorDAOImplementation(JDBCConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
            "admin", "123");

    @Override
    public void addNewMentor(Mentor mentor) {

        try {
            UserDaoImpl userDao = new UserDaoImpl(pool);
            userDao.addUser(mentor);

            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("INSERT INTO mentor (email, roomid, userid) VALUES (?, ?, ?)");
            sqlStatement.setString(1, mentor.getEmail());
            sqlStatement.setInt(2, 0); // skąd wziąć roomid? O.o
            sqlStatement.setInt(3, mentor.getId());

            sqlStatement.executeUpdate();
            connection.commit();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    @Override
    public void updateMentorData(Mentor mentor) {
        try{
            UserDaoImpl userDao = new UserDaoImpl(pool);
            userDao.updateUser(mentor);

            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            sqlStatement = connection.prepareStatement("UPDATE mentor SET email = ?");
            sqlStatement.setString(1, mentor.getEmail());

        }catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
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
//            connection.commit();
        } catch (SQLException sqlError) {
            System.err.println( sqlError.getClass().getName()+": "+ sqlError.getMessage() );
        } try {
            connectionPool.takeIn(connection);
        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
        }
        return mentors;
    }

//    public model.Mentor getMentorByLogin(String login) {
//        try {
//            connection = connectionPool.takeOut();
//            connection.setAutoCommit(false);
//            sqlStatement = connection.prepareStatement("SELECT * FROM users u INNER JOIN mentor m ON u.login = m.usrlogin WHERE u.login LIKE ?;");
//            sqlStatement.setString(1,login);
//            ResultSet recordFromDB = sqlStatement.executeQuery();
//
//            if(recordFromDB.next()){
//                int id = recordFromDB.getInt("id");
//                String login2 = recordFromDB.getString("login");
//                String password = recordFromDB.getString("password");
//                String userType = recordFromDB.getString("userType");
//                String name = recordFromDB.getString("name");
//                String surname = recordFromDB.getString("surname");
//                String email = recordFromDB.getString("email");
//                return new Mentor(id, login2, password, userType, name, surname, email);
//            }
//            else {
//                System.out.println("Wrong login!");
//            }
//            connection.commit();
//            return null;
//
//        } catch (SQLException sqlError) {
//            System.err.println( sqlError.getClass().getName()+": "+ sqlError.getMessage() );
//        } try {
//            connectionPool.takeIn(connection);
//        } catch (Exception e){
//            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//        }
//        return null;
//    }

//
//    public void deleteMentorBylogin (String login){
//        try{
//            connection = connectionPool.takeOut();
//            sqlStatement = connection.prepareStatement("DELETE FROM users WHERE login LIKE ?");
//
//            sqlStatement.setString(1, login);
//        }   catch (SQLException sqlError){
//            System.err.println(sqlError.getClass().getName() + ": " + sqlError.getMessage());
//        }   try{
//            connectionPool.takeIn(connection);
//        }catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
//    }

}
