package File_format;

import Game.Fruit;
import Geom.Path;
import Geom.Point3D;

import java.util.Iterator;

public class Path2KML {

    private Path path;

    public Path2KML(Path path) {
        this.path = path;
    }

    public String toKMLstring(){
        StringBuilder kmlSTRING = new StringBuilder();

        kmlSTRING.append("<Style id="+this.path.getPacmanInPath().getID() + "PathColor");
        kmlSTRING.append("<LineStyle>");
        kmlSTRING.append("<color>"+ this.path.getColor()); //TODO: add color and complete style tag.

        kmlSTRING.append("<Placemark>\n");
        kmlSTRING.append("<name>Line For Pacman: "+this.path.getPacmanInPath().getID()+"</name>\n");
        kmlSTRING.append("<description>Path for a pacman eating fruits</description>\n");
        kmlSTRING.append("<styleUrl>" + this.path.getPacmanInPath().getID() + "PathColor"+"</styleUrl>\n");
        kmlSTRING.append("<LineString>\n");
        kmlSTRING.append("<extrude>1</extrude>\n"); //change if you want to draw without walls
        kmlSTRING.append("<tessellate>1</tessellate>\n");  //doesn't use it, only with clampToGround altitude mode.
        kmlSTRING.append("<altitudeMode>relativeToGround</altitudeMode>\n"); //relative to ground
        kmlSTRING.append("<coordinates>\n");
        kmlSTRING.append(this.path.getPacmanInPath().getGeom()); //pacman path is the first coordinate in our path.
        Iterator<Fruit> fruitIt = this.path.getFruitsInPath().iterator();
        while(fruitIt.hasNext()){
            kmlSTRING.append(fruitIt.next().getGeom());
        }
        kmlSTRING.append("</coordinates>\n");
        kmlSTRING.append("</LineString>\n");
        kmlSTRING.append("</Placemark>\n");


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

        return kmlSTRING.toString();
    }

}
