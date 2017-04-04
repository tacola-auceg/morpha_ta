package analyser;

import java.util.*;

/**
 * The class RelativeParticiple contains methods for checking whether the
 * input ends with 'Relative Participle suffix'. If so, removes it from the input
 * and store it in output
 */
public class RelativeParticiple
{
	static String x = "";

	/**
	 * Checks whether the input ends with 'Relative Participle suffix - Atha'
	 * If so, removes it from the input and store it in output
	 * @param s Stack where analysed output is stored
	 */
	public static boolean check_Atha(Stack s)
	{
		//System.out.println(x + "RP Atha");
		byte[] topElmt = ((Entry)s.peek()).getPart();
		byte[] oldTopElmt = topElmt;

		//Atha
		if(ByteMeth.endsWith(topElmt,Constant.Atha))
		{
			//System.out.println(x + "Atha");
			s.pop();
			s.push(new Entry(Constant.a,Tag.RPSuffix));
			s.push(new Entry(Constant.Athu,Tag.FutureNeg));
			topElmt = ByteMeth.subArray(topElmt,0,topElmt.
				length-Constant.Atha.length);
			topElmt = ByteMeth.addArray(topElmt,Constant.a);
			s.push(new Entry(topElmt,-1,oldTopElmt));
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the input ends with 'Relative Participle suffix - a'
	 * If so, removes it from the input and store it in output
	 * @param s Stack where analysed output is stored
	 */
	public static boolean check_a(Stack s)
	{
		//System.out.println(x + "RP - a");
		byte[] topElmt = ((Entry)s.peek()).getPart();
		byte[] oldTopElmt = topElmt;

		//a
		if(ByteMeth.endsWith(topElmt,Constant.a))
		{
			//System.out.println(x + "a");
			s.pop();
			s.push(new Entry(Constant.a,Tag.RPSuffix));
			topElmt = ByteMeth.subArray(topElmt,0,topElmt.
				length-Constant.a.length);
			s.push(new Entry(topElmt,-1,oldTopElmt));
			return true;
		}

		return false;
	}
}
