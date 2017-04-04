package analyser;

import java.util.*;

/**
 * The class contains methods to check whether the input falls
 * under verb inflection
 */
public class Verb
{
	static String x = "";
	static Stack originalStack;
	static Stack originalStack1;
	static TabConverter TC;

	/**
	 * Checks whether the input falls under verb inflection
	 * @param allStk Stack where analysed input/output is stored
	 * @param inputString the input to be analysed for verb
	 */
	public static boolean check(Stack allStk, String inputString)
	{
		//System.out.println(x + "New VERB ANALYSER");

		Stack s = new Stack();
		s.push(new Entry(TC.convert(inputString),-1));
		Sandhi.kctp(s);

		//1a - ISM - tA, ti
		originalStack = (Stack)s.clone();
		if(VerbMisc.imperativeSingular1(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			else
				Misc.backTrack(originalStack,s);
		}

		//1b - ISM - utA, uti
		originalStack = (Stack)s.clone();
		if(VerbMisc.imperativeSingular2(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			else
				Misc.backTrack(originalStack,s);
		}

		//1c - IPM1 - unkaL
		originalStack = (Stack)s.clone();
		if(VerbMisc.imperativePlural1(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			else
				Misc.backTrack(originalStack,s);
		}

		//1d - IPM2 - nkaL
		originalStack = (Stack)s.clone();
		if(VerbMisc.imperativePlural2(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			else
				Misc.backTrack(originalStack,s);
		}

		/*
		//rule not designed preperly/fully
		//2 - Clitic
		if(Clitic.verb(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
		}
		*/

		//3.0a - aux
		if(AuxiliaryVerb.check(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
		}

		//3.0b - PNG
		if(PNG.check(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			Sandhi.verbException(s);
			if(Tense.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			else if(Infinity.mAtt(s))
			{
				if(Infinity.check(s))
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
				if(AuxiliaryVerb.check(s))
				{
					if(ADictionary.verb(s))
					{
						allStk.push(s);
						return true;
					}
				}
			}
		}

		//3 - Finite Verb
		if(VerbMisc.finiteVerb(s))
		{
			originalStack = (Stack)s.clone();
			if(Infinity.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			Misc.backTrack(originalStack,s);
		}

		//5 - umpati
		if(VerbMisc.umpadi(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//6 - Infinitive + endings
		if(Infinity.infinitive_Ends(s))
		{
			if(Infinity.check(s))
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}


		//7 - Past Tense + Cond. Al
		if(VerbMisc.pastTM_Al(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//8 - Infinitive
		if(Infinity.check(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//9 - VP
		if(VerbalParticiple.check(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//11 - RP - Atha
		if(RelativeParticiple.check_Atha(s))
		{
			if(Infinity.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//12 - RP - a (adv par only to a?)
		if(RelativeParticiple.check_a(s))
		{
			if(Tense.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
			if(AuxiliaryVerb.check(s))
			{
				if(ADictionary.verb(s))
				{
					allStk.push(s);
					return true;
				}
			}
		}

		//13 - RP+Pronominal
		if(VerbMisc.pronominal(s))
		{
			if(RelativeParticiple.check_Atha(s))
			{
				if(Infinity.check(s))
				{
					if(ADictionary.verb(s))
					{
						allStk.push(s);
						return true;
					}
				}
			}
			if(RelativeParticiple.check_a(s))
			{
				if(Tense.check(s))
				{
					if(ADictionary.verb(s))
					{
						allStk.push(s);
						return true;
					}
				}
			}
		}

		if(Sandhi.verbException(s))
		{
			if(ADictionary.verb(s))
			{
				allStk.push(s);
				return true;
			}
		}
		return false;
	}
}