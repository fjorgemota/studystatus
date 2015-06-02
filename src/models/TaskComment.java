package models;

import db.DBConnection;

public class TaskComment implements BaseModel {
	private String comment;
	private int task_id;
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

	public void setTask(Task task) {
		this.task_id = task.getId();
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
}