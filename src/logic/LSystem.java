package logic;

import gui.DrawingPane;
import java.util.HashMap;
import javax.swing.SwingUtilities;

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
		String[] test = { "X>X+YF:Y>FX-Y", "10", "FX" };
		
		//test = { "X>F-[[X]+X]+F[+FX]-X:F>FF", "4", "X" };
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
		System.out.println(output);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DrawingPane(output);
			}
		});

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
