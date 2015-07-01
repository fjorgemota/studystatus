package models;

import db.DBConnection;

public class TaskComment implements BaseModel {
	private String comment;
	private int task_id = -1;
	private int id;

	public boolean insert() {
		DBConnection connect = DBConnection.getInstance();
		return connect.executeInsert("INSERT INTO comments (comment, task_id) VALUES ('" + this.comment + "', " +this.task_id+ ")");
	}

	public boolean delete() {
		DBConnection connect = DBConnection.getInstance();
		return connect.executeDelete("DELETE FROM comments WHERE id = " + this.id);
	}

	public boolean update() {
		DBConnection connect = DBConnection.getInstance();
		return connect.executeUpdate("UPDATE comments SET comment = '" + this.comment + "' WHERE id = " + this.id);
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTaskId(int task_id) {
		this.task_id = task_id;
	}

	public int getTaskId() {
		return this.task_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String validate() {
		if (this.comment == null || this.comment.trim().isEmpty()) {
			return "This comment text should be empty!";
		}
        if (this.comment.length() > 100) {
            return "This comment should have less than 100 characters!";
        }
		if (this.task_id < 1) {
			return "This comment should be associated with a task!";
		}
		return null;
	}
}