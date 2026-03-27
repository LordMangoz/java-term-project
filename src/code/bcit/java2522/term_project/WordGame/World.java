package bcit.java2522.term_project.WordGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
/**
 * Represents all the country data.
 * @author Umanga Bajgai
 */
public class World {

    public World()
    {
        createCountries();
    }

    /**
     * Creates country objects, based on data in resource files.
     *
     */
    private void createCountries()
    {
        final String resourceDirectory;
        final String fileEnding;
        String relativePath;
        String currentFile;

        char currentFileChar;
        File countryFile;

        resourceDirectory = "src/resources/";
        fileEnding = ".txt";
        currentFileChar = 'a';

        for (int i = 'a'; i < 'z'; i++)
        {
            relativePath = resourceDirectory + currentFileChar + fileEnding;
            countryFile = new File(relativePath);

            //for-each of the files, we need to create a country object for it.


            currentFileChar+=1;
        }



    }

}
