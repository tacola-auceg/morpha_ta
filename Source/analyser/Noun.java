package analyser;

import java.util.*;

import demo.*;

/**
 * The class contains methods to check whether the input falls
 * under noun inflection
 */
public class Noun
{
	static String x = "";
	static TabConverter TC;
	static int c = 0;

	/**
	 * Checks whether the input falls under noun inflection
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	public static boolean check(Stack allStk, String inputString)
	{
		//System.out.println(x + "NOUN ANALYSER");
		boolean isNoun = false;

		if(rule_a(allStk,inputString))
			isNoun = true;

		if(rule_c1(allStk,inputString))
			isNoun = true;
		if(rule_c(allStk,inputString))
			isNoun = true;

		if(rule_b(allStk,inputString))
			isNoun = true;
		if(rule_e(allStk,inputString))
			isNoun = true;
		if(rule_d(allStk,inputString))
			isNoun = true;

		return isNoun;
	}

	/**
	 * Checks whether the input falls under rule a -
	 * Noun/PronounClitic/InterrogativeNoun+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule a <Noun/PronounClitic/InterrogativeNoun+Clitic>
	public static boolean rule_a(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE a");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		if(Clitic.check(s))
		{
			c++;
			if(ADictionary.noun_PronounClitic_InterrogativeNoun(s))
			{
				allStk.push(s);
				return true;
			}
			if(Sandhi.handleException(s))
			{
				c++;
				if(ADictionary.noun_PronounClitic_InterrogativeNoun(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the input falls under rule b -
	 * Postposition/Adverb+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule b <Postposition/Adverb+Clitic>
	public static boolean rule_b(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE b");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		if(Clitic.check(s))
		{
			c++;
			if(ADictionary.postposition(s))
			{
				allStk.push(s);
				return true;
			}
			c++;
			if(ADictionary.adverb(s))
			{
				allStk.push(s);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the input falls under rule c -
	 * Noun/Noun+Plural/PronounCase/InterrogativeNoun+Case+Postposition+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule c <Noun/Noun+Plural/PronounCase/InterrogativeNoun+Case+Postposition+Clitic>
	public static boolean rule_c(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE c");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		boolean anySuffixFound = false,
			hasSth = false;

		if(Clitic.check(s))
			hasSth = true;

		if(Postposition.check(s))
			hasSth = true;

		Stack originalStack = (Stack)s.clone();
		if(Case.check(s,false))
			anySuffixFound = true;

		if(anySuffixFound)
		{
			c++;
			if(ADictionary.noun_PronounCase_InterrogativeNoun(s))
			{
				allStk.push(s);
				return true;
			}
			if(Sandhi.handleException(s))
			{
				c++;
				if(ADictionary.noun_PronounCase_InterrogativeNoun(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		if(anySuffixFound && SingPluralMarker.check(s))
		{
			c++;
			if(ADictionary.noun(s))
			{
				allStk.push(s);
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks whether the input falls under rule c1 -
	 * Noun/Noun+Plural/PronounCase/InterrogativeNoun+Postposition+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule c1 <Noun/Noun+Plural/PronounCase/InterrogativeNoun+Postposition+Clitic>
	public static boolean rule_c1(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE c1");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		boolean anySuffixFound = false;

		if(Clitic.check(s));
			//anySuffixFound = true;

		if(Postposition.check(s))
			anySuffixFound = true;

		if(anySuffixFound)
		{
			c++;
			if(ADictionary.noun_PronounCase_InterrogativeNoun(s))
			{
				allStk.push(s);
				return true;
			}
			if(Sandhi.handleException(s))
			{
				c++;
				if(ADictionary.noun_PronounCase_InterrogativeNoun(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		if(SingPluralMarker.check(s))
		{
			c++;
			if(ADictionary.noun(s))
			{
				allStk.push(s);
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks whether the input falls under rule d -
	 * Noun/angu_ingu_engu+AdjSuffix+Pronominal+Plural+Case+Postposition+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule d <Noun/angu_ingu_engu+AdjSuffix+Pronominal+Plural+Case+Postposition+Clitic>
	public static boolean rule_d(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE d");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		boolean anySuffixFound = false,
			hasSth = false,
			pp = false;

		if(Clitic.check(s))
			hasSth = true;

		if(Postposition.check(s))
			pp = true;

		if(Case.check(s,true))
			hasSth = true;

		if(SingPluralMarker.check(s))
			hasSth = true;

		if(NounMisc.pronominal(s))
			anySuffixFound = true;

		if(NounMisc.adjective(s))
			anySuffixFound = true;

		if(pp)
		{
			if(NounMisc.angu_ingu_engu(s))//angku, ingku, engku
			{
				allStk.push(s);
				return true;
			}
		}
		if(anySuffixFound)
		{
			c++;
			if(ADictionary.noun(s))
			{
				allStk.push(s);
				return true;
			}
			if(NounMisc.angu_ingu_engu(s))//angku, ingku, engku
			{
				allStk.push(s);
				return true;
			}
			if(Sandhi.handleException(s))
			{
				c++;
				if(ADictionary.noun(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		if(SingPluralMarker.check(s))
		{
			c++;
			if(ADictionary.noun(s))
			{
				allStk.push(s);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the input falls under rule e -
	 * Noun/Noun+Plural/PronounClitic/InterrogativeNoun+AdvSuffix/FiniteVerb+Clitic
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 */
	//rule e <Noun/Noun+Plural/PronounClitic/InterrogativeNoun+AdvSuffix/FiniteVerb+Clitic>
	public static boolean rule_e(Stack allStk, String inputString)
	{
		//System.out.println(x + "RULE e");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		boolean anySuffixFound = false,
			hasClitic = false;

		if(Clitic.check(s))
			hasClitic = true;

		if(NounMisc.adverb(s))
			anySuffixFound = true;

		if(NounMisc.finiteVerb(s))
			anySuffixFound = true;

		if(anySuffixFound)
		{
			c++;
			if(ADictionary.noun_PronounClitic_InterrogativeNoun(s))
			{
				allStk.push(s);
				return true;
			}
			if(Sandhi.handleException(s))
			{
				c++;
				if(ADictionary.noun_PronounClitic_InterrogativeNoun(s))
				{
					allStk.push(s);
					return true;
				}
			}
			if(SingPluralMarker.check(s))
			{
				c++;
				if(ADictionary.noun(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the input falls under the prefix rule
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for noun
	 * @see #check
	 */
	public static boolean checkPrefix(Stack allStk, String inputString)
	{
		//System.out.println(x + "Prefix");
		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));

		byte[] inputByte = ((Entry)s.peek()).getPart();
		if(inputByte.length <= 3)
			return false;

		byte[] inputByte1 = null;
		byte[] prefix = BooleanMethod.startsWith_prefix(inputByte);
		if(prefix != null)
		{
			//System.out.println(x + "Prefix: Yes");
			inputByte1 = ByteMeth.subArray(inputByte,2,
				inputByte.length);
			s.pop();
			s.push(new Entry((new byte[]{prefix[1]}),Tag.Sandhi));
			s.push(new Entry((new byte[]{prefix[0]}),Tag.DemonstrativeAdjective));//temp

			//noun inflections
			String nounStr = TabConverter.revert(inputByte1);
			if(check(allStk,nounStr))
			{
				//System.out.println(x + "Prefix + Noun Inflections");
				int size = allStk.size();
				Stack[] nounInfStk = new Stack[size];
				for(int i=0; i<size; i++)
				{
					nounInfStk[i] = (Stack)allStk.pop();
				}

				for(int i=0; i<size; i++)
				{
					Misc.merge(s,nounInfStk[i]);
					allStk.push(nounInfStk[i]);
				}
			}

			//noun
			Stack nounStk = new Stack();
			nounStk.push(new Entry(inputByte1,-1));
			if(ADictionary.noun(nounStk))
			{
				//System.out.println(x + "Prefix + Noun");
				Misc.merge(s,nounStk);
				allStk.push(nounStk);
				return true;
			}
		}
		return false;
	}
}
