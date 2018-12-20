package GUI;

import Algorithms.ShortestPathAlgo;
import Algorithms.Solution;
import File_format.Path2KML;
import GIS.GIS_element;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * some of the code is taken from: https://javatutorial.net/display-text-and-graphics-java-jframe
 */
public class MyFrame extends JPanel implements MouseListener {

    private Image image; //game background image.
    private Game game; //game object to work with.
    private int typeToAdd = 0; //1 for pacman, 2 for fruits.
    private Map map; //map object according to provided image.
    private static Solution linesSolution;
    private static MyFrame ourJFrame;
    private Painter paintThread;


    public MyFrame() {
        this.game = new Game();
        Point3D topLeft = new Point3D(35.20236,32.10572);
        Point3D downRight = new Point3D(35.21235,32.10194);
        this.map = new Map(new File("Resources/GameMaps/Ariel1.png"),topLeft,downRight);
        addMouseListener(this);
    }

    /**
     * paint function.
     * will rewrite each time the packmans in they curret location, (if we changed them)
     * Run on Packman array and Fruits array, and paint them one by one.
     *
     * LineSolution will be created and painted too.
     * @param g
     */
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
                pixel = map.CoordsToPixels((Point3D)pacman.getGeom(), getHeight(), getWidth());
            } catch (Exception e) {
                resetGame();
                showMessageToScreen(e.getMessage());
                break;
            }
            g.setColor(Color.decode(pacman.getData().getColor()));
            g.fillArc((int) pixel.x()-8, (int) pixel.y()-8, 16, 16,30,300);
            g.drawString("ID: "+pacman.getID(),(int)pixel.x()-5,(int)pixel.y()-10);
            g.drawString("Speed: "+pacman.getSpeed(),(int)pixel.x()-5,(int)pixel.y()-25);
        }

        while (FruitIterator.hasNext()) {
            Fruit fruit = (Fruit)FruitIterator.next();
            if (!fruit.isEaten()) {
                Point3D pixel;
                try { //pixel might be out of map bounds.
                    pixel = map.CoordsToPixels((Point3D)fruit.getGeom(), getHeight(), getWidth());
                } catch (Exception e) {
                    showMessageToScreen(e.getMessage());
                    resetGame();
                    break;
                }
                g.setColor(Color.decode(fruit.getData().getColor()));
                g.fillOval((int) pixel.x()-5, (int) pixel.y()-5, 10, 10);
                g.drawString("ID:"+fruit.getID(),(int)pixel.x()-5,(int)pixel.y()-5);
            }
        }
        if(this.linesSolution!=null) {
            Iterator<Path> pathIterator = this.linesSolution.getPaths().iterator();
            while (pathIterator.hasNext()) {
                Path path = pathIterator.next();
                g.setColor(path.getColor());
                Point3D pixel1, pixel2;
                try {
                    pixel1 = map.CoordsToPixels(path.getPacmanStartPosition(), getHeight(), getWidth());
                } catch (Exception e) {
                    showMessageToScreen(e.getMessage());
                    resetGame();
                    break;
                }
                Iterator<Fruit> fruitItPath = path.getFruitsInPath().iterator();
                while (fruitItPath.hasNext()) {
                    Fruit fruit = fruitItPath.next();
                    try { //pixel might be out of map bounds.
                        pixel2 = map.CoordsToPixels((Point3D) fruit.getGeom(), getHeight(), getWidth());
                    } catch (Exception e) {
                        showMessageToScreen(e.getMessage());
                        resetGame();
                        break;
                    }
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(3));
                    g2.drawLine((int) pixel1.x(), (int) pixel1.y(), (int) pixel2.x(), (int) pixel2.y());
                    pixel1 = pixel2;
                }
            }
        }

//        System.out.println("Finished paint");
    }
    private static void showMessageToScreen(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }

    private void resetGame() {
        this.game = new Game();
        if(linesSolution!=null) //if we have a solution after running algorithm, we will have to erase its paths .
            linesSolution.getPaths().clear();
        if(ourJFrame.paintThread != null){
            ourJFrame.paintThread.stopAnimKillThread();
        }
        ourJFrame.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman and Fruits");
        ourJFrame = new MyFrame();
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
        MenuItem reset = new MenuItem("Reset");



        fruitItemMenu.addActionListener(e -> ourJFrame.typeToAdd = 2);
        pacmenItemMenu.addActionListener((e -> ourJFrame.typeToAdd = 1));

        //reset clicked
        reset.addActionListener(e -> {
            ourJFrame.resetGame();
            ourJFrame.repaint();
        });
        addMenu.add(reset);
        addMenu.add(pacmenItemMenu);
        addMenu.add(fruitItemMenu);

        MenuItem loadFromCsvItemMenu = new MenuItem("Load From CSV");
        MenuItem saveToCsvItemMenu = new MenuItem("Save To CSV");
        MenuItem exportToKML = new MenuItem("Export to KML");

        MenuItem run = new MenuItem("run");

        algoMenu.add(run);


        //load file
        fileMenu.add(loadFromCsvItemMenu);
        loadFromCsvItemMenu.addActionListener(e->{
            if(ourJFrame.paintThread != null){ //if we have a thread painting in the background, we will stop the animation and kill the thread.
                ourJFrame.paintThread.stopAnimKillThread();
            }
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
                System.out.println("No file selected.");
            }
        });

        //save file
        fileMenu.add(saveToCsvItemMenu);
        saveToCsvItemMenu.addActionListener(e->{
            if(ourJFrame.paintThread != null){ //if we have a thread painting in the background, we will stop the animation and kill the thread.
                ourJFrame.paintThread.stopAnimKillThread();
            }
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
                System.out.println("Cancel button pressed.");
            }
        });

        //export to kml clicked
        fileMenu.add(exportToKML);
        exportToKML.addActionListener(e->{
            if(ourJFrame.paintThread != null){ //if we have a thread painting in the background, we will stop the animation and kill the thread.
                ourJFrame.paintThread.stopAnimKillThread();
            }
            if(linesSolution==null){
                showMessageToScreen("You have to run the algorithm first to find the paths solution.\n" +
                        "After that, you can try to export again.");
            }
            else {
                String fileName = JOptionPane.showInputDialog("Enter name for your kml file (without extension): ");
                Path2KML toKml = new Path2KML();
                toKml.constructKML(fileName, linesSolution, ourJFrame.game);
            }
        });

        //run algo clicked
        run.addActionListener(l->{
            if(ourJFrame.paintThread != null && ourJFrame.paintThread.isKeepGoing()){ //if we have a thread painting in the background, we will stop the animation and kill the thread.
                ourJFrame.paintThread.stopAnimKillThread();
            }
            try {
                ourJFrame.runAlgo();
            }catch (RuntimeException e){
                JOptionPane.showMessageDialog(null, e.getMessage());

            }
        });
        MainMenu.add(algoMenu);
        MainMenu.add(fileMenu);
        MainMenu.add(addMenu);

    }

    /**
     * this method execute by the Menu,
     * check if there are packmans or fruits in the game.
     * we consider that everytime the HashSet will be ordred different each time, and we take this HashSet into an Array.
     * and then we run the algorithm on this shuffled array.
     * we save the time to complete the solution and the solution,
     * if the timeToComplete is lower then the lowest time we got untill now, we will save the new Solution and the new BestTime.
     */
    private void runAlgo() {
        if(this.game.getPacmen().size() == 0){
            throw new RuntimeException("No pacmen to calculate solution.");
        } else if(this.game.getFruits().size() == 0){
            throw new RuntimeException("No fruits to calculate solution.");
        }
        ArrayList<GIS_element> packmen = new ArrayList<>(this.game.getPacmen());
        Solution bestSolution = null;
        long bestTime = Long.MAX_VALUE;
        for(int i=0;i<packmen.size()*game.getFruits().size()*2;i++) { //TODO: change to get faster speed -> less optimized solution.
            Collections.shuffle(packmen);
            ShortestPathAlgo algo = new ShortestPathAlgo(packmen,game.getFruits());
            Solution algoSolution = algo.runAlgo();
            if (bestTime > algoSolution.timeToComplete()) {
                bestSolution = algoSolution;
                bestTime = (long)algoSolution.timeToComplete();
            }
        }
        linesSolution = bestSolution;
        resetTimeAfterAlgoAndSetEatenTimes(linesSolution);
        System.out.println(linesSolution); //TODO: delete this.
        System.out.println("Total time to complete all paths: " + linesSolution.timeToComplete()/1000);
        paintThread = new Painter(bestSolution,ourJFrame);
        Thread repainter = new Thread(paintThread);
        repainter.start();

    }

    /**
     * Because we run the algo couple times, we need to set the eating times for each packman, since they are eating from the
     * beginning each run of the algorithm
     * @param solution
     */
    private void resetTimeAfterAlgoAndSetEatenTimes(Solution solution){
        Iterator<Path> paths = solution.getPaths().iterator();
        while (paths.hasNext()) {
            Path pt = paths.next();
            pt.getPacmanInPath().getData().setUTCtime(solution.getTimeStart()); //reset pacman time to the time of best algorithm start time.
            Iterator<Fruit> frIt = pt.getFruitsInPath().iterator();
            while(frIt.hasNext()){
                Fruit frInPath = frIt.next();
                frInPath.getData().setUTCtime(solution.getTimeStart());//reset fruit time to the time of best algorithm start time.
                frInPath.setTimeToEat((long)(pt.getDistance(pt.getFruitsInPath().indexOf(frInPath))/pt.getPacmanInPath().getSpeed()*1000)); //set eaten time for fruit in specific path in best algo solution.
            }
        }
    }

    /**
     * This function will save our Game into csv file.
     * @param file - get File Object to save the game into.
     */
    private void saveFile(File file) {
        this.game.saveGameToCsv(file.getAbsolutePath());
    }

    /**This function will get File object, and load it into our game.
     *
     * @param file - File to load
     */
    private void loadFile(File file) {
        resetGame();
        try {
            this.game = new Game(file);
            repaint();
            //
        }catch (Exception e){ //CSV might be corrupted or not supported.
            showMessageToScreen("This CSV file is not compatible with our game.");
            resetGame();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(ourJFrame.paintThread != null && ourJFrame.paintThread.isKeepGoing()){ //if we have a thread painting in the background, we will stop the animation and kill the thread.
            ourJFrame.paintThread.stopAnimKillThread();
            showMessageToScreen("You clicked to add into the map while animation was running.\n" +
                    "We will stop the animation now.");
        }
        if (typeToAdd == 1) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element pacman_meta = new Meta_data_element("Packman name", "P"); //color is white as default.
            Packman pac = new Packman(globalpoint, pacman_meta,this.game.getIDpacs(), 1, 1); //orientation is (1,1,1) as default.
            this.game.setIDpacs(this.game.getIDpacs()+1);
            game.getPacmen().add(pac);
        }
        if (typeToAdd == 2) {
            Point3D point = new Point3D(e.getX(), e.getY(), 0);
            Point3D globalpoint2 = map.PixelsToCoords(point, getHeight(), getWidth());
            Meta_data_element fruit_meta = new Meta_data_element("Fruit name", "F"); //color is red as default.
            Fruit fruit = new Fruit(globalpoint2,fruit_meta,this.game.getIDfruits(),1); //default weight for fruit
            this.game.setIDfruits(this.game.getIDfruits()+1);
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