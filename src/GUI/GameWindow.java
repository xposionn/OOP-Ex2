package GUI;

import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class GameWindow extends JFrame implements MouseListener
{
    public BufferedImage myImage;

    public GameWindow()
    {
        initGUI();
        this.addMouseListener(this);
    }

    private void initGUI()
    {

        MenuBar menuBar = new MenuBar();
        Menu game = new Menu("Game"); //game menu
        MenuItem newGame = new MenuItem("New Game");
        MenuItem saveGame = new MenuItem("Save Game");
        MenuItem loadGame = new MenuItem("Load Game");
        Menu algo = new Menu("Algorithm"); //game menu
        MenuItem eatAll = new MenuItem("Eat all fruits");


        menuBar.add(game);
        game.add(saveGame);
        game.add(loadGame);
        menuBar.add(algo);
        algo.add(eatAll);
        this.setMenuBar(menuBar);

        try {
            myImage = ImageIO.read(new File("./Resources/GameMaps/Ariel1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int x = -1;
    int y = -1;

    public void paint(Graphics g)
    {
        g.drawImage(myImage, 0, 0, this);

        if(x!=-1 && y!=-1)
        {
            int r = 10;
            x = x - (r / 2);
            y = y - (r / 2);
            g.fillOval(x, y, r, r);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg) {
        System.out.println("mouse Clicked");
        System.out.println("("+ arg.getX() + "," + arg.getY() +")");
        x = arg.getX();
        y = arg.getY();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        System.out.println("mouse entered");

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}
