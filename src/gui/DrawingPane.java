package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayDeque;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPane extends JFrame implements ActionListener
{
	public DrawingPane(String system)
	{
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawingPanel drw = new DrawingPanel(system);
        
        Timer timer = new Timer(5, this);
		this.add(new DrawingPanel(system));
		this.pack();
		this.setVisible(true);
		timer.start();

	}
	
	class DrawingPanel extends JPanel
	{
		Random rand = new Random(System.nanoTime());
		protected void paintComponent(Graphics g) 
		{

			super.paintComponent(g);
			doUpdate(system,(Graphics2D) g);
		
		}
		
		String system;
		
		public Dimension getPreferredSize() {
	        return new Dimension(800,600);
	    }
		
		DrawingPanel(String system)
		{
			this.system = system;			
		}
		
		
		public void doUpdate(String system, Graphics2D g)
		{
			g.setStroke(new BasicStroke(2));
			ArrayDeque<double[]> state = new ArrayDeque<double[]>();
			double[] currentState = {90, this.getSize().width/2, (int) (this.getSize().height*.9)};

			for(Character ch : system.toCharArray())
			{
				double motion = 10;
				double[] jitter = this.getRandomWalk();
				
				if(ch=='F')
				{
					double motionX = (Math.cos(Math.toRadians(currentState[0]))*motion);
					double motionY = (Math.sin(Math.toRadians(currentState[0]))*motion);
					
					drawLine(currentState[1], currentState[2], currentState[1]-motionX, currentState[2]-motionY, g);
					
					currentState[1]-=motionX;
					currentState[2]-=motionY;
					
				}
				if(ch=='X')
				{
					
					double motionX =  (Math.cos(Math.toRadians(currentState[0]))*motion);
					double motionY =  (Math.sin(Math.toRadians(currentState[0]))*motion);
					
					
					drawLine(currentState[1], currentState[2], currentState[1]-motionX, currentState[2]-motionY, g);
					
					currentState[1]-=motionX;
					currentState[2]-=motionY;
				}
				if(ch=='[')
				{

					state.push(currentState.clone());
				}
				if(ch=='-')
				{
					currentState = new double[]{(double) ((currentState[0]-25*jitter[1])%360),currentState[1],currentState[2]};

				}
				if(ch=='+')
				{
					currentState[0] =  ((currentState[0]+25*jitter[0])%360);

				}
				if(ch ==']')
				{
					currentState = state.pop();
				}
			} 
		}
		
		public double[] getRandomWalk()
		{
			// Calculate jitter - include entity ID to give Monoliths individual jitters
			float time = (float) (((System.currentTimeMillis() + 0xF1234568) % 200000+rand.nextGaussian()) / (500.0F));
			// We use random constants here on purpose just to get different wave forms
			double xJitter = Math.cos(1.1f * time) * Math.cos(0.8f * time);
			double yJitter = Math.sin(1.2f * time) * Math.cos(0.9f * time);
			double zJitter = Math.sin(1.3f * time) * Math.sin(0.7f * time);
			
			return new double[]{xJitter,yJitter};

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
		g.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);		
		g.draw(line);
		
	}
	
	
	
	
}
