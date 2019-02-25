import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static java.awt.SystemColor.TEXT;
import static java.sql.DriverManager.getConnection;
import static java.sql.JDBCType.INTEGER;
import static java.text.Collator.PRIMARY;
import static sun.security.x509.X509CertInfo.KEY;

public class Table {
    private Connection c = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;

    public void createTable() {
        try {
            Class.forName("org.postgresql.Driver");
            c = getConnection("jdbc:postgresql://localhost:5432/QuestStore",
                    "ejaworska", "ewelina123");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE mentors (" +
                    "   id               serial primary key not null," +
                    "   first_name       text    not null," +
                    "   last_name        text    not null," +
                    "   nick_name        text," +
                    "   phone_number     text," +
                    "   email            text    not null unique," +
                    "   city             text," +
                    "   favourite_number integer)";
            String sql2 = "CREATE TABLE Class ( +
                    "id INTEGER primary key not null" +
                    "name" TEXT,
                    PRIMARY KEY ("id")";
);
            stmt.executeUpdate(sql);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            //System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (Exception e){
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            }
        }
        System.out.println("Table created successfully");
    }

}
