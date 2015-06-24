package views.report;

import models.TaskStat;
import repositories.TaskStatsRepository;
import views.RenderableView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BurndownGraphView extends RenderableView {
	private TaskStatsRepository repository;

	public BurndownGraphView() {
		super();
		this.repository = new TaskStatsRepository();
	}

    @Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        ArrayList<TaskStat> stats = this.repository.getNotDoneByDay();
        int max = 0;
        ArrayList<Integer> counts = new ArrayList<Integer>();
        for (int i=0; i<stats.size(); i++) {
            if (!counts.contains(stats.get(i).getCount())) {
                counts.add(stats.get(i).getCount());
            }
            if (max < stats.get(i).getCount()) {
                max = stats.get(i).getCount();
            }
        }
        Collections.sort(counts);
        int height = (this.getHeight()-50)/max;

        if (height == 0) {
            height = 1;
        }
        g.drawLine(50, 30, 50, (max*height)+30);

        for (int i=0; i < counts.size(); i++) {
            g.drawString(""+counts.get(i), 20, (this.getHeight()-30)-(counts.get(i)*height));
        }
        int width = this.getWidth();
        if (width < 100) {
            return;
        }
        width = (width-100)/(stats.size()-1);
        for (int i=1; i < stats.size(); i++) {
            int y1 = (this.getHeight()-30)-(stats.get(i-1).getCount()*height);
            int y2 = (this.getHeight()-30)-(stats.get(i).getCount()*height);

            g.drawLine(50+((i- 1) * width), y1, 50 + (i * width), y2);
            g.fillOval(50 +((i-1)*width), y1-5, 10, 10);
            g.drawString(stats.get(i-1).getText(), ((i-1)*width)+10, getHeight()-15);

            g.fillOval(50+(i*width), y2-5, 10, 10);
            g.drawString(stats.get(i).getText(), (i*width)+10, getHeight()-15);
        }
	}

	public void render() {
		this.removeAll();
        this.revalidate();
        this.repaint();
	}
}