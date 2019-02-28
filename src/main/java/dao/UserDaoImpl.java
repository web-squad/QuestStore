package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.UserDAO;
import model.user.Codecooler;
import model.user.CreepyGuy;
import model.user.Mentor;
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
        String userType = "";
        try {
            connection = connectionPool.takeOut();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet recordFromDatabase = pst.executeQuery();
            if (recordFromDatabase.next()) {
                  userType = recordFromDatabase.getString("usertype");
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return userType;
    }

    private CreepyGuy getCreepyGuyByLoginAndPassword(String login, String password) {
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

    private Codecooler getCodecoolerByLoginAndPassword(String login, String password) {
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

    private Mentor getMentorByLoginAndPassword(String login, String password) {
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
                return new Mentor(id, login, password, userType, name, surname);
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

    public void addCodecooler(Codecooler codecooler) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?)");
            pst.setString(1, codecooler.getLogin());
            pst.setString(2, codecooler.getPassword());
            pst.setString(3, codecooler.getUserType());
            pst.setString(4, codecooler.getName());
            pst.setString(5, codecooler.getSurname());
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

    public void setId(Codecooler codecooler) {
        try {
            pst = connection.prepareStatement("SELECT id FROM users WHERE login = ? AND password = ?");
            pst.setString(1, codecooler.getLogin());
            pst.setString(2, codecooler.getPassword());
            ResultSet recordFromDatabase = pst.executeQuery();
            if(recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                codecooler.setId(id);
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

    public void updateCodecooler(Codecooler codecooler) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("UPDATE users " +
                    "SET login = ?, password = ?, userType = ?, name = ?, surname = ?");
            pst.setString(1, codecooler.getLogin());
            pst.setString(2, codecooler.getPassword());
            pst.setString(3, codecooler.getUserType());
            pst.setString(4, codecooler.getName());
            pst.setString(5, codecooler.getSurname());
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

    public void deleteCodecooler(Codecooler codecooler) {
        int id  = codecooler.getId();
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
