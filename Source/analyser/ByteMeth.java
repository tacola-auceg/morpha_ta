package analyser;

/**
 * The class ByteMeth contains methods which operate on
 * byte arrays, for example concatinating two arrays
 */
public class ByteMeth
{
	/**
	 * Compares the two arrays. The result is true if their contents are same
	 * @param b the first array to be compared.
	 * @param c the second array to be compared.
	 * @return true if two arrays contents are same
	 */
	public static boolean isEqual(byte[] b,byte[] c)
	{
		if(b.length != c.length)
			return false;
		for(int i=0;i<b.length;i++)
		{
			if(b[i]==c[i]);
			else
				return false;
		}
		return true;
	}

	/**
	 * Merges two arrays into one
	 * @param b the first array to be merged.
	 * @param c the second array to be merged.
	 * @return the concatination result
	 */
	public static byte[] addArray(byte b[],byte[] c)
	{
		byte a[]= new byte[b.length+c.length];

		System.arraycopy(b,0,a,0,b.length);
		System.arraycopy(c,0,a,b.length,c.length);

		return a;
	}

	/**
	 * Removes the last c chars from b and append r to b
	 * @param b the first array to be modified.
	 * @param c the second array to be appended to first.
	 * @return the concatination result
	 */
	public static byte[] replace(byte b[],byte[] r,int c)
	{
		byte a[]= new byte[b.length-c+r.length];

		System.arraycopy(b,0,a,0,b.length-c);
		System.arraycopy(r,0,a,b.length-c,r.length);

		return a;
	}

	/**
	 * Extracts some portion of an array
	 * @param c the beginning index, inclusive.
	 * @param d the ending index, exclusive.
	 * @return the extracted result
	 */
	public static byte[] subArray(byte[] b,int c,int d)
	{
		byte[] a=new byte[d-c];

		for(int i=c;i<d;i++)
		{
			a[i-c]=b[i];
		}

		return a;
	}

	/**
	 * Checks the first array starts
	 * with the second or not
	 * @param b the first array
	 * @param c the second array
	 * @return true if the first array starts with the second
	 */
	public static boolean startsWith(byte[] b,byte[] c)
	{
		int len1=0,len2=0;
		if(b.length>c.length)
		{
			len1=b.length;
			len2=c.length;
		}
		else
		{
			len1=c.length;
			len2=b.length;
		}
		for(int i=0;i<len2;i++)
			if(b[i]==c[i])
				;
			else
				return false;
		return true;
	}

	/**
	 * Checks the first array ends with the second or not
	 * @param b the first array
	 * @param c the second array
	 * @return true if the first array ends with the second
	 */
	public static boolean endsWith(byte[] b,byte[] c)
	{
		int len1=0,len2=0;
		if(b.length<=c.length)
			return false;
		if(b.length>c.length)
		{
			len1=b.length;
			len2=c.length;
		}
		else
		{
			len1=c.length;
			len2=b.length;
		}
		for(int i=len1-1,j=len2-1;j!=-1 && i!=-1;j--,i--)
			if(b[i]==c[j])
				;
			else
				return false;
		return true;
	}

	/**
	 * Checks whether the an array includes another one
	 * @param b the array which is to be checked
	 * @param c the array
	 * @return true if the first array contains the second
	 */
	public static boolean contains(byte[] b,byte[] c)
	{
		int len1=0,len2=0;
		if(b.length<c.length)
			return false;
		if(b.length>=c.length)
		{
			len1=b.length;
			len2=c.length;
		}
		else
		{
			len1=c.length;
			len2=b.length;
		}
		for(int i=len1-1,j=len2-1;j!=-1 && i!=-1;j--,i--)
			if(b[i]==c[j])
				;
			else
				return false;
		return true;
	}

	/**
	 * Concatinates two arrays, take the result and check whether
	 * another array ends with the this result
	 * @param b the array which is to be checked
	 * @param c the first array to be concatinated
	 * @param d the second array to be concatinated
	 * @return true if the first array ends with the result
	 */
	public static boolean before_endswith(byte[] b,byte[] c,byte[] d)
	{
		if(endsWith(b,addArray(c,d)))
			return true;
		else
			return false;
	}
}
