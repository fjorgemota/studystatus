package db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    DataSource ds;
    Statement statement;
    String user = "sa";
    String pass = "";
    private static DBConnection instance = null;

    public static DBConnection getInstance(){
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    private DBConnection()
    {
        try {
            JdbcDataSource h2 = new JdbcDataSource();
            h2.setUser(user);
            h2.setPassword(pass);
            h2.setUrl("jdbc:h2:db/StudyStatus");

            ds = h2;

            Flyway migration = new Flyway();
            migration.setDataSource(h2);
            migration.setLocations("migrations/");
            migration.migrate();

            Connection conn = h2.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e){
            System.err.println("Error on the connection with the database: "+e);
        }
    }

    public boolean executeInsert(String sql)
    {
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e){
            System.err.println("Error on the INSERT query: "+e);
            return false;
        }
    }

    public boolean executeUpdate(String sql)
    {
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e){
            System.err.println("Error on the UPDATE query: "+e);
            return false;
        }
    }

    public boolean executeDelete(String sql)
    {
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e){
            System.err.println("Error on the DELETE query: "+e);
            return false;
        }
    }

    public Results executeSelect(String sql)
    {
        try {
            ResultSet rs = statement.executeQuery(sql);
            Results cr = new Results(rs);
            return cr;
        } catch (SQLException e){
            System.err.println("Error on the SELECT query: " + e);
            return null;
        }
    }
}