package demo;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

public class FileLoader extends Thread
{
	Document doc;
	File f;

	public FileLoader(File f, Document doc)
	{
		setPriority(4);
		this.f = f;
		this.doc = doc;
		start();
	}

	public FileLoader(File f, JTextComponent t)
	{
		setPriority(4);
		this.f = f;
		this.doc = t.getDocument();
		start();
	}

	public void run()
	{
		try
		{
			doc.remove(0,doc.getLength());
			// try to start reading
			Reader in = new FileReader(f);
			char[] buff = new char[4096];
			int nch;
			int i = 1;
			while ((nch = in.read(buff, 0, buff.length)) != -1)
			{
				doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
			}

		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
		catch (BadLocationException e)
		{
			System.err.println(e.getMessage());
		}
	}
}
