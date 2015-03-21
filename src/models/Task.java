package models;

import db.DBConnection;

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
        return connection.executeDelete("DELETE FROM tasks WHERE id="+getId());
    }

    public boolean update() {
        DBConnection connection = DBConnection.getInstance();
        return connection.executeUpdate("UPDATE tasks SET title = '" + getTitle() + "', description = '" + getDescription() + "', status = " + this.status + " WHERE id="+this.getId());
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
        switch (this.status) {
            default:
            case 0:
                return Status.NOT_STARTED;

            case 1:
                return Status.TO_DO;

            case 2:
                return Status.DONE;

            case 3:
                return Status.BLOCKED;
        }
    }

    public void setStatus(Status status) {
        int progress;
        switch (status) {
            default:
            case NOT_STARTED:
                progress = 0;
                break;

            case TO_DO:
                progress = 1;
                break;

            case DONE:
                progress = 2;
                break;

            case BLOCKED:
                progress = 3;
        }
        this.status = progress;
    }

    public void setStatus(int status) {
        switch (status) {
            default:
            case 0:
                this.status = 0;
                break;

            case 1:
                this.status = 1;
                break;

            case 2:
                this.status = 2;
                break;

            case 3:
                this.status = 3;
        }
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }
}
