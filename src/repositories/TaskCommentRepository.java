package repositories;

import db.DBConnection;
import db.Results;
import models.Task;
import models.TaskComment;

import java.util.ArrayList;

public class TaskCommentRepository {
    private TaskComment resultToComment(Results result) {
        if (!result.next()) {
            return null;
        }
        TaskComment comment = new TaskComment();
        comment.setId(result.getInt("id"));
        comment.setComment(result.getString("comment"));
        comment.setTaskId(result.getInt("task_id"));
        return comment;
    }

    private ArrayList<TaskComment> resultsToComments(Results result) {
        ArrayList<TaskComment> comments= new ArrayList<TaskComment>();
        TaskComment comment;
        while ((comment = this.resultToComment(result)) != null) {
            comments.add(comment);
        }
        return comments;
    }

    public TaskComment findById(int id) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM comments WHERE id="+id);
        return this.resultToComment(result);
    }

    public ArrayList<TaskComment> findByTask(Task task) {
        return this.findByTask(task.getId());
    }

    public ArrayList<TaskComment> findByTask(int task_id) {
        DBConnection search = DBConnection.getInstance();
        Results result = search.executeSelect("SELECT * FROM comments WHERE task_id="+task_id);
        return this.resultsToComments(result);
    }
    
}
