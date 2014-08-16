package gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPane extends JFrame implements ActionListener
{
	public DrawingPane(ArrayList<Point> points)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new DrawingPanel(points));
		this.pack();
		this.setVisible(true);
		new Timer(5, this).start();
	}

	class DrawingPanel extends JPanel
	{
		Random rand = new Random(System.nanoTime());
		ArrayList<Point> points;

		DrawingPanel(ArrayList<Point> points)
		{
			this.points = points;
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			doUpdate(points, (Graphics2D) g);
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(800, 600);
		}

		public void doUpdate(ArrayList<Point> points, Graphics2D g)
		{
			g.setStroke(new BasicStroke(2));
			double[] startPos = {this.getSize().width / 2, (int) (this.getSize().height * .6) };
			int multifactor = 10;
		
			for(int i = 0; i+1< points.size();i++)
			{

				drawLine(points.get(i).x*multifactor+startPos[0], points.get(i).y*multifactor+startPos[1],points.get(i).x*multifactor+startPos[0], points.get(i).y*multifactor+startPos[1],g);
			}

			
		}
		
		public void drawOutline()
		{
			
		}

		public double[] getRandomWalk()
		{
			float time = (float) (((System.currentTimeMillis() + 0xF1234568) % 200000 + rand.nextGaussian()) / (500.0F));
			double xJitter = Math.cos(1.1f * time) * Math.cos(0.8f * time);
			double yJitter = Math.sin(1.2f * time) * Math.cos(0.9f * time);
			return new double[] { 1, 1 };
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

	public void drawLine(double x1, double y1, double x2, double y2, Graphics2D g)
	{
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.draw(line);

	}

}
