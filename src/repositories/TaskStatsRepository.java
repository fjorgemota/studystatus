package repositories;

import db.DBConnection;
import db.Results;
import models.TaskComment;
import models.TaskStat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Results result = aggregator.executeSelect("SELECT '     Anterior' AS text, COUNT(*) AS count FROM tasks WHERE status <> 2 AND status_changed_at < DATEADD('DAY', -7, CURRENT_TIMESTAMP())");
        ArrayList<TaskStat> resultado = this.resultsToTaskStats(result);
        result = aggregator.executeSelect("SELECT CONCAT('Dia ', FORMATDATETIME(status_changed_at,   'd/M/y')) AS text, COUNT(*) AS count FROM tasks WHERE status <> 2 AND status_changed_at >= DATEADD('DAY', -7, CURRENT_TIMESTAMP()) GROUP BY status_changed_at");
        resultado.addAll(this.resultsToTaskStats(result));
        for (int i = 0; i <= 7; i++) {
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7 + i);
            String f = "Dia "+cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
            boolean found = false;
            for (int p = 0; p < resultado.size(); p++) {
                if (resultado.get(p).getText().equals(f)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                TaskStat n = new TaskStat(f, 0);
                resultado.add(i+1, n);
            }
        }
        for(int i=1; i < resultado.size(); i++) {
            resultado.get(i).setCount(resultado.get(i).getCount()+resultado.get(i-1).getCount());
        }
        return resultado;
    }
}