package dao;

import user.CreepyGuy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreepyGuyDAOImpl implements DAOCreepyGuy {

    private Connection conn = null;
    private PreparedStatement preStatement;

    public void connectToDB(){
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/QuestStore", "admin", "123");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public CreepyGuy getCreepyGuy(String login) {
        return null;
    }

    public static void main(String[] args) {

        CreepyGuyDAOImpl dCreepy = new CreepyGuyDAOImpl();

        dCreepy.connectToDB();
    }
}

