package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by fernando on 22/03/15.
 */
public abstract class RenderableView extends JPanel implements ComponentListener {
    public RenderableView() {
        super();
        this.setLayout(new BorderLayout());
        this.addComponentListener(this);
    }
    public abstract void render();

    public void componentResized(ComponentEvent e){
        this.render();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}