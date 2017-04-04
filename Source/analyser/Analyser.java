package analyser;

import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The class Analyser contains methods for analysing the given word.
 */
public class Analyser
{
	public static javax.swing.JTextArea stackTextArea = new javax.swing.JTextArea();

	public static ResourceBundle Tags = ResourceBundle.getBundle("analyser/Tag/" + Tag.ENGLISH_EXPAND);

	/**
	 * Returns the analysed output of the given input word.
	 * @param input input word
	 * @return the analysed output
	 * @see Method#analyse
	 */
	public static String analyseF(String input)
	{
		String output = input + ":";
try
{
		Stack allStk = new Stack();

		Method.analyse(input,allStk);
		int size = allStk.size();
		if(size == 0)
		{
			output += " Unknown";
			return output;
		}
		for(int i=0;i<size;i++)
		{
			Stack outputStack = (Stack)allStk.get(i);
			while (!outputStack.empty())
			{
				Entry entry = (Entry)outputStack.pop();
				String s = TabConverter.revert(entry.getPart());
				output += "\n";
				output += s;
				int tag = entry.getTag();
				if(tag != -1)
				{
					s = " < ";
					s += Tags.getString(String.valueOf(tag));
					s += " > ";
				}
				else
					s = "";
				output += s;
			}
			if(allStk.size() != 1)
				output += "\n" + "***";
		}
}catch(Exception e)
{
	System.err.println("Analyser analyseF: " + e);
	e.printStackTrace();
	return input;
}
	return output;
	}

	/**
	 * Loads dictionary and the corresponding analyser classes into memory
	 * so that analysing words from large text would become faster later on.
	 * The method should be called first before analysing words from large
	 * text like while spell checking a document
	 */
	public static void init()
	{
		analyseF(TabConverter.revert(Constant.vel));
	}

}