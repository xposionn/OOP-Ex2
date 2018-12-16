package GUI;

import Algorithms.ShortestPathAlgo;
import Algorithms.Solution;
import GIS.Meta_data_element;
import Game.Fruit;
import Game.Game;
import Game.Map;
import Game.Packman;
import Geom.Path;
import Geom.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static JFrameGraphics ourJFrame;

    public JFrameGraphics() {
        this.game = new Game();
        Point3D topLeft = new Point3D(35.20236,32.10572);
        Point3D downRight = new Point3D(35.21235,32.10194);
        this.map = new Map(new File("Resources/GameMaps/Ariel1.png"),topLeft,downRight);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        System.out.println("Started paint");
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
            g.fillArc((int) pixel.x()-8, (int) pixel.y()-8, 16, 16,30,300);
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
//        System.out.println("Finished paint");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman and Fruits");
        ourJFrame = new JFrameGraphics();
        frame.getContentPane().add(ourJFrame);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);


        MenuBar MainMenu = new MenuBar();
        frame.setMenuBar(MainMenu);
        Menu fileMenu = new Menu("File");
        Menu addMenu = new Menu("Add");
        Menu algoMenu = new Menu("Algo");

        MenuItem pacmenItemMenu = new MenuItem("Pacman");
        MenuItem fruitItemMenu = new MenuItem("Fruit");
        fruitItemMenu.addActionListener(e -> ourJFrame.type = 2);
        pacmenItemMenu.addActionListener((e -> ourJFrame.type = 1));

        addMenu.add(pacmenItemMenu);
        addMenu.add(fruitItemMenu);

        MenuItem loadFromCsvItemMenu = new MenuItem("Load From CSV");
        MenuItem saveToCsvItemMenu = new MenuItem("Save To CSV");

        MenuItem run = new MenuItem("run");

        algoMenu.add(run);



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

        run.addActionListener(l->{
            ourJFrame.runAlgo();
        });
        MainMenu.add(algoMenu);
        MainMenu.add(fileMenu);
        MainMenu.add(addMenu);


    }

    private void runAlgo() {
        ShortestPathAlgo algo = new ShortestPathAlgo(game.getPacmen(),game.getFruits());
        Solution out = algo.runAlgo();
        System.out.println(out); //TODO: delete this.
        Thread repainter = new Thread(new Runnable() {
            @Override
            public void run() {
                String timeToRun= JOptionPane.showInputDialog("Enter for how long do you want to show path animation in milliseconds: ");
                long timeToPlay = 0;
                try{
                    timeToPlay = Long.parseLong(timeToRun);
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed! Enter milli-seconds to run animation.");
                }
                String fpsString= JOptionPane.showInputDialog("Enter how much FPS (Frames per second) you want to run the animation with: " +
                        "\nDefault FPS is set to 60. Max is 144 fps. (Your screen probably doesn't support more than that.)");

                int FPS = 60;
                try{
                    FPS = Integer.parseInt(fpsString);
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed! Enter positive integer for your wanted FPS.");
                }
                if(FPS<=0 && FPS>144){ //regular checks for bypassing.
                    JOptionPane.showMessageDialog(null, "You entered invalid FPS value. We will set it to 60FPS. Have fun.");
                    FPS = 60;
                }

                long startTime = System.currentTimeMillis();
                long currentTime = System.currentTimeMillis();
                while (currentTime-startTime<timeToPlay) {
                    Iterator<Path> pathIt = out.getPaths().iterator();
                    while (pathIt.hasNext()) {
                        Path path = pathIt.next();
//                        System.out.println("Pacman id: " + path.getPacmanInPath().getID() + " Pos:" + path.getPacmanInPath().getGeom());
                        path.getPacmanInPath().setGeom(path.getPacPositionAfterXtime((currentTime-startTime)*100)); /**DO NOT CHANGE
                         This is calculated REAL-TIME movement of Pacman. separately from FPS. Thread sleeping provides the FPS on screen.**/

                        currentTime = System.currentTimeMillis();
                    }
                    ourJFrame.paintImmediately(0, 0, ourJFrame.getWidth(), ourJFrame.getHeight());
                    try {
                        Thread.sleep(1000/FPS);  //FPS determined here. 60 FPS is default.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        repainter.start();

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