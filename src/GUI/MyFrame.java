package GUI;

import javax.swing.*;


public class MyFrame {

    public static void main(String[] args)
    {
        GameWindow window = new GameWindow();
        window.setVisible(true);
        window.setSize(window.myImage.getWidth(),window.myImage.getHeight());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
