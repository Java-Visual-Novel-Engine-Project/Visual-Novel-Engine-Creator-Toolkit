package com.marcel.Windows.Surfaces;
import com.marcel.Utils.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GenericProject
{
    public WindowSize size;
    protected String title = "Empty";

    public String GetTitle()
    {
        return title;
    }

    public GenericProject()
    {

    }


    public void DrawFrame(Graphics2D g2d) {}

    public void HandleMouseClick(MouseEvent e) {}
    public void HandleMousePress(MouseEvent e) {}
    public void HandleMouseRelease(MouseEvent e) {}

}