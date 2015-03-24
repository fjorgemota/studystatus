package views;

import models.Status;
import models.Task;
import repositories.TaskRepository;
import services.StatusTransformer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by fernando on 21/03/15.
 */
public class ColumnView extends RenderableView {
    private final int CARD_HEIGHT = 120;
    private final int HORIZONTAL_MARGIN = 5;
    private Status statusFilter;
    private TaskRepository repository;

    public ColumnView(Status statusFilter, TaskRepository repository) {
        super();
        this.statusFilter = statusFilter;
        this.repository = repository;
    }

    public ColumnView(Status statusFilter) {
        this(statusFilter, new TaskRepository());
    }

    public void render(){
        this.removeAll();
        this.setLayout(new GridBagLayout());
        GridBagConstraints cconstraints = new GridBagConstraints();
        cconstraints.gridx = 0;
        cconstraints.anchor = GridBagConstraints.NORTHWEST;
        cconstraints.gridwidth = 1;
        cconstraints.fill = GridBagConstraints.BOTH;
        JLabel title = new JLabel();
        title.setText(StatusTransformer.statusToString(this.statusFilter));
        title.setLocation(HORIZONTAL_MARGIN, 10);
        title.setPreferredSize(new Dimension(this.getWidth() - (HORIZONTAL_MARGIN * 2), 25));
        cconstraints.gridy = 0;
        this.add(title, cconstraints);
        JPanel contentView = new JPanel();
        contentView.setLayout(new GridBagLayout());
        JScrollPane pane = new JScrollPane(
                contentView,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setLocation(HORIZONTAL_MARGIN, 30);
        pane.setPreferredSize(new Dimension(this.getWidth() - (HORIZONTAL_MARGIN * 2), this.getHeight() - 40));
        ArrayList<Task> tasks = this.repository.findByStatus(this.statusFilter);
        int count = 1;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 1;
        for(Task task: tasks) {
            TaskView view = new TaskView(task);
            view.setBorder(new LineBorder(Color.BLACK));
            view.setPreferredSize(new Dimension(this.getWidth() - (HORIZONTAL_MARGIN * 2), CARD_HEIGHT));
            constraints.gridy = count;
            contentView.add(view, constraints);
            count++;
        }
        cconstraints.gridy = 1;
        this.add(pane, cconstraints);
        this.revalidate();
        this.repaint();
    }
}

