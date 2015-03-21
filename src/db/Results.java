package db;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

class Results {
    private ResultSet rs;
    public Results(ResultSet rs){
        this.rs = rs;
    }

    public boolean next(){
        try{
            return rs.next();
        }catch(SQLException sqlError){
            System.err.println("Error on the SELECT query: "+sqlError);
            return false;
        }
    }

    public String getString(String field){
        try{
            return rs.getString(field);
        }catch(SQLException sqlError){
            System.err.println("Error on getting a string from the SELECT Results: "+sqlError);
            return "";
        }
    }

    public int getInt(String field){
        try{
            return rs.getInt(field);
        }catch(SQLException sqlError){
            System.err.println("Error on getting a integer from the SELECT Results: "+sqlError);
            return -1;
        }
    }

    public boolean getBoolean(String field){
        try{
            return rs.getBoolean(field);
        }catch(SQLException sqlError){
            System.err.println("Error on getting a boolean from the SELECT Results: "+sqlError);
            return false;
        }
    }
    
    public Timestamp getTimestamp(String field) {
        try{
            return rs.getTimestamp(field);
        }catch(SQLException sqlError){
            System.err.println("Error on getting a timestamp from the SELECT Results: "+sqlError);
            return null;
        }
    }
}