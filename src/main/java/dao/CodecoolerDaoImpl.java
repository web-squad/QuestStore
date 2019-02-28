package dao;

import dao.connectionPool.JDBCConnectionPool;
import dao.interfaces.CodecoolerDAO;
import model.user.Codecooler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDaoImpl implements CodecoolerDAO {
    private JDBCConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement pst = null;

    public CodecoolerDaoImpl(JDBCConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Codecooler> getAllCodecoolers() {
        List<Codecooler> codecoolerList = new ArrayList<Codecooler>();
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT users.* FROM users " +
                                                    "INNER JOIN codecooler " +
                                                    "ON users.id = codecooler.userid");
            ResultSet recordFromDatabase = pst.executeQuery();
            while (recordFromDatabase.next()) {
                int userid = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                Codecooler codecooler = new Codecooler(userid,login,password,"codecooler",name, surname);
                codecoolerList.add(codecooler);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return codecoolerList;
    }

    public List<Codecooler> getCodecoolersByTeamId(int teamid) {
        List<Codecooler> codecoolerList = new ArrayList<Codecooler>();
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT users.* FROM users " +
                                                    "INNER JOIN codecooler " +
                                                    "ON users.id = codecooler.userid " +
                                                    "WHERE codecooler.teamid = ?");
            pst.setInt(1, teamid);
            ResultSet recordFromDatabase = pst.executeQuery();
            while (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                Codecooler codecooler = new Codecooler(id,login,password,"codecooler",name, surname);
                codecoolerList.add(codecooler);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return codecoolerList;
    }

    public List<Codecooler> getCodecoolersByRoomId(int roomid) {
        List<Codecooler> codecoolerList = new ArrayList<Codecooler>();
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT users.*, codecooler.* FROM users " +
                                                    "INNER JOIN codecooler " +
                                                    "ON users.id = codecooler.userid " +
                                                    "WHERE codecooler.roomid = ?");
            pst.setInt(1, roomid);
            ResultSet recordFromDatabase = pst.executeQuery();
            while (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                Codecooler codecooler = new Codecooler(id,login,password,"codecooler",name, surname);
                codecoolerList.add(codecooler);
            }
            connection.commit();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.takeIn(connection);
        }
        return codecoolerList;
    }

    public Codecooler getCodecoolerByUserId(int codecoolerId) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("SELECT users.*, codecooler.* FROM users " +
                                                    "INNER JOIN codecooler " +
                                                    "ON users.id = codecooler.userid " +
                                                    "WHERE codecooler.id = ?");
            pst.setInt(1, codecoolerId);
            ResultSet recordFromDatabase = pst.executeQuery();
            while (recordFromDatabase.next()) {
                int id = recordFromDatabase.getInt("id");
                String login = recordFromDatabase.getString("login");
                String password = recordFromDatabase.getString("password");
                String name = recordFromDatabase.getString("name");
                String surname = recordFromDatabase.getString("surname");
                int roomId = recordFromDatabase.getInt("roomid");
                int teamId = recordFromDatabase.getInt("teamid");
                int earnings = recordFromDatabase.getInt("earnings");

                Codecooler codecooler = new Codecooler(id,login,password,"codecooler",name, surname);
                codecooler.setCodecoolerId(codecoolerId);
                codecooler.setRoomId(roomId);
                codecooler.setTeamId(teamId);
                codecooler.setEarnings(earnings);
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
            pst = connection.prepareStatement("INSERT INTO codecooler VALUES(?, ?, ?, ?)");
            pst.setInt(1, codecooler.getRoomId());
            pst.setInt(2, codecooler.getTeamId());
            pst.setInt(3, codecooler.getId());
            pst.setInt(4, codecooler.getEarnings());
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

    public void updateCodecooler(Codecooler codecooler) {
        try {
            connection = connectionPool.takeOut();
            pst = connection.prepareStatement("UPDATE codecooler " +
                    "SET roomid = ?, teamid = ?, userid = ?, earnings = ?");
            pst.setInt(1, codecooler.getRoomId());
            pst.setInt(2, codecooler.getTeamId());
            pst.setInt(3, codecooler.getId());
            pst.setInt(4, codecooler.getEarnings());
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
            pst = connection.prepareStatement("DELETE FROM codecooler WHERE userid = ?");
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
