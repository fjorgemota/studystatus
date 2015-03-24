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
        GroupLayout columnLayout = new GroupLayout(this);
        this.setLayout(columnLayout);

        GroupLayout.SequentialGroup cseqv = columnLayout.createSequentialGroup();
        GroupLayout.ParallelGroup cseqh = columnLayout.createParallelGroup();


        JLabel title = new JLabel();
        title.setText(StatusTransformer.statusToString(this.statusFilter));
        title.setLocation(HORIZONTAL_MARGIN, 10);
        title.setPreferredSize(new Dimension(this.getWidth() - (HORIZONTAL_MARGIN * 2), 25));

        cseqv.addComponent(title);
        cseqh.addComponent(title);

        JPanel contentView = new JPanel();
        GroupLayout layout = new GroupLayout(contentView);
        contentView.setLayout(layout);
        JScrollPane pane = new JScrollPane(
                contentView,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setLocation(HORIZONTAL_MARGIN, 30);
        pane.setPreferredSize(new Dimension(this.getWidth() - (HORIZONTAL_MARGIN * 2), this.getHeight() - 40));
        ArrayList<Task> tasks = this.repository.findByStatus(this.statusFilter);
        int count = 1;
        GroupLayout.SequentialGroup seqv = layout.createSequentialGroup();
        GroupLayout.ParallelGroup seqh = layout.createParallelGroup();
        for(Task task: tasks) {
            TaskView view = new TaskView(task);
            view.setBorder(new LineBorder(Color.BLACK));
            view.setPreferredSize(new Dimension(this.getWidth(), CARD_HEIGHT));
            seqv.addComponent(view, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE);
            seqh.addComponent(view, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE);
            count++;
        }
        layout.setHorizontalGroup(seqh);
        layout.setVerticalGroup(seqv);
        cseqv.addComponent(pane);
        cseqh.addComponent(pane);
        columnLayout.setVerticalGroup(cseqv);
        columnLayout.setHorizontalGroup(cseqh);
        this.revalidate();
        this.repaint();
    }
}

