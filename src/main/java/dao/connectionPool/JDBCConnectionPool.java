package dao.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionPool extends ObjectPool<Connection> {
    private String dsn;
    private String usr;
    private String pwd;

    public JDBCConnectionPool(String dsn, String usr, String pwd)
    {
        super();
        this.dsn = dsn;
        this.usr = usr;
        this.pwd = pwd;
    }

    public Connection create()
    {
        try {
            return (DriverManager.getConnection(dsn, usr, pwd));
        }
        catch (SQLException e) {
            e.printStackTrace();
            return (null);
        }
    }

    public void dead(Connection o)
    {
        try {
            o.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validate(Connection o)
    {
        try {
            return (!o.isClosed());
        }
        catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }
}

