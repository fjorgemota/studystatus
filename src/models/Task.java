package models;

import db.DBConnection;
import services.StatusTransformer;

import java.sql.Date;
import java.sql.Timestamp;

public class Task implements BaseModel {
    private int id;
    private String description;
    private String title;
    private int status;
    private Timestamp created_at;
    private Date status_changed_at;

    public boolean insert() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeInsert("INSERT INTO tasks (title, description, status, status_changed_at) VALUES ('" + getTitle() + "', '" + getDescription() + "', " + this.status + ", '"+ this.status_changed_at+"')");
    }

    public boolean delete() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeDelete("DELETE FROM tasks WHERE id=" + getId());
    }

    public boolean update() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeUpdate("UPDATE tasks SET title = '" + getTitle() + "', description = '" + getDescription() + "', status = " + this.status + ", status_changed_at = '"+this.status_changed_at+"' WHERE id=" + this.getId());
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
        java.util.Date now = new java.util.Date();
        this.status_changed_at = new Date(now.getTime());
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

    public Date getStatusChangedAt() {
        return status_changed_at;
    }

    @Override
    public String validate() {
        if (this.getTitle() == null || this.getTitle().trim().isEmpty()) {
            return "The title cannot be empty!";
        }
        if (this.getTitle().length() > 50) {
            return "The title should have less than 50 characters!";
        }
        if (this.getDescription() == null || this.getDescription().trim().isEmpty()) {
            return "The description cannot be empty!";
        }
        if (this.getDescription().length() > 100) {
            return "The description should have less than 100 characters!";
        }
        return null;
    }
}
