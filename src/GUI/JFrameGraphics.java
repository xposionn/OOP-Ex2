package GUI;

import File_format.Main;
import GIS.Meta_data_element;
import Game.Game;
import Game.Map;
import Game.Packman;
import Game.Fruit;
import Geom.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Iterator;

/**
 * some of the code is taken from: https://javatutorial.net/display-text-and-graphics-java-jframe
 */
public class JFrameGraphics extends JPanel implements MouseListener {

    Image image;
    Game game;
    int type = 0;
    Map map;


    public JFrameGraphics() {
        this.game = new Game();
        Point3D topLeft = new Point3D(35.20236,32.10572);
        Point3D downRight = new Point3D(35.21235,32.10194);
        this.map = new Map(new File("Resources/GameMaps/Ariel1.png"),topLeft,downRight);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        image = Toolkit.getDefaultToolkit().getImage(map.getImagePath());
        int w = this.getWidth();
        int h = this.getHeight();
        g.drawImage(image, 0, 0, w, h, this);
        Iterator PacIterator = game.getPacmen().iterator();
        Iterator FruitIterator = game.getFruits().iterator();

        while (PacIterator.hasNext()) {
            Packman pacman = (Packman)PacIterator.next();
            Point3D Pixel = map.CoordsToPixels((Point3D)pacman.getGeom(), getHeight(), getWidth());
            g.setColor(Color.decode(pacman.getData().getColor()));
            g.fillOval((int) Pixel.x()-8, (int) Pixel.y()-8, 16, 16);
        }

        while (FruitIterator.hasNext()) {
            Fruit fruit = (Fruit)FruitIterator.next();
            Point3D Pixel = map.CoordsToPixels((Point3D)fruit.getGeom(), getHeight(), getWidth());
            g.setColor(Color.decode(fruit.getData().getColor()));
            g.fillOval((int) Pixel.x()-5, (int) Pixel.y()-5, 10, 10);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Packman and Fruits");
        JFrameGraphics ourJFrame = new JFrameGraphics();
        frame.getContentPane().add(ourJFrame);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);


        MenuBar MainMenu = new MenuBar();
        frame.setMenuBar(MainMenu);
        Menu File = new Menu("File");
        Menu AddMenu = new Menu("Add:");
        MenuItem Pacman = new MenuItem("Packman");
        MenuItem Fruit = new MenuItem("Fruit");

        Fruit.addActionListener(e -> ourJFrame.type = 2);
        Pacman.addActionListener((e -> ourJFrame.type = 1));

        AddMenu.add(Pacman);
        AddMenu.add(Fruit);

        MenuItem Load = new MenuItem("Load From CSV:");
        File.add(Load);
        Load.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter =   new FileNameExtensionFilter(
                    "CSV Files", "csv");
            chooser.setFileFilter(filter);
            int returnValue = chooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                ourJFrame.loadFile(file);

                System.out.println(chooser.getSelectedFile());
            }else{
                System.out.println("Error");
            }
        });
        MainMenu.add(File);
        MainMenu.add(AddMenu);


    }

    private void loadFile(File file) {
        this.game = new Game(file);
        repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (type == 1) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element pacman_meta = new Meta_data_element("Packman name", "P"); //color is white as default.
            Packman pac = new Packman(globalpoint, pacman_meta, 1, 1); //orientation is (1,1,1) as default.
            game.getPacmen().add(pac);
        }
        if (type == 2) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint2 = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element fruit_meta = new Meta_data_element("Fruit name", "F"); //color is white as default.
            Fruit fruit = new Fruit(globalpoint2,fruit_meta,1);
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