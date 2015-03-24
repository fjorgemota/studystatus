package views.menu;

import views.AddTaskView;
import views.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fernando on 23/03/15.
 */
public class TasksMenu extends JMenu implements ActionListener {
    private final String ADD = "ADD";
    public TasksMenu(){
        super("Tasks");
        JMenuItem addTask = new JMenuItem();
        addTask.setText("Add");
        addTask.setActionCommand(ADD);
        addTask.addActionListener(this);
        this.add(addTask);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(ADD)) {
            MainView.getInstance().setContentPane(new AddTaskView());
        }
    }
}
