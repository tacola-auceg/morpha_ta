package demo;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class AnalyserTestPane extends JPanel implements ActionListener,ListSelectionListener
{
	private File currentDir = new File("F:\\_AllDemo\\Generator_Output");
	String outputFilePath;
	final int ANAND = 1;
	final int CURRENT = 2;
	final int INTERMEDIATE = 3;

	public static JTextArea inputTextArea = new JTextArea(4,10);
	JList outputList = new JList();

	JTextField filePathTextField = new JTextField("",20);

	JButton browse = new JButton("Browse");
	JButton analyseNew = new JButton("Analyse");
	JButton analyseT = new JButton("Tested");
	JButton analyseOld = new JButton("Old");

	JButton rd = new JButton("Reload Dic");
	JButton clear = new JButton("Clear");
	JCheckBox sortCbx = new JCheckBox("Sort");
	JCheckBox ignoreTagTextCbx = new JCheckBox("Ignore text between < tags >");
	JComboBox tagComboBox = new JComboBox();
	JButton help = new JButton("Help");
	JButton exit = new JButton("Exit");

	JList inputWordList = new JList();

	JFrame frame;

	public AnalyserTestPane(JFrame frame)
	{
		this.frame = frame;
		setLayout(new BorderLayout());

		analyseNew.setToolTipText("Current Version");
		analyseOld.setToolTipText("Old Version");
		analyseT.setToolTipText("Tested Version");
		filePathTextField.setToolTipText("Path of the File");
		browse.setToolTipText("Select the input file");
		rd.setToolTipText("Reloads Dictionary");
		clear.setToolTipText("Clear everything");
		sortCbx.setToolTipText("Sort input text");
		tagComboBox.setToolTipText("Select a tag form");
		ignoreTagTextCbx.setToolTipText("Ignores the text between the tags <>");
		help.setToolTipText("Help for how to use");
		exit.setToolTipText("Exit Application");

		analyseNew.setMnemonic(java.awt.event.KeyEvent.VK_A);
		analyseOld.setMnemonic(java.awt.event.KeyEvent.VK_O);
		clear.setMnemonic(java.awt.event.KeyEvent.VK_C);
		sortCbx.setMnemonic(java.awt.event.KeyEvent.VK_P);
		browse.setMnemonic(java.awt.event.KeyEvent.VK_B);
		exit.setMnemonic(java.awt.event.KeyEvent.VK_X);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel("File: "));
		buttonPanel.add(filePathTextField);
		buttonPanel.add(browse);
		buttonPanel.add(analyseNew);
		//buttonPanel.add(analyseT);
		//buttonPanel.add(analyseOld);
		//buttonPanel.add(rd);
		buttonPanel.add(clear);
		buttonPanel.add(exit);

		JPanel cbxPanel = new JPanel();
		cbxPanel.add(sortCbx);
		cbxPanel.add(ignoreTagTextCbx);
		cbxPanel.add(new JLabel("Tags: "));
		cbxPanel.add(tagComboBox);
		cbxPanel.add(help);

		JPanel btn_cbx_Pane = new JPanel(new BorderLayout());
		btn_cbx_Pane.add(buttonPanel,BorderLayout.NORTH);
		btn_cbx_Pane.add(cbxPanel,BorderLayout.SOUTH);

		JPanel inputTextScroll_Pane = new JPanel(new BorderLayout());
		JScrollPane inputTextScroll = new JScrollPane(inputTextArea,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputTextScroll_Pane.add(inputTextScroll);

		inputTextScroll_Pane.setBorder(new TitledBorder(
				new EtchedBorder(),"Input Text"));
		Utils.addUndoRedo(inputTextArea);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(btn_cbx_Pane,BorderLayout.NORTH);
		topPanel.add(inputTextScroll_Pane,BorderLayout.CENTER);

		JPanel inputWordListScroll_Pane = new JPanel(new BorderLayout());
		JScrollPane inputWordListScroll = new JScrollPane(inputWordList);
		inputWordListScroll_Pane.add(inputWordListScroll);

		JPanel outputListScroll_Pane = new JPanel(new BorderLayout());
		JScrollPane outputListScroll = new JScrollPane(outputList);
		outputListScroll_Pane.add(outputListScroll);

		JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
								inputWordListScroll_Pane, outputListScroll_Pane);

		inputWordListScroll_Pane.setBorder(new TitledBorder(
				new EtchedBorder(),"Input Words"));

		outputListScroll_Pane.setBorder(new TitledBorder(
				new EtchedBorder(),"Output"));

		bottomSplitPane.setOneTouchExpandable(true);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
								topPanel, bottomSplitPane);
		splitPane.setOneTouchExpandable(true);

		add(splitPane,BorderLayout.CENTER);

		inputTextArea.setTabSize(3);

		tagComboBox.addItem(analyser.Tag.ENGLISH_ABBREVIATED);
		tagComboBox.addItem(analyser.Tag.ENGLISH_EXPAND);
		tagComboBox.addItem(analyser.Tag.TAMIL_ABBREVIATED);
		tagComboBox.addItem(analyser.Tag.TAMIL_EXPAND);

		//init
		//inputTextArea.setText("Üñ¢ñ£ Üð¢ð£¬õ ªê£ô¢½ñ¢");
		//Thread t = new FileLoader(new File("Sample\\Sample.txt"),inputTextArea.getDocument());
		//t.join();

		tagComboBox.setSelectedIndex(1);

		ignoreTagTextCbx.setSelected(true);

		Font f = Utils.getFont();

		inputTextArea.setFont(f);
		outputList.setFont(f);
		outputList.setCellRenderer(new MyCellRenderer());
		inputWordList.setFont(f);

		inputTextArea.setLineWrap(true);
		inputTextArea.setWrapStyleWord(true);

		inputWordList.addListSelectionListener(this);
		outputList.addListSelectionListener(this);

		browse.addActionListener(this);
		analyseNew.addActionListener(this);
		analyseOld.addActionListener(this);
		analyseT.addActionListener(this);
		rd.addActionListener(this);
		clear.addActionListener(this);
		help.addActionListener(this);
		exit.addActionListener(this);
		inputTextArea.requestFocus();

		addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent fe)
			{
				super.focusGained(fe);
				inputTextArea.requestFocus();
			}
		});
		analyser.Analyser.init();

	}

	public void actionPerformed(ActionEvent ae)
	{
		Object obj = ae.getSource();
		if(obj == browse)
		{
			JFileChooser fc = new JFileChooser();
			Utils.setProperty(fc,"txt");
			if(currentDir != null)
				fc.setCurrentDirectory(currentDir);

			int option = fc.showOpenDialog(this);
			if(option == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				currentDir = fc.getCurrentDirectory();

				new FileLoader(file,inputTextArea.getDocument());

				filePathTextField.setText(file.getPath());
			}
		}
		else if(obj == analyseNew)
		{
			if(Utils.isProperInput(this,inputTextArea))
			{
				analyseAll(CURRENT,sortCbx.isSelected(),ignoreTagTextCbx.isSelected());
			}
		}
		else if(obj == analyseOld)
		{
			if(Utils.isProperInput(this,inputTextArea))
			{
				analyseAll(ANAND,sortCbx.isSelected(),ignoreTagTextCbx.isSelected());
			}
		}
		else if(obj == analyseT)
		{
			if(Utils.isProperInput(this,inputTextArea))
			{
				analyseAll(INTERMEDIATE,sortCbx.isSelected(),ignoreTagTextCbx.isSelected());
			}
		}
		else if(obj == clear)
		{
			inputWordList.setListData(new Vector());
			outputList.setListData(new Vector());
			tagComboBox.setSelectedIndex(1);
			ignoreTagTextCbx.setSelected(true);
			sortCbx.setSelected(false);
			inputTextArea.setText("");
			filePathTextField.setText("");
		}
		else if(obj == rd)
		{
			reloadDic();
		}
		else if(obj == exit)
		{
			System.exit(0);
		}
		else if(obj == help)
		{
			help();
		}

	}

	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getSource()==inputWordList)
		{
			if(inputWordList.getSelectedValue() != null)
			{
				outputList.setSelectedIndex(inputWordList.getSelectedIndex());
				outputList.ensureIndexIsVisible(outputList.getSelectedIndex());
			}
		}

		if(e.getSource()==outputList)
		{
			try
			{
				if(inputWordList.getSelectedValue() != null)
				{
					inputWordList.setSelectedIndex(outputList.getSelectedIndex());
					inputWordList.ensureIndexIsVisible(inputWordList.getSelectedIndex());
				}
			}catch(ArrayIndexOutOfBoundsException aiobe)
			{
				System.err.println("ArrayIndexOutOfBoundsException @ AnalyserTestPane.actionPerformed()");
			}
		}
	}

	public static void main(String args[])
	{
		JFrame f = new JFrame();
		JPanel p = new AnalyserTestPane(f);

		f.setTitle("Atcharam(Tamil Morphological Analyser) - RCILTS-Tamil");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.getContentPane().add(p);
		Utils.sizeScreen(f);
		f.setVisible(true);
	}

	public String[] tokenize(String string)
	{
		int i=0;
		String wordSeparators = new String(Utils.WORD_SEPARATORS_FOR_DIC);
		StringTokenizer stringTokens = new StringTokenizer(string,wordSeparators);
		String[] result = new String[stringTokens.countTokens()];
		while(stringTokens.hasMoreTokens())
		result[i++] = stringTokens.nextToken();
		return result;
	}

	public ListItem[] getAnalysed(String[] s,int version)
	{
		ListItem[] listItem = new ListItem[s.length+2];
		int errCount = 0;
		Date startDate,endDate;
		String msg = "";
		ListItem li = null;
		long timeElapsed = 0;
		switch(version)
		{
			case CURRENT:
			startDate = new Date();
			for(int i=0; i<s.length;  i++)
			{
				String analysedOutput = analyser.Analyser.analyseF(s[i]);

				Color listItemColor = Color.white;
				if(!Utils.isTagged(analysedOutput))
				{
					listItemColor = new Color(255,255,200);
					errCount++;
				}
				li = new ListItem(listItemColor,analysedOutput);
				listItem[i] = li;
			}
			endDate = new Date();
			timeElapsed = Utils.calculateElapsedTime(startDate,endDate);
			msg = "Time Elapsed: " +String.valueOf(timeElapsed)+ " ms";
			li = new ListItem(new Color(251,237,251),msg);
			listItem[s.length] = li;
			break;
		}

		int correctCount = s.length - errCount;
		double percentage = (float)correctCount/s.length;
		percentage *= 100;
		String perMsg = "Percentage: " + correctCount + "/" + s.length + "=" +
			percentage;
		li = new ListItem(new Color(251,237,251),perMsg);
		listItem[s.length+1] = li;

		return listItem;
	}

	public void analyseAll(int version, boolean preprocess,
		boolean ignoreTagText)
	{
		String tag = "analyser/tag/" + tagComboBox.getSelectedItem().toString();
		analyser.Analyser.Tags = ResourceBundle.getBundle(tag);

		String inputStr = "";

		if(ignoreTagText)
			inputStr = Utils.removeTag(inputTextArea.getText().trim());
		else
			inputStr = inputTextArea.getText().trim();

		if(preprocess)
			inputStr = Utils.sort(inputStr,false,outputFilePath+"_Duplicates.txt");

		String[] words = tokenize(inputStr);
		inputWordList.setListData(words);
		ListItem[] listItem = getAnalysed(words,version);
		outputList.setListData(listItem);
		if(frame != null)
		{
			frame.toFront();
		}
	}

	public void help()
	{
		JFrame helpFrame = new JFrame("Atcharam Help");

		JTextArea helpPane = new JTextArea();
		helpPane.setFont(Utils.getFont());
		helpPane.setEditable(false);
		helpPane.setWrapStyleWord(true);
		new FileLoader(new File("analyser/tag/Help.txt"),helpPane.getDocument());
		JScrollPane scrollPane = new JScrollPane(helpPane);

		helpFrame.getContentPane().add(scrollPane);
		helpFrame.setSize(580,520);
		helpFrame.setLocation(Utils.
				getMiddle(helpFrame.getSize()));
		helpFrame.setVisible(true);
		helpFrame.toFront();
	}

	public void reloadDic()
	{
		analyser.ADictionary.reloadDic();
	}

}
