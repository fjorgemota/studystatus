package views.kanban;

import models.Status;
import models.Task;
import repositories.TaskRepository;
import services.StatusTransformer;
import views.RenderableView;
import views.events.DropTargetListener;
import views.events.TransferableTask;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.util.ArrayList;

/**
 * Created by fernando on 21/03/15.
 */
public class ColumnView extends RenderableView implements DragGestureListener {
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
        new DropTargetListener(this);



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
            DragSource ds = new DragSource();
            ds.createDefaultDragGestureRecognizer(view, DnDConstants.ACTION_MOVE, this);
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

    public Status getStatus() {
        return this.statusFilter;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        TaskView panel = (TaskView) event.getComponent();

        Task task = panel.getTask();
        if (event.getDragAction() == DnDConstants.ACTION_MOVE) {
            cursor = DragSource.DefaultMoveDrop;
        }
        event.startDrag(cursor, new TransferableTask(task));
    }
}

