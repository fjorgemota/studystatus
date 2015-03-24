package views;

import models.Status;
import models.Task;
import services.StatusTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aluno on 18/03/15.
 */
public class AddTaskView extends RenderableView implements ActionListener {
    private final String SAVE = "SAVE";
    private final String CANCEL = "CANCEL";

    protected JTextField titleField;
    protected JScrollPane descriptionField;
    protected JComboBox statusField;
    protected boolean rendered = false;

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
        this.revalidate();
        this.repaint();
        this.rendered = true;
    }

    protected void renderTitle() {
        //Campo Titulo
        JLabel titleLabel = new JLabel("Titulo:");
        titleLabel.setBounds(10, 10, 100, 30);
        this.titleField = new JTextField();
        this.titleField.setBounds(120, 10, 300, 30);
        this.add(titleLabel);
        this.add(this.titleField);
    }

    protected void renderDescription() {
        //Campo Descrição
        JLabel descriptionLabel = new JLabel("Descrição:");
        descriptionLabel.setBounds(10, 50, 100, 30);
        this.descriptionField = new JScrollPane(
                new JTextArea(),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.descriptionField.setBounds(120, 50, 300, 30);
        this.add(descriptionLabel);
        this.add(this.descriptionField);
    }

    protected void renderStatus() {
        //Campo Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(10, 90, 100, 30);
        this.statusField = new JComboBox<String>(new String[]{"Não iniciado", "Em progresso", "Concluido", "Bloqueado"});
        this.statusField.setBounds(120, 90, 300, 30);
        this.add(statusLabel);
        this.add(this.statusField);
    }

    protected void renderSubmit() {
        JButton button = new JButton("Salvar");
        button.setLocation(10, 130);
        button.setSize(new Dimension(100, 30));
        button.setActionCommand(SAVE);
        button.addActionListener(this);
        this.add(button);
    }

    protected void renderCancel() {
        JButton button = new JButton("Cancelar");
        button.setLocation(120, 130);
        button.setSize(new Dimension(100, 30));
        button.setActionCommand(CANCEL);
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

    protected Task getTask(){
        Task task = new Task();
        task.setTitle(this.getTitle());
        task.setDescription(this.getDescription());
        task.setStatus(this.getStatus());

        return task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(SAVE)) {
            Task task = this.getTask();
            if (task.insert()) {
                JOptionPane.showMessageDialog(null, "Tarefa '"+this.getTitle()+"' inserida com sucesso! :D");
                MainView.getInstance().setContentPane(new KanbanView());
            } else {
                JOptionPane.showMessageDialog(null, "Houve um erro durante a inserção da tarefa '"+this.getTitle()+"'! :(");
            }
        } else if (command.equals(CANCEL)) {
            MainView.getInstance().setContentPane(new KanbanView());
        }
    }
}
