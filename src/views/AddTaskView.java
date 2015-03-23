package views;

import models.Status;
import models.Task;
import services.StatusTransformer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aluno on 18/03/15.
 */
public class AddTaskView extends RenderableView implements ActionListener {
    protected JTextField titleField, descriptionField;
    protected JComboBox statusField;

    public void render() {
        this.renderTitle();
        this.renderDescription();
        this.renderStatus();
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
        this.descriptionField = new JTextField();
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

    protected Status getStatus() {
        return StatusTransformer.intToStatus(this.statusField.getSelectedIndex());
    }

    protected String getTitle(){
        return this.titleField.getText();
    }

    protected String getDescription(){
        return this.descriptionField.getText();
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
        if (e.getActionCommand().equals("save")) {
            Task task = this.getTask();
            if (task.insert()) {
                JOptionPane.showMessageDialog(null, "Tarefa '"+this.getTitle()+"' inserida com sucesso! :D");
            } else {
                JOptionPane.showMessageDialog(null, "Houve um erro durante a inserção da tarefa '"+this.getTitle()+"'! :(");
            }
        } else if (e.getActionCommand().equals("cancel")) {
        }
    }
}
