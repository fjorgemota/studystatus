package views;

import models.Status;
import repositories.TaskRepository;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by fernando on 21/03/15.
 */
public class KanbanView extends RenderableView {
    private final int COLUMN_WIDTH = 300;
    private ArrayList<Status> columns;
    private TaskRepository repository;

    public KanbanView(ArrayList<Status> columns) {
        super();
        this.columns = columns;
        this.repository = new TaskRepository();
    }

    public KanbanView() {
        super();
        this.columns = new ArrayList<Status>();
        this.columns.add(Status.NOT_STARTED);
        this.columns.add(Status.DOING);
        this.columns.add(Status.DONE);
        this.columns.add(Status.STOPPED);
        this.repository = new TaskRepository();
    }

    public void render(){
        this.removeAll();
        JPanel contentView = new JPanel();
        contentView.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(
                contentView,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        int count = 0;
        for(Status status: this.columns) {
            ColumnView view = new ColumnView(status, this.repository);
            view.setBounds((COLUMN_WIDTH*count)+(5*count), 5, COLUMN_WIDTH, this.getHeight());
            view.setLayout(new FlowLayout());
            contentView.add(view);
            view.render();
            count++;
        }
        this.add(pane);
        this.revalidate();
        this.repaint();
    }
}
