package File_format;

import Algorithms.Solution;
import Algorithms.TimeChange;
import GIS.GIS_layer;
import Game.Fruit;
import Game.Game;
import Game.Packman;
import Geom.Path;
import Geom.Point3D;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Path2KML {

    private String styleStringForKML(Solution solution){
        StringBuilder kmlString = new StringBuilder();
        kmlString.append("<Style id=\"pacman-icon\">\n" +
                "      <IconStyle>\n" +
                "        <Icon>\n" +
                "          <href>https://i.imgur.com/vfKFip4.png</href>\n" +
                "        </Icon>\n" +
                "        <hotSpot x=\"0\" y=\".5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "      </IconStyle>\n" +
                "    </Style>");
        kmlString.append("<Style id=\"default-icon\">\n" +
                "      <IconStyle>\n" +
                "        <Icon>\n" +
                "          <href>https://i.imgur.com/dA36Dhf.png</href>\n" +
                "        </Icon>\n" +
                "        <hotSpot x=\"0\" y=\".5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "      </IconStyle>\n" +
                "    </Style>");
        for(Path path: solution.getPaths()) {
            kmlString.append("<Style id=\"" + path.getPacmanInPath().getID() + "PathColor\">");
            kmlString.append("<LineStyle>");
            kmlString.append("<color>" + colorToKML(path.getColor()) + "</color>");
            kmlString.append("<width>4</width>\n"); //change value for requested line width.
            kmlString.append("</LineStyle>\n");
            kmlString.append("<PolyStyle>\n");
            kmlString.append("<color>" + colorToKML(path.getColor()) + "</color>\n");
            kmlString.append("</PolyStyle>\n");
            kmlString.append("</Style>\n");
        }
        return kmlString.toString();
    }

    private String onePathToKMLstring(Path path){
        StringBuilder kmlSTRING = new StringBuilder();

        kmlSTRING.append("<Placemark>\n");
        kmlSTRING.append("<name>Path For Pacman: "+path.getPacmanInPath().getID()+"</name>\n");
        kmlSTRING.append("<description>Path for a pacman eating fruits</description>\n");
        kmlSTRING.append("<styleUrl>#" + path.getPacmanInPath().getID() + "PathColor"+"</styleUrl>\n");
        kmlSTRING.append("<LineString>\n");
        kmlSTRING.append("<extrude>1</extrude>\n"); //change if you want to draw without walls
        kmlSTRING.append("<tessellate>1</tessellate>\n");  //doesn't use it, only with clampToGround altitude mode.
        kmlSTRING.append("<altitudeMode>relativeToGround</altitudeMode>\n"); //relative to ground
        kmlSTRING.append("<coordinates>\n");
        kmlSTRING.append(((Point3D)path.getPacmanInPath().getGeom()).toStringKMLgoogle()); //pacman path is the first coordinate in our path.
        kmlSTRING.append("\n");
        Iterator<Fruit> fruitIt = path.getFruitsInPath().iterator();
        while(fruitIt.hasNext()){
            kmlSTRING.append(((Point3D)fruitIt.next().getGeom()).toStringKMLgoogle());
            kmlSTRING.append("\n");
        }
        kmlSTRING.append("</coordinates>\n");
        kmlSTRING.append("</LineString>\n");
        kmlSTRING.append("</Placemark>\n");

        return kmlSTRING.toString();
        /** Example:
         * <Style id="yellowLineGreenPoly">
         *       <LineStyle>
         *         <color>7f00ffff</color>
         *         <width>4</width>
         *       </LineStyle>
         *       <PolyStyle>
         *         <color>7f00ff00</color>
         *       </PolyStyle>
         *     </Style>
         *
         * <Placemark>
         *       <name>Absolute Extruded</name>
         *       <description>Transparent green wall with yellow outlines</description>
         *       <styleUrl>#yellowLineGreenPoly</styleUrl>
         *       <LineString>
         *         <extrude>1</extrude>
         *         <tessellate>1</tessellate>
         *         <altitudeMode>relativeToGround</altitudeMode>
         *         <coordinates> -112.2550785337791,36.07954952145647,30
         *           -112.2549277039738,36.08117083492122,30
         *           -112.2552505069063,36.08260761307279,30000
         *           -112.2564540158376,36.08395660588506,0
         *           -112.2580238976449,36.08511401044813,30
         *           -112.2595218489022,36.08584355239394,0
         *           -112.2608216347552,36.08612634548589,0
         *           -112.262073428656,36.08626019085147,0
         *           -112.2633204928495,36.08621519860091,0
         *           -112.2644963846444,36.08627897945274,2
         *           -112.2656969554589,36.08649599090644,2320
         *         </coordinates>
         *       </LineString>
         *     </Placemark>
         */
    }

    public String solutionPathsToKML(Solution pathSolution){
        StringBuilder allPathsKMLstring = new StringBuilder();
        allPathsKMLstring.append("<Folder>\n");
        allPathsKMLstring.append("<name>All Paths </name>\n");
        for (Path path : pathSolution.getPaths()) {
            allPathsKMLstring.append(onePathToKMLstring(path));
        }
        allPathsKMLstring.append("</Folder>");

        return allPathsKMLstring.toString();
    }

    public String fruitsToKML(GIS_layer fruits){
        return fruits.toKmlForProject(); //from EX2.
    }

    public String pacmenMovementKML(Solution pathSolution){
        StringBuilder kmlString = new StringBuilder();
        double timeToComplete = pathSolution.timeToComplete();
        for (Path path : pathSolution.getPaths()) {
            kmlString.append("<Folder><name>Snapshots for Pacman: " + path.getPacmanInPath().getID()+"</name>");
            Packman pac = path.getPacmanInPath();
            for(int i=0;i<timeToComplete/1000;i++){ //each iteration is 1 second.
                Point3D newPosition = path.getPacPositionAfterXtime(i*1000);
                kmlString.append("<Placemark>\n" +
                        "<name>" + pac.getID() + "</name>\n" +
                        "<description>" + pac.getData().toStringKML() +
                        "Speed:"+ pac.getSpeed()+ "<br/>" +
                        "Eat Radius:" + pac.getEatRadius()+ "<br/>" +
                        "</description>\n" +
                        "<styleUrl>" + "#pacman-icon" + "</styleUrl>\n" + //colorToKML(Color.decode(pac.getData().getColor())) TODO: we can use the pacman object color
                        "<TimeSpan><begin>" + TimeChange.longtoUTC(pac.getData().getUTC()+i*1000) + "</begin><end>"+TimeChange.longtoUTC(pac.getData().getUTC()+(i+1)*1000)+"</end></TimeSpan>\n" +
                        "<Point>\n" +
                        "<coordinates>" + newPosition.toStringKMLgoogle()+ "</coordinates>\n" + //0 at Z is relative to ground height
                        "</Point>\n" +
                        "</Placemark>\n");
            }
            kmlString.append("</Folder>");
        }
        return kmlString.toString();
    }

    public void constructKML(String fileNameForKML, Solution pathSolution, Game game) {
        StringBuilder kmlContent = new StringBuilder();
        kmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "  <Document>\n");

        kmlContent.append(styleStringForKML(pathSolution)); //add all styles for paths.

        kmlContent.append(solutionPathsToKML(pathSolution)); //paths layer completed.

        kmlContent.append(fruitsToKML(game.getFruits())); //fruits layer completed

        kmlContent.append(pacmenMovementKML(pathSolution));

        kmlContent.append("</Document></kml>");

        try {
            FileWriter fw = new FileWriter(fileNameForKML+".kml");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(kmlContent.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String colorToKML(Color c){
        int red1 = c.getRed();
        int green3 = c.getGreen();
        int blue2 = c.getBlue();
        StringBuilder red = new StringBuilder(Integer.toHexString(red1));
        StringBuilder green = new StringBuilder(Integer.toHexString(green3));
        StringBuilder blue = new StringBuilder(Integer.toHexString(blue2));
        red = red.reverse();
        green = green.reverse();
        blue = blue.reverse();
        StringBuilder a = new StringBuilder();
        a.append(red).append(blue).append(green);
        return "ff"+a.reverse().toString();
    }



}




