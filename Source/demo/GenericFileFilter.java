package demo;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.filechooser.FileFilter;

/**
 * An implementation of <code>FileFilter</code> interface to let a <code>JFileChooser</code> keep
 * non-"Arangam" files from appearing in the directory listing.
 * <p>
 * This implementation will filter all files without the *.tpt extension.
 */
public class GenericFileFilter extends FileFilter
{
	private String fileExtn = "txt";
	private String fileExtn2 = null;

	public GenericFileFilter(String fileExtn)
	{
		this.fileExtn = fileExtn;
	}

	public GenericFileFilter(String fileExtn, String fileExtn2)
	{
		this.fileExtn = fileExtn;
		this.fileExtn2 = fileExtn2;
	}

	/**
	 * Returns true if this file should be shown in the directory pane,
	 * false if it shouldn't. Accepts all directories and all *.tpt files.
	 */
	public boolean accept(File f)
	{
		//accept directories
		if (f.isDirectory())
			return true;

		//accept arangam files(.tpt)
		String extension = getExtension(f);
		if (extension != null)
		{
			if (extension.equalsIgnoreCase(fileExtn) ||
				extension.equalsIgnoreCase(fileExtn2))
				return true;
			else
				return false;
		}
		return true;

	}

	/**
	 * Returns the human readable description of this filter. The description
	 * returned is:
	 * <p>
	 * <ul>"Arangam(*.tpt)"</ul>
	 * @return the description of the file filter
	 * @see FileFilter#getDescription
	 */
	public String getDescription()
	{
		if(fileExtn.equalsIgnoreCase("mdb"))
			return "Microsoft Access Databases (*.mdb)";
		if(fileExtn.equalsIgnoreCase("tag"))
			return "Auto Tagged Corpus (*.tag)";
		if(fileExtn.equalsIgnoreCase("doc"))
			return "Microsoft Word (*.doc)";
		if(fileExtn.equalsIgnoreCase("htm") ||
			fileExtn.equalsIgnoreCase("html"))
			return "HTML (*.htm, *.html)";
		if(fileExtn.equalsIgnoreCase("gen"))
			return "Morph Gen Output(*.gen)";
		else
			return "ASCII Text (*.txt)";
	}

	/**
	 * Returns the extension portion of the file's name.
	 * @param file the File
	 * @return the extension of the file
	 */
	public static String getExtension(File file)
	{
		String ext = null;
		String s = file.getName();

		//get the portion after last dot
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1)
			ext = s.substring(i+1).toLowerCase();
		return ext;
	}
}
