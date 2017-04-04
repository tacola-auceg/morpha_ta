package demo;
import analyser.*;

import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.BorderLayout;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Vector;
import java.util.Stack;
import java.util.Set;
import java.util.HashMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;

public class Utils
{
	static HashMap hash;
	static JTextArea textArea = new JTextArea();
	static final JDialog stackDialog = new JDialog(new Frame(), "Output Stack", true);

	public static String newLineStr = "\n";
	public static String newLineStr_OS = System.getProperty("line.separator");

	public static final char[] WORD_SEPARATORS = {' ', '\t', '\n', ';', '?', '_',
		'\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{',
		'}', '<', '>', '/', '|', '\\', '\'', '\"'};

	public static final char[] WORD_SEPARATORS_FOR_DIC = {' ', '\t', '\n', ';', '?', '_',
		'\r', '\f', '.', ',', ':', '(', ')', '[', ']', '{',
		'}', '<', '>', '/', '|', '\\', '\'', '\"'};

	public static final char[] WORD_SEPARATORS_FOR_TAGGER = {' ', '\t', '\n', ';', '?', '_',
		'\r', '\f', '.', ',', ':', '(', ')', '[', ']', '{',
		'}', '/', '|', '\\', '\'', '\"'};

	public final static int ENDS_WITH = 1;
	public final static int STARTS_WITH = 2;
	public final static int ANYWHERE = 3;
	public final static int WHOLE_WORD = 4;

	public static int wordCount = 0;
	public static int searchWordCount = 0;

	public static String getCurrentDir()
	{
		try
		{
			String className = Utils.class.toString();
			int classIndex = className.indexOf("class");
			int interfaceIndex = className.indexOf("interface");

			String fileName = "";
			if(classIndex != -1)
			{
				fileName = className.substring(classIndex+1 +"class".length());
			}
			else if(interfaceIndex != -1)
			{
				fileName = className.substring(interfaceIndex+1 +"interface".length());
			}

			fileName += ".class";

			URL url =  Utils.class.getResource (fileName);

			return url.getFile();

		}catch(Exception e)
		{
			return "";
		}
	}

	//temp
	public static void textComponentReadMethod(File file,JTextComponent t)
	{
		Date startDate = new Date();

		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
			t.read(in, null);
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ex);
			return;
		}

		Date endDate = new Date();
		calculateElapsedTime(startDate,endDate);
	}

	public static long calculateElapsedTime(Date startDate, Date endDate)
	{
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long elapsedMilliSeconds = endTime - startTime;
		long elapsedSeconds = (long)elapsedMilliSeconds
								/(long)(Math.pow(10,6));
		long elapsedMinutes = (long)elapsedSeconds/(long)60;

		String x = "";
		/*System.out.println(x + "Time Elapsed: ");
		System.out.println(x + elapsedMilliSeconds + " ms");
		System.out.println(x + elapsedSeconds + " sec");
		System.out.println(x + elapsedMinutes + " min");*/
		return elapsedMilliSeconds;
	}

	public static Font getFont()
	{
		try
		{
			InputStream in = Utils.class.getResourceAsStream(
				"TABANNA.TTF");

			Font f1 = Font.createFont(Font.TRUETYPE_FONT, in);
			return f1.deriveFont(Font.PLAIN, 14);
		}
		catch(Exception e)
		{
			e.printStackTrace();//showErrorDialog(false,null,e);
			return new Font("Arial",0,12);
		}
	}

	public static Point getMiddle(Dimension dimension)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(screenSize.width-dimension.width)/2;
		int y = (int)(screenSize.height-dimension.height)/2;
		return new Point(x,y);
	}

	public static Point getMiddle(Dimension d1,Dimension d2)
	{
		int x = (int)(d1.width-d2.width)/2;
		int y = (int)(d1.height-d2.height)/2;
		return new Point(x,y);
	}

	public static void sizeScreen(JFrame frame)
	{
		if(System.getProperty("java.version").startsWith("1.4"))
		{
			//get screen size and set it to frame
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();

			//to display the frame above taskbar
			GraphicsConfiguration config = frame.getGraphicsConfiguration();
			Insets insets = kit.getScreenInsets(config);
			screenSize.width -= (insets.left + insets.right);
			screenSize.height -= (insets.top + insets.bottom);
			frame.setLocation(insets.left, insets.top);
			frame.setSize(screenSize);
		}else
		{
			sizeScreen_v3(frame);
		}
	}

	public static void sizeScreen_v3(JFrame f)
	{
		Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(dimScreenSize);
		f.setVisible(true);
		java.awt.Rectangle rectVisible = f.getRootPane().getVisibleRect();
		int iWidth = (int)rectVisible.getWidth();
		int iHeight = (int)rectVisible.getHeight();
		int iX = (int)rectVisible.x;
		int iY = (int)rectVisible.y;
		f.setBounds(iX,iY,iWidth,iHeight);
	}

	public static void setProperty(JFileChooser fc,String fileExtn)
	{
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new GenericFileFilter(fileExtn));
	}

	public static void setProperty(JFileChooser fc,String fileExtn, String fileExtn2)
	{
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(new GenericFileFilter(fileExtn,fileExtn2));
	}

	public static File showFileChooserDialog(
											Component	parent,
											String		approveButtonText,
											String		fileExtn,
											File 		currentFile)
	{
		JFileChooser fc = new JFileChooser();

		setProperty(fc,fileExtn);
		fc.setCurrentDirectory(currentFile);

		int option = fc.showDialog(parent,approveButtonText);
		if(option == JFileChooser.APPROVE_OPTION)
		{
			return fc.getSelectedFile();
		}
		return null;
	}

	protected static String removeSpace(String s)
	{
		String result = new String();
		StringTokenizer st = new StringTokenizer(s, " ");
		while (st.hasMoreTokens())
		{
			result = result + st.nextToken();
		}

		return result;
	}

	public static void showErrorDialog(boolean userFriendly,JFrame parent,Throwable e)
	{
		String msg = e.getClass().getName() + newLineStr;
		msg += e.getMessage() + newLineStr;

		if(userFriendly || !System.getProperty("java.version").startsWith("1.4"))
		{
			JOptionPane.showMessageDialog(parent,msg,"Exception",
				JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			StackTraceElement ste[] = e.getStackTrace();
			JDialog errorDialog = new JDialog(parent, "Exception", true);
			JTextArea textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);

			for(int i=0; i < ste.length; i++)
			{
				msg += "@ " + ste[i].toString() + newLineStr;
			}
			textArea.setText(msg);

			Container c = new Container();
			c.setLayout(new BorderLayout());
			c.add(scrollPane);
			errorDialog.setContentPane(c);

			errorDialog.setSize(errorDialog.getPreferredSize());
			if(parent != null)
			{
				errorDialog.setLocation(getMiddle(parent.getSize(),errorDialog.getSize()));
			}
			errorDialog.show();
			errorDialog.dispose();
		}

	}

	public static String getErrorMessage(Throwable e)
	{
		String msg = e.getClass().getName() + newLineStr;
		msg += e.getMessage() + newLineStr;

		if(!System.getProperty("java.version").startsWith("1.4"))
		{
			return msg;
		}
		else
		{
			StackTraceElement ste[] = e.getStackTrace();

			for(int i=0; i < ste.length; i++)
			{
				msg += "@ " + ste[i].toString() + newLineStr;
			}
		}
		return msg;
	}

	public static void printStack(Stack s,String title,JTextArea t)
	{
		Stack s1 = (Stack)s.clone();
		String output = newLineStr + title + ":";
		while (!s1.empty())
		{
			Entry entry = (Entry)s1.pop();
			String str = TabConverter.revert(entry.getPart());
			output += (newLineStr + str);
			int tag = entry.getTag();
			if(tag != -1)
				str = " < " + analyser.Analyser.Tags.getString(String.valueOf(tag)) + " > ";
			else str = "";

			output += (str);
		}

		output += newLineStr + "---------------";
		t.append(output);
	}


	public static void printStack(Stack s,String title)
	{
		Stack s1 = (Stack)s.clone();
		String output = newLineStr + title + ":";
		while (!s1.empty())
		{
			Entry entry = (Entry)s1.pop();
			String str = TabConverter.revert(entry.getPart());
			output += (newLineStr + str);
			int tag = entry.getTag();
			if(tag != -1)
				str = " < " + analyser.Analyser.Tags.getString(String.valueOf(tag)) + " > ";
			else str = "";

			output += (str);
		}

		output += newLineStr + "---------------";
		System.out.println(output);
	}

	/* tedious
	public static void printStack(Frame parent, Stack s,String title)
	{
		Stack s1 = (Stack)s.clone();
		String output = newLineStr + title + ":";
		while (!s1.empty())
		{
			Entry entry = (Entry)s1.pop();
			String str = TabConverter.revert(entry.getPart());
			output += (newLineStr + str);
			int tag = entry.getTag();
			if(tag != -1)
				str = " < " + analyser.Analyser.Tags.getString(String.valueOf(tag)) + " > ";
			else str = "";

			output += (str);
		}

		output += newLineStr + "---------------";

		textArea.setWrapStyleWord(true);
		textArea.setFont(getFont());
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);

		textArea.append(output);

		Container c = new Container();
		c.setLayout(new BorderLayout());
		c.add(scrollPane,BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				stackDialog.dispose();
			}
		});
		buttonPanel.add(okButton);
		c.add(buttonPanel,BorderLayout.SOUTH);

		stackDialog.setContentPane(c);

		stackDialog.setSize(stackDialog.getPreferredSize());
		if(parent != null)
		{
			stackDialog.setLocation(getMiddle(parent.getSize(),stackDialog.getSize()));
		}
		stackDialog.pack();
		stackDialog.show();
	}*/

	public static void writeData(String[] words, String fileName)
	{
		try
		{
			FileOutputStream fOutStream;
			if(fileName != null)
				fOutStream = new FileOutputStream(fileName);
			else
				fOutStream = new FileOutputStream("SomeFile.txt");
			for(int i=0; i<words.length; i++)
			{
				fOutStream.write(words[i].getBytes());
				fOutStream.write(newLineStr_OS.getBytes());
			}
			fOutStream.close();
		}catch(IOException e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ioe);
			return;
		}

	}

	public static void writeData(Vector words, String fileName)
	{
		try
		{
			BufferedWriter bw;
			if(fileName != null)
				bw = new BufferedWriter(new FileWriter(new File(fileName)));
			else
				bw = new BufferedWriter(new FileWriter(new File("SomeFile.txt")));
			for(int i=0; i<words.size(); i++)
			{
				String s = (String)words.get(i);
				bw.write(s,0,s.length());
				bw.newLine();
			}
			bw.close();
		}catch(IOException e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ioe);
			return;
		}
	}

	public static void writeData(String words,String fileName)
	{
		StringTokenizer inputTokens = new StringTokenizer(words,"\n");
		try
		{
			BufferedWriter bw;
			if(fileName != null)
				bw = new BufferedWriter(new FileWriter(new File(fileName)));
			else
				bw = new BufferedWriter(new FileWriter(new File("SomeFile.txt")));
			for(;inputTokens.hasMoreTokens();)
			{
				String s = inputTokens.nextToken();
				bw.write(s,0,s.length());
				bw.newLine();
			}
			bw.close();
		}catch(IOException e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ioe);
			return;
		}
	}
	public static String readData(String fileName)
	{
		String s = "";
		try
		{
			InputStream fInStream = new FileInputStream(fileName);
			int n = fInStream.available();
			byte b[] = new byte[n];
			fInStream.read(b);
			fInStream.close();
			s = new String(b,0,n);
		}catch(IOException e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ioe);
			return "Error input";
		}
		return s;
	}

	public static String[] getStrArray(String fileName)
	{
		String str = readData(fileName);
		StringTokenizer s = new StringTokenizer(str,"\n");
		String[] output = new String[s.countTokens()];
		for(int i=0;s.hasMoreTokens();i++)
		{
			output[i] = s.nextToken().trim();
		}
		return output;
	}

	public static String[] getStrArray(BufferedReader in)
	{
		StringBuffer data = new StringBuffer();
		new FileLoader1(in,data);
		String str = data.toString();
		StringTokenizer s = new StringTokenizer(str,"\n");
		String[] output = new String[s.countTokens()];
		for(int i=0;s.hasMoreTokens();i++)
		{
			output[i] = s.nextToken().trim();
		}
		return output;
	}

	public static String[] getStrArray_ignoreTag(String fileName)
	{
		String str = readData(fileName);
		str = removeTag(str);
		StringTokenizer s = new StringTokenizer(str,"\n");
		String[] output = new String[s.countTokens()];
		for(int i=0;s.hasMoreTokens();i++)
		{
			output[i] = s.nextToken().trim();
		}
		return output;
	}

	public static String[] getStrArray1(String input)
	{
		input = removeTag(input);
		StringTokenizer s = new StringTokenizer(input,"\n");
		String[] output = new String[s.countTokens()];
		for(int i=0;s.hasMoreTokens();i++)
		{
			output[i] = s.nextToken().trim();
		}
		return output;
	}

	public static String readData(File file)
	{
		String s = "";
		try
		{
			InputStream fInStream = new FileInputStream(file);
			int n = fInStream.available();
			byte b[] = new byte[n];
			fInStream.read(b);
			fInStream.close();
			s = new String(b,0,n);
		}catch(IOException e)
		{
			e.printStackTrace();//showErrorDialog(false,null,ioe);
			return "Error input";
		}
		return s;
	}

	public static boolean isTagged(String input)
	{
		int eolnIndex = input.indexOf(Utils.newLineStr);

		int secondEolnIndex = -1;
		if(eolnIndex != -1)
		{
			secondEolnIndex= input.indexOf(Utils.newLineStr,eolnIndex+1);
		}

		int tagIndex = input.indexOf('<');

		if(tagIndex != -1)
		{
			if(secondEolnIndex == -1)
				return true;
			if(tagIndex < secondEolnIndex)
				return true;
		}
		return false;
	}

	public static void addUndoRedo(JTextComponent t)
	{
		Document doc = t.getDocument();
		final UndoManager undo = new UndoManager();

		doc.addUndoableEditListener(new UndoableEditListener()
		{
			public void undoableEditHappened(UndoableEditEvent evt)
			{
				undo.addEdit(evt.getEdit());
			}
		});

		t.getActionMap().put("Undo",
			new AbstractAction("Undo") {
				public void actionPerformed(ActionEvent evt) {
					try {
						if (undo.canUndo()) {
							undo.undo();
						}
					} catch (CannotUndoException e) {
					}
				}
			});
		t.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		t.getActionMap().put("Redo",
			new AbstractAction("Redo") {
				public void actionPerformed(ActionEvent evt) {
					try {
						if (undo.canRedo()) {
							undo.redo();
						}
					} catch (CannotRedoException e) {
					}
				}
			});
		t.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
	}

	public static boolean isProperInput(Component parent, JTextComponent t)
	{
		String inputStr = t.getText().trim();
		if(inputStr == null || inputStr.equals(""))
		{
			JOptionPane.showMessageDialog(
				parent,
				"Please Enter Input.",
				"Input?",
				JOptionPane.PLAIN_MESSAGE);
			t.requestFocus();
			return false;
		}
		return true;
	}

	public static String removeTag(String ip)
	{
		StringBuffer output = new StringBuffer();
		return parseLine1(ip,output);
	}

	static String parseLine1(String input,StringBuffer output)
	{
		try
		{
			int ltIndex = -1;
			int gtIndex = -1;
			while(input.length() > 0)
			{
				ltIndex = input.indexOf("<");
				if(ltIndex != -1)
				{
					gtIndex = input.indexOf(">");
					if(gtIndex != -1)
					{
						output.append(input.substring(0,ltIndex));
						input = input.substring(gtIndex+1);
					}
					else
					{
						input = "WRONG INPUT";
					}
				}
				else
				{
					output.append(input.substring(0,input.length()));
					break;
				}
			}
		}catch(StringIndexOutOfBoundsException ex)
		{
			System.out.println("Utils parseLine: " + ex);
		}
		return output.toString();
	}

	public static void print(String title, byte[] b)
	{
		System.out.println(title + ": ");
		for(int i=0; i<b.length; i++)
		{
			System.out.println(i + ": " + b[i]);
		}
	}

	public static String replace(String s, String r, String i)
	{
		return i.replaceAll(s,r);
	}

	public static void main(String ars[])
	{
		System.out.println("main");
		String[] s = getStrArray("Rule.txt");
		for(int i = 0; i < s.length; i++)
		{
			System.out.println(i + ": " + s[i]);
		}
	}

	public static void printArray(byte[] n)
	{
		for(int i=0; i<n.length;i++)
		{
			System.out.println(i + ": " + n[i]);
		}
	}

	public static void printArray(String[] s)
	{
		for(int i=0; i<s.length;i++)
		{
			System.out.println(i + ": " + s[i]);
		}
	}



	public static String removeDuplicateWords(String inputText,
		boolean considerSpace, String filepath)
	{
		String outputText = "";
		String wordSeparators = "";

		if(considerSpace)
		{
			wordSeparators = newLineStr;
		}
		else
		{
			wordSeparators = new String(WORD_SEPARATORS_FOR_DIC);
		}

		StringTokenizer inputTokens = new StringTokenizer(inputText,
			wordSeparators);
		int ipWordCount = inputTokens.countTokens();

		Set uniqueWordSet = new HashSet();
		String duplicates = "<Duplicates>" + newLineStr_OS;
		while(inputTokens.hasMoreTokens())
		{
			String s = inputTokens.nextToken();
			boolean added = uniqueWordSet.add(s);
			if(!added)
			{
				duplicates += s + newLineStr_OS;
				uniqueWordSet.remove(s);
				//for some purpose like easy id of dup words
				//s += "Dup";
				uniqueWordSet.add(s);
			}
		}

		int opWordCount = uniqueWordSet.size();
		Iterator it = uniqueWordSet.iterator();

		String delim=" ";

		if(considerSpace)
		{
			delim = newLineStr;
		}
		for(int i=0; i<opWordCount; i++)
		{
			outputText += delim + it.next();
			if(!considerSpace && i % 8 == 0)
			{
				outputText += newLineStr;
			}
		}

		String msg = newLineStr + "Input/Output(in Words): " + ipWordCount + "/" + opWordCount
			 + ":::" + " Duplicates:=" + (ipWordCount-opWordCount) ;

		duplicates += msg;
		writeData(duplicates, filepath);

		return outputText;
	}
	public static String sort(String inputText,boolean considerSpace, String filepath)
	{

		String outputText = "";

		inputText = removeDuplicateWords(inputText,considerSpace,filepath);

		String wordSeparators;
		if(considerSpace)
		{
			wordSeparators = new String(newLineStr);
		}
		else
		{
			wordSeparators = new String(WORD_SEPARATORS_FOR_DIC);
		}
		StringTokenizer inputTokens = new StringTokenizer(inputText,
			wordSeparators);

		int count = inputTokens.countTokens();

		String[] ip = new String[count];
		String[] ipbkup = new String[count];

		for(int i=0; i<count; i++)
		{
			ip[i] = inputTokens.nextToken();
			ipbkup[i] = ip[i];
		}

		String[] sWithoutSpace = preProcessForSort(ip);

		String[] b4Sort = convertToTab(sWithoutSpace,considerSpace);

		Arrays.sort(b4Sort);

		String[] afSort = convertToString(b4Sort,considerSpace);

		String[] opStr = new String[afSort.length];

		int j=0;
		for(int i=0; i<afSort.length; i++)
		{
			if(hash.containsKey(afSort[i]))
			{
				opStr[j] = hash.get(afSort[i]).toString();
			}
			else
			{
				opStr[j] = afSort[i];
			}
			j++;
		}

		for(int i=0; i<opStr.length; i++)
		{
			outputText += opStr[i] + newLineStr;
		}

		//debug
		/*
		for(int i=0; i<b4Sort.length; i++)
		{
			byte[] b = b4Sort[i].getBytes();
			String s = "";
			for(int k=0; k<b.length; k++)
			{
				s += b[k] +": ";
			}
			outputText += afSort[i] + "::: " + s + newLineStr;
		}
		*/
		return outputText;
	}
	public static String[] convertToTab(String[] ip,boolean considerSpace)
	{
		String[] op = new String[ip.length];

		if(considerSpace)
		for(int i=0; i<ip.length; i++)
		{
			byte[] b = Sort_TabConverter.convert(ip[i]);
			op[i] = new String(b);
		}
		else
		for(int i=0; i<ip.length; i++)
		{
			byte[] b = TabConverter.convert(ip[i]);
			op[i] = new String(b);
		}

		return op;
	}

	public static String[] convertToString(String[] ip,boolean considerSpace)
	{
		String[] op = new String[ip.length];

		if(considerSpace)
		for(int i=0; i<ip.length; i++)
		{
			byte b[] = ip[i].getBytes();
			op[i] = Sort_TabConverter.revert(b);
		}
		else
		for(int i=0; i<ip.length; i++)
		{
			byte b[] = ip[i].getBytes();
			op[i] = TabConverter.revert(b);
		}
		return op;
	}

	protected static String[] preProcessForSort(String[] input)
	{
		int count = input.length;

		hash = new HashMap();
		String[] output = new String[count];

		for(int i=0; i<count; i++)
		{
			String op = input[i];
			if(input[i].indexOf(' ') != -1)
			{
				op = removeSpace(input[i]);
				hash.put(op,input[i]);
			}
			output[i] = op;
		}
		return output;
	}
}
//word sep:
//;?_rf.:-()[]{}<>/|"};

