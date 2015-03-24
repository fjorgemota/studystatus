package views;

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
import models.Task;

import javax.swing.*;

public class TaskView extends RenderableView {
    private final int HORIZONTAL_MARGIN = 5;
    Task task;
    public TaskView(Task task) {
        this.task = task;
    }

    public void render() {
        this.removeAll();
        JLabel title = new JLabel();
        title.setText(this.task.getTitle());
        title.setBounds(HORIZONTAL_MARGIN, 5, this.getWidth()-(HORIZONTAL_MARGIN*2), 20);
        this.add(title);
        JLabel description = new JLabel();
        String ds = this.task.getDescription();
        description.setText((ds.length() > 30) ? ds.substring(0, 30)+"...": ds);
        title.setBounds(HORIZONTAL_MARGIN, 25, this.getWidth()-(HORIZONTAL_MARGIN*2), 30);
        this.add(description);
    }
}
