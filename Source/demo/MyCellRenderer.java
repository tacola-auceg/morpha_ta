package demo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

public class MyCellRenderer extends JTextArea
    implements ListCellRenderer
{

    public MyCellRenderer()
    {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1)
    {
        setText(((ListItem)obj).getValue());
        setBackground(((ListItem)obj).getColor());
        setFont((new Utils()).getFont());
        if(flag)
            setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        else
            setBorder(BorderFactory.createLineBorder(Color.black));
        return this;
    }
}
