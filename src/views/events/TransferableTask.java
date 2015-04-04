package views.events;

import models.Task;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Created by fernando on 04/04/15.
 */
public class TransferableTask implements Transferable {
    protected static DataFlavor taskFlavor = new DataFlavor(Task.class, "A Task Model");
    protected static DataFlavor[] supportedFlavors = { taskFlavor };
    Task task;
    public TransferableTask(Task task) {
        this.task = task;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(taskFlavor)) {
            return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(taskFlavor)) {
            return this.task;
        }
        else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
