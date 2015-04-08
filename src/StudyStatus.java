import views.MainView;

public class StudyStatus {
    public static void main(String[] argv) {
        StudyStatus instance = new StudyStatus();
        instance.run();
    }

    protected void run() {
        MainView view = MainView.getInstance();
        view.setVisible(true);
    }
}
