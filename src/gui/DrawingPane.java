package gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
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
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPane extends JFrame implements ActionListener
{
	public DrawingPane(Iterable<Point> points)
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
		Iterable<Point> points;

		DrawingPanel(Iterable<Point> points2)
		{
			this.points = points2;
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

		public void doUpdate(Iterable<Point> points2, Graphics2D g)
		{
			g.setStroke(new BasicStroke(2));
			double[] startPos = {this.getSize().width / 20, (int) (this.getSize().height * .5) };
			int multifactor = 6;
		
			Iterator<Point> itr = points2.iterator();
			while(itr.hasNext())
			{
				Point p1 = itr.next();
				
				if(!itr.hasNext())
				{
					break;
				}
				Point p2 = itr.next();
				if(!itr.hasNext())
				{
					break;
				}
				Point p3 = itr.next();

				
				g.drawPolygon(new int[]{(int) (p1.x*multifactor+startPos[0]),(int) (p2.x*multifactor+startPos[0]),(int) (p3.x*multifactor+startPos[0])}, new int[]{(int) (p1.y*multifactor+startPos[1]),(int) (p2.y*multifactor+startPos[1]),(int) (p3.y*multifactor+startPos[1])}, 3);
				//drawLine(previous.x*multifactor+startPos[0],previous.y*multifactor+startPos[1],current.x*multifactor+startPos[0],current.y*multifactor+startPos[1],g);
				//previous = current;
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
