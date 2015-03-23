package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fernando on 22/03/15.
 */
public abstract class RenderableView extends JPanel {
    public RenderableView() {
        super();
        this.setLayout(new BorderLayout());
    }
    public abstract void render();
}
