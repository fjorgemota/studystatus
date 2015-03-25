package views;

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
import models.Task;

import javax.swing.*;
import java.awt.*;

public class TaskView extends RenderableView {
    private final int HORIZONTAL_MARGIN = 5;
    Task task;
    public TaskView(Task task) {
        this.task = task;
    }

    public void render() {
        this.removeAll();
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
        description.setText((ds.length() > 28) ? ds.substring(0, 28)+"...": ds);
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
