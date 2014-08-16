package logic;

import gui.DrawingPane;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.SwingUtilities;
import utils.Interval;
import utils.Interval2D;
import utils.QuadTree;

public class LSystem
{
	int steps;
	static String output;

	/**
	 * 
	 * @param args
	 *            0 = rule 1 = iterations 2 = start
	 */
	public static void main(String[] args)
	{
		String[] test = { "X>X+YF+:Y>-FX-Y", "10", "FX" };
		if(args.length == 0)
		{
			args = test;
		}
		
		int steps = Integer.parseInt(args[1]);
		output = args[2];
		String[] rules = args[0].split(":");
		HashMap<String, String> lSystemsRule = new HashMap<String, String>();

		for (String rule : rules)
		{
			String[] parts = rule.split(">");
			lSystemsRule.put(parts[0], parts[1]);
		}

		output = (generate(args[2], steps, lSystemsRule));
		
		final ArrayList<Point> points = getBoundaryPoints(convertToPoints(90, output));
		System.out.println(output);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DrawingPane(points);
			}
		});

	}
	
	/**
	 * method to totally remove points that have duplicates, not just the duplicates themselves
	 * 
	 * @param input
	 * @return
	 */
	public static ArrayList<Point> getBoundaryPoints(ArrayList<double[]> input)
	{
		//store confirmed duplicates here
		HashSet<Integer> duplicates = new HashSet<Integer>();
		
		//store possible singles here
		HashMap<Integer, double[]> singles = new HashMap<Integer, double[]>();
		
		//list to store confirmed singles and output
		ArrayList<Point> output = new ArrayList<Point>();

		//sort into Hashmaps and hashsets to make contains operations possible, while testing for duplicates
		for(double[] point : input)
		{
			//get a hash from a rounded x and y coord
			Integer hash =  (( (int)Math.round(point[1]) << 16 ) ^ (int)Math.round(point[0]));
			
			//test if we already contain this point, add it to confirmed duplicates if we do and dont bother adding it to possible singles
			if(singles.containsKey(hash))
			{
				duplicates.add(hash);
			}
			else
			{
				singles.put(hash, point);
			}
		}
		
		//test to see if possible singles are actual singles and add them to the output.
		for(Integer hash : singles.keySet())
		{
			if(!duplicates.contains(hash))
			{
				double[] point = singles.get(hash);
				output.add(new Point((int) (Math.round(point[0])),(int)Math.round(point[1])));
			}
		}
		
		return output;
	}
	
	public static ArrayList<double[]> convertToPoints(double angle, String system)
	{
		ArrayList<double[]> output = new ArrayList<double[]>();
		double[] currentState = {angle, 0, 0};
		ArrayDeque<double[]> state = new ArrayDeque<double[]>();

		for (Character ch : system.toCharArray())
		{
			double motion = 1;

			if (ch == 'F')
			{
				currentState[1] -= (Math.cos(Math.toRadians(currentState[0])) * motion);
				currentState[2] -= (Math.sin(Math.toRadians(currentState[0])) * motion);
				output.add(new double[]{currentState[1],currentState[2]});
				
			}
			if (ch == '[')
			{

				state.push(currentState.clone());
			}
			if (ch == '-')
			{
				currentState = new double[] { (double) ((currentState[0] - angle) % 360), currentState[1], currentState[2] };
			}
			if (ch == '+')
			{
				currentState[0] = ((currentState[0] + angle) % 360);

			}
			if (ch == ']')
			{
				currentState = state.pop();
			}
		}
		return output;
		
	}
	
	//order the points in the proper order to create a boundary path
	public ArrayList<Point> getBoundaryPath(ArrayList<Point> points)
	{
		//array to fill with ordered points
		ArrayList<Point> output = new ArrayList<Point>();
		
		//create and populate quadtree for searching
		QuadTree<Integer, Point> tree = new QuadTree<Integer, Point>();
		for(Point point : points)
		{
			tree.insert(point.x, point.y, point);
		}
		
		//Iteratively find the points along the boundary
		Point startPoint = new Point(0,0);
		Point currentPoint = startPoint;
		Point previousPoint = null;
		HashSet<Point> exclude = new HashSet<Point>();
		
		while(currentPoint != startPoint)
		{
			previousPoint = currentPoint;

			currentPoint = findNextPoint(currentPoint, tree, exclude).get(0);
		}
		
		for(Point)
		
		return points;
	}
	
	
	public ArrayList<Point> findNextPoint(Point origin, QuadTree<Integer, Point> points, HashSet<Point> exclude)
	{
		ArrayList<Point> pointsInRange = new ArrayList<Point>();
		
		for(Point point : points.query2D(new Interval2D<Integer>(new Interval(1, 1), new Interval(1, 1))))
		{
			if(!point.equals(origin) && exclude!=null && !exclude.contains(point))
			{
				pointsInRange.add(point);
			}
		}
		
		return pointsInRange;
	}

	public static String generate(String start, int steps, HashMap<String, String> lSystemsRule)
	{
		
		while (steps > 0)
		{
			StringBuilder output = new StringBuilder();

			for (Character ch : start.toCharArray())
			{
				String data = lSystemsRule.get(ch.toString());

				// handle constants
				if (data == null)
				{
					data = ch.toString();
				}
				output.append(data);

			}
			steps--;
			start = output.toString();

		}

		return start;
	}

}
