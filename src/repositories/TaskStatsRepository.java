package repositories;

import db.DBConnection;
import db.Results;
import models.TaskComment;
import models.TaskStat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskStatsRepository
{
    private TaskStat resultToTaskStat(Results result) {
        if (!result.next()) {
            return null;
        }
        return new TaskStat(result.getString("text"), result.getInt("count"));
    }

    private ArrayList<TaskStat> resultsToTaskStats(Results result) {
        ArrayList<TaskStat> stats = new ArrayList<TaskStat>();
        TaskStat stat;
        while ((stat = this.resultToTaskStat(result)) != null) {
            stats.add(stat);
        }
        return stats;
    }


    public ArrayList<TaskStat> getNotDoneByDay() {
        DBConnection aggregator = DBConnection.getInstance();
        Results result = aggregator.executeSelect("SELECT '     Anterior' AS text, COUNT(*) AS count FROM tasks WHERE status_changed_at < DATEADD('DAY', -7, CURRENT_TIMESTAMP())");
        ArrayList<TaskStat> resultado = this.resultsToTaskStats(result);
        result = aggregator.executeSelect("SELECT CONCAT('Dia', DAY(status_changed_at), '/', MONTH(status_changed_at), '/', YEAR(status_changed_at)) AS text, COUNT(*) AS count FROM tasks WHERE status_changed_at >= DATEADD('DAY', -7, CURRENT_TIMESTAMP())");
        resultado.addAll(this.resultsToTaskStats(result));
        return resultado;
    }
}