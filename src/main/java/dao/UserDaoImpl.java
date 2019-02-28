package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.UserDAO;
import model.user.Codecooler;
import model.user.CreepyGuy;
import model.user.Mentor;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {

    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public UserDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public String getUserType(String login, String password) {
        String usertype = "";
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                  usertype = recordFromDatabase.getString("usertype");
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return usertype;
    }

    public CreepyGuy getCreepyGuyByLoginAndPassword(String login, String password) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String userType = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new CreepyGuy(id, login, password, userType, name, surname);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public Codecooler getCodecoolerByLoginAndPassword(String login, String password) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String userType = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new Codecooler(id, login, password, userType, name, surname);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public Mentor getMentorByLoginAndPassword(String login, String password) {
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String usertype = recordFromDatabase.getString("usertype");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new Mentor(id, login, password, usertype, name, surname);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public Codecooler getCodecoolerByUserId(int userId) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM users WHERE  id = ?");
            pst.setInt(1, userId);
            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new Codecooler(id,login,password,"codecooler",name, surname);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public Mentor getMentorByUserId(int userId) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT * FROM users WHERE  id = ?");
            pst.setInt(1, userId);
            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                return new Mentor(id,login,password,"mentor",name, surname);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return null;
    }

    public void addUser(User user) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("INSERT INTO users (login, password, usertype, name, surname) VALUES(?, ?, ?, ?, ?)");
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserType());
            pst.setString(4, user.getName());
            pst.setString(5, user.getSurname());
            pst.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public void setUserId(User user) {
        try {
            pst = connection.prepareStatement("SELECT id FROM users WHERE login = ? AND password = ?");
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());
            ResultSet recordFromDatabase = pst.executeQuery();
            if(recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                user.setId(id);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public void updateUser(User user) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("UPDATE users " +
                    "SET login = ?, password = ?, usertype = ?, name = ?, surname = ?");
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserType());
            pst.setString(4, user.getName());
            pst.setString(5, user.getSurname());
            pst.executeUpdate();
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }

    public void deleteUser(User user) {
        int id  = user.getId();
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            pst.setInt(1, id);
            pst.executeUpdate();
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
    }
}
