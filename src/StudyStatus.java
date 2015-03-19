import db.DBConnection;
import models.Status;
import models.Task;

public class StudyStatus {
    private DBConnection dbConn;

    public static void main(String[] argv) {
        StudyStatus instance = new StudyStatus();
        instance.initialize();
        instance.run();
    }

    private void connect() {
        this.dbConn = DBConnection.getInstance();
    }

    protected void initialize() {
        this.connect();
    }

    protected void run() {
    }
}
