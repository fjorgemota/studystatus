package views.forms;

import models.Status;
import models.Task;
import services.StatusTransformer;
import views.kanban.KanbanView;
import views.MainView;
import views.RenderableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by aluno on 18/03/15.
 */
public class EditTaskView extends RenderableView implements ActionListener {
    private final String SAVE = "SAVE";
    private final String CANCEL = "CANCEL";
    private final String DELETE = "DELETE";
    private Task task;

    protected JTextField titleField;
    protected JScrollPane descriptionField;
    protected JComboBox statusField;
    protected boolean rendered = false;

    public EditTaskView(Task task) {
        super();
        this.task = task;
    }

    public void render() {
        if (this.rendered) {
            this.revalidate();
            this.repaint();
            return;
        }
        this.removeAll();
        this.setLayout(null);
        this.renderTitle();
        this.renderDescription();
        this.renderStatus();
        this.renderSubmit();
        this.renderCancel();
        this.renderDelete();
        this.revalidate();
        this.repaint();
        this.rendered = true;
    }

    protected void renderTitle() {
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(10, 10, 100, 30);
        this.titleField = new JTextField();
        this.titleField.setBounds(120, 10, 300, 30);
        this.titleField.setText(this.task.getTitle());
        this.add(titleLabel);
        this.add(this.titleField);
    }

    protected void renderDescription() {
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 50, 100, 30);
        JTextArea textArea = new JTextArea(5, 60);
        textArea.setText(this.task.getDescription());
        this.descriptionField = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.descriptionField.setBounds(120, 50, 300, 100);
        this.add(descriptionLabel);
        this.add(this.descriptionField);
    }

    protected void renderStatus() {
        //Campo Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(10, 160, 100, 30);

        ArrayList<Status> statuses = new ArrayList<Status>();
        statuses.add(Status.NOT_STARTED);
        statuses.add(Status.DOING);
        statuses.add(Status.DONE);
        statuses.add(Status.STOPPED);
        this.statusField = new JComboBox<String>();
        for(Status status: statuses) {
            this.statusField.addItem(StatusTransformer.statusToString(status));
        }
        this.statusField.setBounds(120, 160, 300, 30);
        this.statusField.setSelectedItem(StatusTransformer.statusToString(this.task.getStatus()));
        this.add(statusLabel);
        this.add(this.statusField);
    }

    protected void renderSubmit() {
        JButton button = new JButton("Save");
        button.setLocation(10, 200);
        button.setSize(new Dimension(100, 30));
        button.setActionCommand(SAVE);
        button.addActionListener(this);
        this.add(button);
    }

    protected void renderCancel() {
        JButton button = new JButton("Cancel");
        button.setLocation(120, 200);
        button.setSize(new Dimension(100, 30));
        button.setActionCommand(CANCEL);
        button.addActionListener(this);
        this.add(button);
    }

    protected void renderDelete() {
        JButton button = new JButton("Delete");
        button.setLocation(240, 200);
        button.setSize(new Dimension(100, 30));
        button.setActionCommand(DELETE);
        button.addActionListener(this);
        this.add(button);
    }

    protected Status getStatus() {
        return StatusTransformer.intToStatus(this.statusField.getSelectedIndex());
    }

    protected String getTitle(){
        return this.titleField.getText();
    }

    protected String getDescription(){
        return ((JTextArea) this.descriptionField.getViewport().getView()).getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(SAVE)) {
            this.task.setTitle(this.getTitle());
            this.task.setDescription(this.getDescription());
            this.task.setStatus(this.getStatus());
            if (this.task.update()) {
                JOptionPane.showMessageDialog(null, "Task '"+this.getTitle()+"' updated successfully! :D");
                MainView.getInstance().setContentPane(new KanbanView());
            } else {
                JOptionPane.showMessageDialog(null, "There is an error in the update of the task '"+this.getTitle()+"'! :(");
            }
        } else if (command.equals(CANCEL)) {
            MainView.getInstance().setContentPane(new KanbanView());
        } else if (command.equals(DELETE)) {
            if(JOptionPane.showConfirmDialog(null, "Do you really want to delete this task?") == JOptionPane.OK_OPTION) {
                if (this.task.delete()) {
                    JOptionPane.showMessageDialog(null, "Task '"+this.getTitle()+"' deleted successfully! :D");
                    MainView.getInstance().setContentPane(new KanbanView());
                } else {
                    JOptionPane.showMessageDialog(null, "There is an error in the delete of the task '"+this.getTitle()+"'! :(");
                }
            }

        }
    }
}
