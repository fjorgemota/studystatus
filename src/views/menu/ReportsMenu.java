package views.menu;

import views.MainView;
import views.forms.AddTaskView;
import views.report.BurndownGraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fernando on 23/03/15.
 */
public class ReportsMenu extends JMenu implements ActionListener {
    private final String BURNDOWN_GRAPH = "BURNDOWN_GRAPH";
    public ReportsMenu(){
        super("Reports");
        JMenuItem burndownGraph = new JMenuItem();
        burndownGraph.setText("Burndown Graph");
        burndownGraph.setActionCommand(BURNDOWN_GRAPH);
        burndownGraph.addActionListener(this);
        this.add(burndownGraph);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(BURNDOWN_GRAPH)) {
            MainView.getInstance().setContentPane(new BurndownGraphView());
        }
    }
}
