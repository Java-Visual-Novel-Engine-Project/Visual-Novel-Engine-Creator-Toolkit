package com.marcel.Windows.Surfaces;

import com.marcel.Utils.WindowSize;

import java.awt.*;

public class TestingProject extends  GenericProject
{
    public int number;

    public TestingProject(int number)
    {
        this.number = number;
        title = "Testing: " + number;
    }


    public void DrawFrame(Graphics2D g2d)
    {
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("Number: " + number, size.x + 10, size.y + 20);
    }

}