package GUI;

import GIS.Meta_data_element;
import Game.Fruit;
import Game.Game;
import Game.Map;
import Game.Packman;
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

    private Image image; //game background image.
    private Game game; //game object to work with.
    private int type = 0;
    private Map map; //map object according to provided image.
    private int IDfruits = 0;
    private int IDpacs = 0;

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
            Point3D pixel = null;
            try { //pixel might be out of map bounds.
                pixel = map.CoordsToPixels((Point3D)pacman.getGeom(), getHeight(), getWidth(),false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                this.game = new Game();
                g.drawImage(image, 0, 0, w, h, this);
                break;
            }
            g.setColor(Color.decode(pacman.getData().getColor()));
            g.fillOval((int) pixel.x()-8, (int) pixel.y()-8, 16, 16);
        }

        while (FruitIterator.hasNext()) {
            Fruit fruit = (Fruit)FruitIterator.next();
            Point3D pixel = null;
            try { //pixel might be out of map bounds.
                pixel = map.CoordsToPixels((Point3D)fruit.getGeom(), getHeight(), getWidth(),false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                this.game = new Game();
                g.drawImage(image, 0, 0, w, h, this);
                break;
            }
            g.setColor(Color.decode(fruit.getData().getColor()));
            g.fillOval((int) pixel.x()-5, (int) pixel.y()-5, 10, 10);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman and Fruits");
        JFrameGraphics ourJFrame = new JFrameGraphics();
        frame.getContentPane().add(ourJFrame);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);


        MenuBar MainMenu = new MenuBar();
        frame.setMenuBar(MainMenu);
        Menu fileMenu = new Menu("File");
        Menu addMenu = new Menu("Add");
        MenuItem pacmenItemMenu = new MenuItem("Pacman");
        MenuItem fruitItemMenu = new MenuItem("Fruit");

        fruitItemMenu.addActionListener(e -> ourJFrame.type = 2);
        pacmenItemMenu.addActionListener((e -> ourJFrame.type = 1));

        addMenu.add(pacmenItemMenu);
        addMenu.add(fruitItemMenu);

        MenuItem loadFromCsvItemMenu = new MenuItem("Load From CSV");
        fileMenu.add(loadFromCsvItemMenu);
        loadFromCsvItemMenu.addActionListener(e->{
            JFileChooser chooser = new JFileChooser("./Resources/dataExamples");
            FileNameExtensionFilter filter =   new FileNameExtensionFilter(
                    "CSV Files", "csv");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);  // disable the "All files" option.
            int returnValue = chooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                ourJFrame.loadFile(file);
                System.out.println(chooser.getSelectedFile());
            }else{
                System.out.println("Error");
            }
        });

        MenuItem saveToCsvItemMenu = new MenuItem("Save To CSV");
        fileMenu.add(saveToCsvItemMenu);
        saveToCsvItemMenu.addActionListener(e->{
            JFileChooser chooser = new JFileChooser("./Resources/dataExamples");
            FileNameExtensionFilter filter =   new FileNameExtensionFilter(
                    "CSV Files", "csv");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);  // disable the "All files" option.
            int returnValue = chooser.showSaveDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File file = new File(String.valueOf(chooser.getSelectedFile()));
                if (file.getName().endsWith(".csv")) {
                    ourJFrame.saveFile(file);
                    System.out.println(chooser.getSelectedFile());
                }else{
                    JOptionPane.showMessageDialog(null, "Saved games must end with .csv file extension!! Try again.");
                }
            }else{
                System.out.println("Error");
            }
        });

        MainMenu.add(fileMenu);
        MainMenu.add(addMenu);


    }

    private void saveFile(File file) {
        this.game.saveGameToCsv(file.getAbsolutePath());
    }

    private void loadFile(File file) {
        try {
            this.game = new Game(file);
            repaint();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "This CSV file is not compatible with our game.");
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (type == 1) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element pacman_meta = new Meta_data_element("Packman name", "P"); //color is white as default.
            Packman pac = new Packman(globalpoint, pacman_meta,IDpacs++, 1, 1); //orientation is (1,1,1) as default.
            game.getPacmen().add(pac);
        }
        if (type == 2) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint2 = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element fruit_meta = new Meta_data_element("Fruit name", "F"); //color is red as default.
            Fruit fruit = new Fruit(globalpoint2,fruit_meta,IDfruits++,1);
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