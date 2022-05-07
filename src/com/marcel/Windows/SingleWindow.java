package com.marcel.Windows;

import com.marcel.Utils.Point;
import com.marcel.Utils.WindowSize;
import com.marcel.Windows.Surfaces.GenericProject;
import com.marcel.Windows.Surfaces.TestingProject;
//import com.marcel.VNWindow.SceneObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static com.marcel.Utils.Util.puts;


public class SingleWindow extends JFrame
{
    List<SingleWindow> windowList;
    WindowManager manager;

    List<GenericProject> projects;
    GenericProject currentProject;

    MouseState mouse;



    public SingleWindow(String windowName, int width, int height, List<SingleWindow> windowList)
    {
        projects = new ArrayList<>();
        currentProject = null;
        this.windowList = windowList;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Close();
            }
        });

        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                HandleKeyEvent(e);
            }
        });

        addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (currentProject != null)
                {
                    manager.UpdateMousePosition();
                    if (mouse.pos.y > 30)
                        currentProject.HandleMouseClick(e);
                    else
                        ;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouse.mousePressed = true;

                HandleMouseEvent(e);
                if (currentProject != null)
                {
                    manager.UpdateMousePosition();
                    if (mouse.pos.y > 30)
                        currentProject.HandleMousePress(e);
                    else
                        manager.HandleMousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouse.mousePressed = false;

                if (currentProject != null)
                {
                    manager.UpdateMousePosition();
                    if (mouse.pos.y > 30)
                        currentProject.HandleMouseRelease(e);
                    else
                        ;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
        });

        addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                if (manager != null)
                {
                    manager.height = getHeight();
                    manager.width = getWidth();
                    manager.subWindowSize.width = width;
                    manager.subWindowSize.height = height - 30;
                }

            }
        });

        setTitle(windowName);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        setMinimumSize(new Dimension(200,200));

        manager = new WindowManager(width, height);
        add(manager);
        mouse = manager.mouse;

        System.out.println("Created new Window.");

    }

    public void UpdateTitle()
    {
        if (currentProject != null)
            setTitle("#" + windowList.indexOf(this) + " - " + currentProject.GetTitle());
        else
            setTitle("#" + windowList.indexOf(this));
    }

    public void UpdateTitles()
    {
        for (SingleWindow window : windowList)
            window.UpdateTitle();
    }

    public void Close()
    {
        puts("Closed Window.");
        windowList.remove(this);
        setVisible(false);
        UpdateTitles();
        dispose();
    }

    public void DrawFrame()
    {
        manager.repaint();
    }

    public void AddProject(GenericProject project)
    {
        project.size = manager.subWindowSize;
        projects.add(project);
        currentProject = project;

        UpdateTitle();
    }

    private void HandleKeyEvent(KeyEvent e)
    {
//            if (e.getKeyCode() == KeyEvent.VK_UP)
//                surface.currentScene.changeSelectedButton(new Point(0, -1));
//            if (e.getKeyCode() == KeyEvent.VK_DOWN)
//                surface.currentScene.changeSelectedButton(new Point(0, 1));
//            if (e.getKeyCode() == KeyEvent.VK_LEFT)
//                surface.currentScene.changeSelectedButton(new Point(-1, 0));
//            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//                surface.currentScene.changeSelectedButton(new Point(1, 0));

        if (currentProject != null)
            currentProject.HandleKeyEvent(e);

        if (e.getKeyCode() == KeyEvent.VK_4)
            AddProject(new TestingProject(projects.size()));

        if (e.getKeyCode() == KeyEvent.VK_5)
            DrawFrame();

        if (e.getKeyCode() == KeyEvent.VK_6)
            setTitle("G2D: " + getGraphics());

    }

    private void HandleMouseEvent(MouseEvent e)
    {
        manager.UpdateMousePosition();
        System.out.println(mouse.pos.x + "," + mouse.pos.y);
        //pointList.add(new Point(mouse.pos.x, mouse.pos.y));
    }





    class WindowManager extends JPanel implements ActionListener
    {

        public int width, height;
        public GenericProject project;
        public WindowSize subWindowSize;
        public Font menuFont;
        private MouseState mouse;

        public WindowManager(int width, int height)
        {
            this.width = width;
            this.height = height;
            project = null;
            subWindowSize = new WindowSize(0, 30, width, height - 30);

            menuFont = new Font("Arial",0, 25);

            mouse = new MouseState();
            mouse.hoverProject = null;
            UpdateTitle();

            repaint();
        }

        public void HandleMousePressed(MouseEvent e)
        {
            if (mouse.hoverProject != null)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    currentProject = mouse.hoverProject;
                }
                else if (e.getButton() == MouseEvent.BUTTON2)
                {
                    if (projects.size() == 1)
                    {
//                        projects.remove(mouse.hoverProject);
//                        projects.add(new GenericProject());
//                        currentProject = projects.get(0);
                        Close();
                    }
                    else
                    {
                        if (currentProject == mouse.hoverProject)
                        {
                            int i = projects.indexOf(currentProject);
                            projects.remove(mouse.hoverProject);
                            if (i >= projects.size())
                                i--;
                            currentProject = projects.get(i);
                        }
                        else
                            projects.remove(mouse.hoverProject);
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                {
                    if (projects.size() == 1)
                    {
                        //Close();
                    }
                    else
                    {
                        if (currentProject == mouse.hoverProject)
                        {
                            int i = projects.indexOf(currentProject);
                            projects.remove(mouse.hoverProject);
                            if (i >= projects.size())
                                i--;
                            currentProject = projects.get(i);
                        }
                        else
                            projects.remove(mouse.hoverProject);

                        {
                            SingleWindow window = new SingleWindow(mouse.hoverProject.GetTitle(), width, height, windowList);
                            window.AddProject(mouse.hoverProject);
                            windowList.add(window);
                            window.UpdateTitle();
                        }
                    }
                }
            }
            else if (mouse.newProject)
            {
                AddProject(new TestingProject(projects.size()));
            }
            UpdateTitle();
        }

        private void doDrawing(Graphics2D g2d) throws Exception
        {
            UpdateMousePosition();

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,30, width, height-30);

            if (currentProject != null)
                currentProject.DrawFrame(g2d);

            {
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0,0, width, 30);

                mouse.hoverProject = null;

                double pos = 5;
                double space = 5;
                double boxsize = ((width - ((projects.size() + 1) * 5) - 80) / ((double)projects.size()));
                if (boxsize < 5)
                {
                    space = 1;
                    boxsize = ((width - ((projects.size() + 1) * space) - 80) / ((double)projects.size()));
                }
                for (int i = 0; i < projects.size(); i++)
                {
                    GenericProject current = projects.get(i);
                    if (mouse.pos.x >= pos && mouse.pos.y >= 0 && mouse.pos.y <= 30 && mouse.pos.x <= pos+ boxsize)
                    {
                        mouse.hoverProject = current;
                        g2d.setColor(Color.BLUE);
                    }
                    else if (current == currentProject)
                        g2d.setColor(Color.LIGHT_GRAY);
                    else
                        g2d.setColor(Color.GRAY);

                    g2d.fillRect((int)pos,0, (int)boxsize, 30);
                    pos += boxsize + space;
                }

                g2d.setFont(menuFont);

                if (mouse.pos.x >= pos && mouse.pos.y >= 0 && mouse.pos.y <= 30 && mouse.pos.x <= width)
                {
                    g2d.setColor(Color.BLUE);
                    mouse.newProject = true;
                }
                else
                {
                    g2d.setColor(Color.WHITE);
                    mouse.newProject = false;
                }

                g2d.drawString("+", (width - 60), 25);
            }

//            if (pointList.size() >= 2)
//            {
//                Point currentOld = pointList.get(0), current;
//
//                g2d.setColor(Color.RED);
//
//                for (int i = 1; i < pointList.size(); i++)
//                {
//                    current = pointList.get(i);
//                    g2d.drawLine(currentOld.x, currentOld.y, current.x, current.y);
//                    currentOld = pointList.get(i);
//                }
//            }
        }

        private void UpdateMousePosition()
        {
            PointerInfo a = MouseInfo.getPointerInfo();
            java.awt.Point b = a.getLocation();

            mouse.pos.x = (int)b.getX() - manager.getLocationOnScreen().x;
            mouse.pos.y = (int)b.getY() - manager.getLocationOnScreen().y;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                doDrawing((Graphics2D) g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(e.toString());
            repaint();
        }
    }
}


