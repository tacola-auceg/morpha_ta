package analyser;

import java.util.Stack;
import java.util.Vector;

/**
 * The class contains methods Miscellaneous functions like merging two stacks
 */
public class Misc
{
	/**
	 * Copies the content of one stack to another
	 * @param o Stack one
	 * @param s Stack two
	 */
	static void backTrack(Stack o, Stack s)
	{
		while(!s.empty()) s.pop();
		for(int i=0; i < o.size(); i++)
		{
			s.push((Entry)o.get(i));
		}
	}

	/**
	 * Append the content of one stack to another
	 * @param s1 Stack one
	 * @param s2 Stack two
	 */
	static void merge(Stack s1, Stack s2)
	{
		for(int i=0; i < s1.size(); i++)
		{
			s2.push((Entry)s1.get(i));
		}
	}

	/**
	 * Reverses a byte array
	 * @param b byte array to be reversed
	 */
	public static void reverse(byte[] n)
	{
		for(int i=0; i<n.length/2;i++)
		{
			byte t = n[i];
			n[i] = n[n.length-i-1];
			n[n.length-i-1] = t;
		}
	}
}
