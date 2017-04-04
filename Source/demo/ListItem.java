package demo;

import java.awt.Color;

public class ListItem
{

    public ListItem(Color color1, String s)
    {
        color = color1;
        value = s;
    }

    public Color getColor()
    {
        return color;
    }

    public String getValue()
    {
        return value;
    }

    public String toString()
    {
        return value + Utils.newLineStr + "----";
    }

    private Color color;
    private String value;
}
