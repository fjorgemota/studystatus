package views.menu;

import javax.swing.*;

/**
 * Created by aluno on 18/03/15.
 */
public class MenuBarView extends JMenuBar {
    public MenuBarView() {
        this.add(new TasksMenu());
        this.add(new ReportsMenu());
    }
}
