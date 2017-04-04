package demo;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class FileLoader1 extends Thread
{
	StringBuffer data;
	BufferedReader in;

	public FileLoader1(BufferedReader in, StringBuffer data)
	{
		setPriority(4);
		this.in = in;
		this.data = data;
		start();
		try
		{
			join();
		}catch(java.lang.InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			// try to start reading
			char[] buff = new char[4096];
			int nch;
			int i = 1;
			while ((nch = in.read(buff, 0, buff.length)) != -1)
			{
				data.append(new String(buff, 0, nch));
			}

		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
	}
}
