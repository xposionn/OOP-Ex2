package Game;

import java.io.File;
import java.util.HashSet;

public class Game {
    HashSet<Fruit> fruits;
    HashSet<Packman> packmen;

    /**
     * Constructor to build the game from a saved CSV file. loads the data and creates the object.
     * @param csvGameFile CSV file to load.
     */
    public Game(File csvGameFile) {
    }

    /**
     * Saves current game state into CSV file. returns CSV file path.
     * @return file path for CSV file.
     */
    public String saveGameToCsv(){
        return "csv file path";
    }
}
