package views.events;

import models.Task;
import views.kanban.ColumnView;
import views.MainView;

import javax.swing.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

/**
 * Created by fernando on 04/04/15.
 */
public class DropTargetListener extends DropTargetAdapter {
    private DropTarget dropTarget;
    private JPanel panel;

    public DropTargetListener(JPanel panel) {
        this.panel = panel;
        dropTarget = new DropTarget(panel, DnDConstants.ACTION_MOVE, this, true, null);
    }

    public void drop(DropTargetDropEvent event) {
        try {
            Transferable tr = event.getTransferable();
            Task task= (Task) tr.getTransferData(TransferableTask.taskFlavor);
            if (event.isDataFlavorSupported(TransferableTask.taskFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_MOVE);
                DropTarget columnTarget = (DropTarget) event.getSource();
                ColumnView columnView = (ColumnView) columnTarget.getComponent();
                task.setStatus(columnView.getStatus());
                event.dropComplete(true);
                if (task.update()) {
                    // Update the Kanban view
                    MainView.getInstance().repaint();
                }
                return;
            }
            event.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
        }
    }
}
