package analyser;

import java.util.*;

/**
 * The class Method contains methods for analysing the input string
 */
public class Method
{
	static String x = "";
	static String y = "";
	static TabConverter TC;
	static ADictionary dictionary;
	static Stack originalStack;
	static Stack originalStack1;

	/**
	 * Checks whether the input is a root word or falls under noun inflection
	 * or falls under verb inflection or contains any prefix
	 */
	public static int analyse(String inputString, Stack allStk)
	{
try
{
		//System.out.println(x + "Word: " + inputString);
		int c = 0;

		if(NounMisc.checkNumber(allStk,inputString))
			return c;

		//root word
		boolean isVerb = ADictionary.check(allStk,inputString);
		c = 17;

		//prefix
		Noun.checkPrefix(allStk,inputString);

		//noun analysis
		Noun.check(allStk,inputString);

		if(ADictionary.adjectivalNoun(allStk,inputString))
			return c;

		//mARRu
		if(isVerb)
		{
			return c;
		}

		//verb analysis
		Verb.check(allStk,inputString);

		return c;
}catch(Exception ex)
{
	String xxx = "";
	System.out.println(xxx + "Error while analysing input string: " + inputString);
	//ex.printStackTrace();
	return 0;
}

	}
}
