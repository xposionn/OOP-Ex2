package GUI;

import Algorithms.Solution;
import Geom.Path;

import javax.swing.*;
import java.util.Iterator;


public class Painter implements Runnable {

    /**
     * This class represent the painter.
     * this painter will be running into Thread.
     * his job is to run repaint method for repainting the GUI window in different Thread because we dont want the GUI
     * will be stuck.
     */
    private Solution solution;
    private JFrameGraphics frame;

    public Painter(Solution sol, JFrameGraphics framer) {
        this.solution = sol;
        this.frame = framer;
    }

    @Override
    public void run() {
        long startingTime = System.currentTimeMillis();
        while ( System.currentTimeMillis() - startingTime < solution.timeToComplete()) {
            String timeToRun = JOptionPane.showInputDialog("Enter for how long do you want to show path animation in milliseconds: \n" +
                    "Enter 0 for full animation.");
            long timeToPlay = 0;
            try {
                timeToPlay = Long.parseLong(timeToRun);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Only numbers are allowed! Enter milli-seconds to run animation.");
                timeToPlay = 0;
            }
            String fpsString = JOptionPane.showInputDialog("Enter how much FPS (Frames per second) you want to run the animation with: " +
                    "\nDefault FPS is set to 60. Max is 144 fps. (Your screen probably doesn't support more than that.)");

            int FPS = 60;
            try {
                FPS = Integer.parseInt(fpsString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Only numbers are allowed! Enter positive integer for your wanted FPS.");
            }
            if (FPS <= 0 || FPS > 144) { //regular checks for bypassing.
                JOptionPane.showMessageDialog(null, "You entered invalid FPS value. We will set it to 60FPS. Have fun.");
                FPS = 60;
            }
            if (timeToPlay == 0) {
                timeToPlay = (long) solution.timeToComplete();
            }
            long startTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();
            while (currentTime - startTime < timeToPlay) {
                Iterator<Path> pathIt = solution.getPaths().iterator();
                while (pathIt.hasNext()) {
                    Path path = pathIt.next();
//                        System.out.println("Pacman id: " + path.getPacmanInPath().getID() + " Pos:" + path.getPacmanInPath().getGeom());
                    path.getPacmanInPath().setGeom(path.getPacPositionAfterXtime((System.currentTimeMillis() - startTime) * 10)); /**DO NOT CHANGE
                     This is calculated REAL-TIME movement of Pacman. separately from FPS. Thread sleeping provides the FPS on screen.**/

                    currentTime = System.currentTimeMillis();
                }
                frame.paintImmediately(0, 0, frame.getWidth(), frame.getHeight());
                try {
                    Thread.sleep(1000 / FPS);  //FPS determined here. 60 FPS is default.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

