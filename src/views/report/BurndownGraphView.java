package views.report;

import models.TaskStat;
import repositories.TaskStatsRepository;
import views.RenderableView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BurndownGraphView extends RenderableView {
	private TaskStatsRepository repository;
    private final int OVAL_DIAMETER = 10;

	public BurndownGraphView() {
		super();
		this.repository = new TaskStatsRepository();
	}

    private ArrayList<Integer> getCounts(ArrayList<TaskStat> stats)
    {
        ArrayList<Integer> counts = new ArrayList<Integer>();

        for (int i=0; i<stats.size(); i++) {
            if (!counts.contains(stats.get(i).getCount())) {
                counts.add(stats.get(i).getCount());
            }
        }
        Collections.sort(counts);
        return counts;
    }

    private int getMaxValue(ArrayList<TaskStat> stats) {
        int max = 1;
        for (int i=0; i<stats.size(); i++) {
            if (max < stats.get(i).getCount()) {
                max = stats.get(i).getCount();
            }
        }
        return max;
    }

    private int getDistanceByYPoint(ArrayList<TaskStat> stats)
    {
        int max = this.getMaxValue(stats);
        int height = (this.getHeight()-50)/max;

        if (height == 0) {
            height = 1;
        }
        return height;
    }

    private int getDistanceByXPoint(ArrayList<TaskStat> stats)
    {
        int width = this.getWidth();
        if (width < 100) {
            return 100;
        }
        width = (width-100)/(stats.size()-1);
        return width;
    }

    private void clearScreen(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
    }

    private void drawCountsVertically(Graphics g, ArrayList<TaskStat> stats)
    {
        int max = this.getMaxValue(stats);
        int height = this.getDistanceByYPoint(stats);
        ArrayList<Integer> counts = this.getCounts(stats);
        g.drawLine(50, 30, 50, (max * height) + 30);

        for (int i=0; i < counts.size(); i++) {
            g.drawString(""+counts.get(i), 20, (this.getHeight()-30)-(counts.get(i)*height));
        }
    }

    private int getYAtScale(int count, int distanceY)
    {
        return (this.getHeight()-30)-(count*distanceY);
    }

    private int getXAtScale(int i, int distanceX)
    {
        return 50+(i * distanceX);
    }

    @Override
	public void paint(Graphics g) {
        this.clearScreen(g);
        ArrayList<TaskStat> stats = this.repository.getNotDoneByDay();
        int distanceY = this.getDistanceByYPoint(stats);
        int distanceX = this.getDistanceByXPoint(stats);
        this.drawCountsVertically(g, stats);
        for (int i=1; i < stats.size(); i++) {
            int y1 = this.getYAtScale(stats.get(i-1).getCount(), distanceY);
            int y2 = this.getYAtScale(stats.get(i).getCount(), distanceY);
            int x1 = this.getXAtScale(i - 1, distanceX);
            int x2 = this.getXAtScale(i, distanceX);

            g.drawLine(x1, y1, x2, y2);
            g.fillOval(x1, y1 - 5, OVAL_DIAMETER, OVAL_DIAMETER);
            g.drawString(stats.get(i-1).getText(), x1-40, getHeight()-15);

            g.fillOval(x2, y2-5, OVAL_DIAMETER, OVAL_DIAMETER);
            g.drawString(stats.get(i).getText(), x2-40, getHeight()-15);
        }
	}

	public void render() {
		this.removeAll();
        this.revalidate();
        this.repaint();
	}
}