package views;

import models.Status;
import models.Task;
import repositories.TaskRepository;
import services.StatusTransformer;

import javax.swing.*;
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
        JLabel title = new JLabel();
        title.setText(StatusTransformer.statusToString(this.statusFilter));
        title.setBounds(HORIZONTAL_MARGIN, 10, this.getWidth() - (HORIZONTAL_MARGIN * 2), 25);
        this.add(title);
        JScrollPane pane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setBounds(HORIZONTAL_MARGIN, 30, this.getWidth()-(HORIZONTAL_MARGIN*2), this.getHeight()-40);
        ArrayList<Task> tasks = this.repository.findByStatus(this.statusFilter);
        int count = 0;
        for(Task task: tasks) {
            TaskView view = new TaskView(task);
            view.setBounds(
                    0,
                    (CARD_HEIGHT*count)+(5*count),
                    pane.getWidth(),
                    CARD_HEIGHT
            );
            pane.add(view);
            count += 1;
        }
        this.add(pane);
    }
}

