package GUI;

import javax.swing.*;

public class MainMenu extends JFrame{
    private JLabel welcomeHeader;
    private JLabel developedByFooter;
    private JButton loadGameBtn;
    private JButton saveGameBtn;
    private JButton newGameBtn;
    private JPanel mainPanel;

    public MainMenu() { //constructor
        newGameBtn.addActionListener(e -> {  //new game button is clicked
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            gameWindow.setSize(gameWindow.myImage.getWidth(),gameWindow.myImage.getHeight());
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
