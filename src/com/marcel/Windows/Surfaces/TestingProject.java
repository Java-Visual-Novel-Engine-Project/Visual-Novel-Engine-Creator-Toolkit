package com.marcel.Windows.Surfaces;

import com.marcel.Utils.WindowSize;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestingProject extends  GenericProject
{
    public String value;

    public TestingProject(int number)
    {
        value = "" + number;
        title = "Testing: " + number;
    }

    @Override
    public void HandleKeyEvent(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            value = "";
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            ;
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        {
            if (value.length() > 0)
                value = value.substring(0, value.length()-1);
        }
        else
            value += e.getKeyChar();

    }

    public void DrawFrame(Graphics2D g2d)
    {
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("String: " + value, size.x + 10, size.y + 20);
    }

}