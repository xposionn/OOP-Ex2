package File_format;

import Algorithms.Solution;
import GIS.GIS_layer;
import Game.Fruit;
import Game.Game;
import Geom.Path;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Path2KML {


    private String onePathToKMLstring(Path path){
        StringBuilder kmlSTRING = new StringBuilder();

        kmlSTRING.append("<Style id="+path.getPacmanInPath().getID() + "PathColor");
        kmlSTRING.append("<LineStyle>");
        kmlSTRING.append("<color>"+ path.getColor()); //TODO: add color and complete style tag.


        kmlSTRING.append("<width>4</width>\n"); //change value for requested line width.
        kmlSTRING.append("</LineStyle>\n");
        kmlSTRING.append("<PolyStyle>\n");
        kmlSTRING.append("<color>");

        kmlSTRING.append("</color>\n");
        kmlSTRING.append("</PolyStyle>\n");
        kmlSTRING.append("</Style>\n");


        /**** finished styling, now for path data:   ****/

        kmlSTRING.append("<Placemark>\n");
        kmlSTRING.append("<name>Line For Pacman: "+path.getPacmanInPath().getID()+"</name>\n");
        kmlSTRING.append("<description>Path for a pacman eating fruits</description>\n");
        kmlSTRING.append("<styleUrl>" + path.getPacmanInPath().getID() + "PathColor"+"</styleUrl>\n");
        kmlSTRING.append("<LineString>\n");
        kmlSTRING.append("<extrude>1</extrude>\n"); //change if you want to draw without walls
        kmlSTRING.append("<tessellate>1</tessellate>\n");  //doesn't use it, only with clampToGround altitude mode.
        kmlSTRING.append("<altitudeMode>relativeToGround</altitudeMode>\n"); //relative to ground
        kmlSTRING.append("<coordinates>\n");
        kmlSTRING.append(path.getPacmanInPath().getGeom()); //pacman path is the first coordinate in our path.
        Iterator<Fruit> fruitIt = path.getFruitsInPath().iterator();
        while(fruitIt.hasNext()){
            kmlSTRING.append(fruitIt.next().getGeom());
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

    public void constructKML(String fileNameForKML, Solution pathSolution, Game game) {
        StringBuilder kmlContent = new StringBuilder();
        kmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "  <Document>");
        kmlContent.append(solutionPathsToKML(pathSolution)); //paths layer completed

        kmlContent.append(fruitsToKML(game.getFruits())); //fruits layer completed

        //TODO: add packmen layer with snapshop according to time

        kmlContent.append("</Document></kml>");

        try {
            FileWriter fw = new FileWriter(fileNameForKML);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(kmlContent.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}




