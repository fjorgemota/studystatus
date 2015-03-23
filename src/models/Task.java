package models;

import db.DBConnection;
import services.StatusTransformer;

import java.sql.Timestamp;

public class Task implements BaseModel {
    private int id;
    private String description;
    private String title;
    private int status;
    private Timestamp created_at;

    public boolean insert() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeInsert("INSERT INTO tasks (title, description, status) VALUES ('" + getTitle() + "', '" + getDescription() + "', " + this.status + ")");
    }

    public boolean delete() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeDelete("DELETE FROM tasks WHERE id=" + getId());
    }

    public boolean update() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeUpdate("UPDATE tasks SET title = '" + getTitle() + "', description = '" + getDescription() + "', status = " + this.status + " WHERE id=" + this.getId());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return StatusTransformer.intToStatus(this.status);
    }

    public void setStatus(Status status) {
        this.status = StatusTransformer.statusToInt(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }
}
