package com.marcel.Windows;

import com.marcel.Windows.Utils.Point;
import com.marcel.Windows.Surfaces.GenericProject;

public class MouseState {
    Point pos;
    boolean mousePressed;
    GenericProject hoverProject;
    boolean newProject;

    public MouseState()
    {
        pos = new Point(0,0);
        mousePressed = false;

    }
}
