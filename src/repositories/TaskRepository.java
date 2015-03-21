package repositories;

import db.DBConnection;
import db.Results;
import models.Task;
import java.util.ArrayList;

public class TaskRepository {

    private Task result_to_task(Results result) {
        Task task = new Task();
        task.setId(result.getInt("id"));
        task.setTitle(result.getString("title"));
        task.setDescription(result.getString("description"));
        task.setCreatedAt(result.getTimestamp("created_at"));
        task.setStatus(result.getInt("status"));
        return task;
    }

    public Task findTaskId(int id_request) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE id="+id_request);
        if (!result.next()) {
            return null;
        }
        Task target = this.result_to_task(result);
        return target;
    }

    public ArrayList<Task> findTaskTitle(String title_request) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE title='"+title_request+"'");
        ArrayList<Task> tasks = new ArrayList<Task>();
        while (result.next()) {
            tasks.add(this.result_to_task(result));
        }
        return tasks;
    }

    public ArrayList<Task> findTaskDescription(String description_request) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE description='"+ description_request+"'");
        ArrayList<Task> tasks = new ArrayList<Task>();
        while (result.next()) {
            tasks.add(this.result_to_task(result));
        }
        return tasks;
    }

    public ArrayList<Task> findTaskStatus(int status_request) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE status="+status_request);
        ArrayList<Task> tasks = new ArrayList<Task>();
        while (result.next()) {
            tasks.add(this.result_to_task(result));
        }
        return tasks;
    }

    public ArrayList<Task> findAll() {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks");
        ArrayList<Task> tasks = new ArrayList<Task>();
        while (result.next()) {
            tasks.add(this.result_to_task(result));
        }
        return tasks;
    }
}
