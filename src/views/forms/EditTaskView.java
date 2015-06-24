package views.forms;

import models.Status;
import models.Task;
import models.TaskComment;
import repositories.TaskCommentRepository;
import services.StatusTransformer;
import views.kanban.KanbanView;
import views.MainView;
import views.RenderableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditTaskView extends RenderableView implements ActionListener, MouseListener {
    private final String SAVE = "SAVE";
    private final String CANCEL = "CANCEL";
    private final String DELETE = "DELETE";
    private final String COMMENT = "COMMENT";
    private final String DELETE_COMMENT = "DELETE_COMMENT";
    private final String UPDATE_COMMENT = "UPDATE_COMMENT";
    private Task task;
    private TaskComment taskComment;
    private boolean select;

    protected JTextField titleField;
    protected JScrollPane descriptionField;
    protected JComboBox statusField;
    protected JScrollPane commentField;
    protected JScrollPane commentsContainer;
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
        this.renderComment();
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
        textArea.setLineWrap(true);
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

    protected void renderComment() {
        JLabel commentLabel = new JLabel("Comment");
        commentLabel.setBounds(10, 275, 100, 30);
        JTextArea textArea = new JTextArea(2, 60);
        this.commentField = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        textArea.setLineWrap(true);
        this.commentField.setBounds(120, 275, 300, 50);
        this.add(commentLabel);
        this.add(this.commentField);

        JButton button = new JButton("Comment");
        button.setBounds(450, 275, 100, 50);
        button.setActionCommand(COMMENT);
        button.addActionListener(this);
        this.add(button);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(450, 450, 100, 50);
        deleteButton.setActionCommand(DELETE_COMMENT);
        deleteButton.addActionListener(this);
        this.add(deleteButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(450, 500, 100, 50);
        updateButton.setActionCommand(UPDATE_COMMENT);
        updateButton.addActionListener(this);
        this.add(updateButton);

        JLabel commentTitle = new JLabel("Commentaries:");
        commentTitle.setFont(commentTitle.getFont().deriveFont(Font.BOLD, 32));
        commentTitle.setBounds(120, 350, 300, 50);
        this.add(commentTitle);

        final JPanel commentsPanel = new JPanel();
        this.commentsContainer = new JScrollPane(
                commentsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.commentsContainer.setBounds(120, 400, 300, 200);
        GroupLayout layout = new GroupLayout(commentsPanel);
        commentsPanel.setLayout(layout);
        GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
        GroupLayout.ParallelGroup vertical = layout.createParallelGroup();
        layout.setHorizontalGroup(vertical);
        layout.setVerticalGroup(horizontal);

        TaskCommentRepository repository = new TaskCommentRepository();
        ArrayList<TaskComment> comments = repository.findByTask(this.task);

        for(TaskComment comment: comments) {
            JPanel commentPanel = new JPanel();
            commentPanel.setSize(300, 200);
            JTextArea commentText = new JTextArea(comment.getComment());
            commentText.setLocation(5, 5);
            commentText.setMaximumSize(new Dimension(300, 180));
            commentText.setOpaque(false);
            commentText.setBorder(null);
            commentText.setLineWrap(true);

            JLabel commentId = new JLabel("#"+comment.getId());
            commentId.setLocation(5, 5);
            commentId.setSize(20, 10);
            commentPanel.add(commentText);
            commentPanel.add(commentId);
            vertical.addComponent(commentPanel);
            horizontal.addComponent(commentPanel);
        }
        this.add(this.commentsContainer);
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


    protected String getComment(){
        return ((JTextArea) this.commentField.getViewport().getView()).getText();
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
        } else if (command.equals(COMMENT)) {
            TaskComment comment = new TaskComment();
            comment.setTaskId(this.task.getId());
            comment.setComment(this.getComment());
            if (comment.insert()) {
                JOptionPane.showMessageDialog(null, "Comment inserted successfully! :D");
                MainView.getInstance().setContentPane(new EditTaskView(this.task));
            } else {
                JOptionPane.showMessageDialog(null, "There is an error in the insertion of the comment! :(");
            }
        }
    }
}
