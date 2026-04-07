package bcit.java2522.term_project.WordGame;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


/**
 * Represents all the country data.
 * @author Umanga Bajgai
 */
public class World
{


//    final private Map<Country, String> countries;
    public World()
    {
        final Path dir = Path.of("countries");

//        countries = new HashMap<>();

        createCountries(dir);
    }

    private void createCountries(final Path dir)
    {
        //getting the file names in the directory.

        try(final Stream <Path> dirFileNames = Files.list(dir))
        {
            dirFileNames.forEach(this::createCountry);
        }
        catch(java.io.IOException e) {
            System.out.println(e + "");
        }

    }

    private void createCountry(Path dir)
    {
        //create a buffered reader and read the file.

        Boolean countryAndCapital;
        try (BufferedReader bufferedReader = Files.newBufferedReader(dir))
        {
            final Stream<String> lines;
            lines = bufferedReader.lines();
            if (lines == null)
            {
                return;
            }
            lines.
                //then facts stored in country array list.
                //if line is a new line/ just white space we know next line is country: captial city.

        }
        catch(java.io.IOException e)
        {
            System.out.println("Error \"" + e + "\" could not be located." );
        }
    }

}
