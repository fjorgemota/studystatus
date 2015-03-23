package repositories;

import db.DBConnection;
import db.Results;
import models.Status;
import models.Task;
import services.StatusTransformer;

import java.util.ArrayList;

public class TaskRepository {

    private Task resultToTask(Results result) {
        if (!result.next()) {
            return null;
        }
        Task task = new Task();
        task.setId(result.getInt("id"));
        task.setTitle(result.getString("title"));
        task.setDescription(result.getString("description"));
        task.setCreatedAt(result.getTimestamp("created_at"));
        task.setStatus(result.getInt("status"));
        return task;
    }

    private ArrayList<Task> resultsToTasks(Results result) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task;
        while ((task = this.resultToTask(result)) != null) {
            tasks.add(task);
        }
        return tasks;
    }

    public Task findById(int id) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE id="+id);
        return this.resultToTask(result);
    }

    public ArrayList<Task> findByTitle(String title) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE title='"+title+"'");
        return this.resultsToTasks(result);
    }

    public ArrayList<Task> findByDescription(String description) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE description='"+ description+"'");
        return this.resultsToTasks(result);
    }

    public ArrayList<Task> findByStatus(int status) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE status="+status);
        return this.resultsToTasks(result);
    }

    public ArrayList<Task> findByStatus(Status status) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks WHERE status="+ StatusTransformer.statusToInt(status));
        return this.resultsToTasks(result);
    }

    public ArrayList<Task> findAll() {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM tasks");
        return this.resultsToTasks(result);
    }
}
