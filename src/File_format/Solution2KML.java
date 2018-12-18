package File_format;

import Algorithms.Solution;

import java.io.File;

public class Solution2KML {
    Solution solution;

    public Solution2KML(Solution solution) {
        this.solution = solution;
    }

    public void toKML(){
        double finishtime = solution.timeToComplete();
        //Create an empty file
        ClassLoader classLoader = getClass().getClassLoader();
//        File kml = new File(String.format("%s/gametoKML.kml", ClassLoader.getResource(".").getFile()));

    }

}
