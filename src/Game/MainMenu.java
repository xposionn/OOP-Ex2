package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame{
    private JLabel welcomeHeader;
    private JLabel developedByFooter;
    private JButton loadGameBtn;
    private JButton saveGameBtn;
    private JButton newGameBtn;
    private JPanel mainPanel;

    public MainMenu() { //constructor
        newGameBtn.addActionListener(new ActionListener() { //new game button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow gameWindow = new GameWindow();
                gameWindow.setVisible(true);
                gameWindow.setSize(gameWindow.myImage.getWidth(),gameWindow.myImage.getHeight());
                gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
