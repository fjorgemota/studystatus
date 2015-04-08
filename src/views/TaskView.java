package views;

import models.Task;
import views.forms.EditTaskView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TaskView extends RenderableView implements MouseListener {
    private final int HORIZONTAL_MARGIN = 5;
    private Task task;
    public TaskView(Task task) {
        this.task = task;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MainView.getInstance().setContentPane(new EditTaskView(this.task));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Task getTask() {
        return this.task;
    }

    public void render() {
        this.removeAll();
        this.addMouseListener(this);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        GroupLayout.SequentialGroup seqv = layout.createSequentialGroup();
        GroupLayout.ParallelGroup seqh = layout.createParallelGroup();
        JLabel title = new JLabel();
        String ts = this.task.getTitle();
        title.setText((ts.length() > 17) ? ts.substring(0, 17)+"..." : ts);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24));

        title.setBounds(HORIZONTAL_MARGIN, 5, this.getWidth() - (HORIZONTAL_MARGIN * 2), 30);
        //constraints.gridy = 0;
        seqv.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE);
        seqh.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE);
        JLabel description = new JLabel();
        String ds = this.task.getDescription();
        description.setText("<html><body style='width: 350px;'>" +((ds.length() > 120) ? ds.substring(0, 120)+"...": ds)+"</body></html>");
        description.setFont(description.getFont().deriveFont(Font.BOLD, 14));
        description.setBounds(HORIZONTAL_MARGIN, 25, this.getWidth()-(HORIZONTAL_MARGIN*2), 30);
        //constraints.gridy = 1;
        seqv.addComponent(description, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE);
        seqh.addComponent(description, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE);
        layout.setVerticalGroup(seqv);
        layout.setHorizontalGroup(seqh);
    }
}
