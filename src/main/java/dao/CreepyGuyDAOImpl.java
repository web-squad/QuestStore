package dao;

import dao.interfaces.DAOCreepyGuy;
import model.user.CreepyGuy;

import java.sql.*;

public class CreepyGuyDAOImpl implements DAOCreepyGuy {

    //    This class should be removed!

    //    private Connection conn = null;
//    private PreparedStatement preStatement;
    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public CreepyGuyDAOImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
//    public void connectToDB(){
//        try{
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
//        }catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Opened database successfully");
//    }

    public CreepyGuy getCreepyGuy(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM user WHERE login = ?");
            pst.setString(1, login);


            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String loginFromDB = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String userType = recordFromDatabase.getString("userType");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new CreepyGuy(id, loginFromDB, password, userType, name, surname);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//
//        CreepyGuyDAOImpl dCreepy = new CreepyGuyDAOImpl();
//
//        dCreepy.connectToDB();
//    }
}

