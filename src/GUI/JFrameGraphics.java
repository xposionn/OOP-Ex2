package GUI;

import Game.Game;
import Game.Packman;
import Game.Fruit;
import Geom.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

/**
 * Code taken from: https://javatutorial.net/display-text-and-graphics-java-jframe
 */
public class JFrameGraphics extends JPanel implements MouseListener {

    Image image;
    Game game;
    int type = 0;


    public JFrameGraphics() {
        this.game = new Game();
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        image = Toolkit.getDefaultToolkit().getImage("Resources/GameMaps/Ariel1.png");
        int w = this.getWidth();
        int h = this.getHeight();
        g.drawImage(image, 0, 0, w, h, this);
        Iterator PacIterator = game.getPacmen().iterator();
        Iterator FruitIterator = game.getFruits().iterator();

        while (PacIterator.hasNext()) {
            Packman Pac = (Packman)PacIterator.next();
            Point3D Pixel = game.getMap().CoordsToPixels(Pac.getLocation(), getHeight(), getWidth());
            g.setColor(Color.GREEN);
            g.fillOval((int) Pixel.x(), (int) Pixel.y(), 15, 15);
        }

        while (FruitIterator.hasNext()) {
            Fruit fruit = (Fruit)FruitIterator.next();
            Point3D Pixel = game.getMap().CoordsToPixels(fruit.getLocation(), getHeight(), getWidth());
            g.setColor(Color.blue);
            g.fillOval((int) Pixel.x(), (int) Pixel.y(), 15, 15);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman and Fruits");
        JFrameGraphics fr = new JFrameGraphics();
        frame.getContentPane().add(fr);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);


        MenuBar MainMenu = new MenuBar();
        frame.setMenuBar(MainMenu);
        Menu AddMenu = new Menu("Add:");
        MenuItem Pacman = new MenuItem("Pacman");
        MenuItem Fruit = new MenuItem("Fruit");
        Fruit.addActionListener(e -> fr.type = 2);
        Pacman.addActionListener((e -> fr.type = 1));
        AddMenu.add(Pacman);
        AddMenu.add(Fruit);
        MainMenu.add(AddMenu);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (type == 1) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint = game.getMap().PixelsToCoords(point, getHeight(), getWidth());
            Packman pac = new Packman(globalpoint, 1, 0, 0);
            game.getPacmen().add(pac);
        }
        if (type == 2) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint2 = game.getMap().PixelsToCoords(point, getHeight(), getWidth());
            Fruit fruit = new Fruit(globalpoint2);
            game.getFruits().add(fruit);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}