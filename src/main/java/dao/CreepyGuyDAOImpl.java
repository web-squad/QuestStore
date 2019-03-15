package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.DAOCreepyGuy;
import model.user.CreepyGuy;
import model.user.Mentor;

import java.sql.*;

public class CreepyGuyDAOImpl implements DAOCreepyGuy {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public CreepyGuyDAOImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public CreepyGuy getCreepyGuy(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            pst.setString(1, login);


            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String loginFromDB = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String userType = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new CreepyGuy(id, loginFromDB, password, userType, name, surname);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }
    public Mentor getMentorByLogin(String login) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users u LEFT JOIN mentor m ON u.id = m.userid WHERE u.login LIKE ?;");
            pst.setString(1,login);
            ResultSet recordFromDB = pst.executeQuery();

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
}

