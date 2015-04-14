package views;

import views.kanban.KanbanView;
import views.menu.MenuBarView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aluno on 18/03/15.
 */
public class MainView extends JFrame {
    private static MainView instance;

    public static MainView getInstance(){
        if (instance == null) {
            instance = new MainView();
        }
        return instance;
    }

    private MainView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("StudyStatus");
        this.setJMenuBar(new MenuBarView());
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(200, 200));
        this.setContentPane(new KanbanView());
    }

    public void setContentPane(RenderableView contentPane) {
        super.setContentPane(contentPane);
        this.revalidate();
        this.repaint();
    }

    public void repaint() {
        RenderableView view = (RenderableView) this.getContentPane();
        view.render();
    }
}
