package bcit.java2522.term_project.WordGame;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;


/**
 * Represents all the country data.
 * @author Umanga Bajgai
 */
public class World
{
    //the key is the country name, and stores a country object.
    private final Map<String, Country> countryHashMap;


    //    final private Map<Country, String> countries;
    public World()
    {
        final Path dir = Path.of("src","resources", "countries").toAbsolutePath();

        countryHashMap = new HashMap<>();

        createCountries(dir);

    }

    private void createCountries(final Path dir)
    {
//        System.out.println("full directory: " + dir.toAbsolutePath());
//        System.out.println("exist status of directory" + Files.exists(dir));

        //getting the file names in the directory.
        try(final Stream <Path> dirFileNames = Files.list(dir))
        {
            dirFileNames.forEach(this::getCountry);
        }
        catch(java.io.IOException e) {
            System.out.println(e + " error from createCountries");
        }

    }

    private void getCountry(Path filePath)
    {

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath))
        {
            final List<String> linesArr;
            final Stream<String> CountryArr;

            linesArr = new ArrayList<String>();
            bufferedReader.lines().forEach(linesArr::add);

            String currentLine;
            ArrayList<String> currentCountryList;
//            System.out.println("\n\n\n\n\ncurrentFilePath" + filePath.toString());
            for (int i = 0; i < linesArr.size(); i++)
            {
                currentLine = linesArr.get(i);
                if (currentLine.contains(":") && !(currentLine.contains(".")))
                {
                    if(i+4 <= linesArr.size()) {
                    currentCountryList = new ArrayList<>(linesArr.subList(i, i+4));
                    makeCountry(currentCountryList);
                    }
                }
            }

            //need to grab the block of country.
            //then run run functoin to crate country
            // just checks if the assignment ends up with a null result, telling us we reached the end of the file.

        }
        catch(java.io.IOException e)
        {
            System.out.println("Error \"" + e + "\" could not be located." );
        }
    }

    private void makeCountry(final ArrayList<String> countryData)
    {
        String countryName = null;
        String capitalName = null;
        final String[] facts;
        final Map<String, Country> hashmap;

        int colonPosition;
        hashmap = countryHashMap;
        facts = new String[3];
        int factsCounter = 0;

        for (String line : countryData)
        {
            //parse for country name and capital and initialize them.
            if (line == null)
            {
                continue;
            }
            if ((line.contains(":") && !line.contains(".")))
            {
                colonPosition = line.indexOf(":");

                countryName = line.substring(0, colonPosition).trim();
                capitalName = line.substring(colonPosition + 1).trim();
//                System.out.println(countryName + capitalName);
                continue;
            }
            if(!line.isBlank())
            {
                facts[factsCounter] = line;
                factsCounter++;
            }

            if( capitalName != null &&
                factsCounter == 3)
            {
                hashmap.put(countryName, new Country(countryName, capitalName, facts));
            }
        }

    }

    public Map<String, Country> getCountryHashMap() {
        return countryHashMap;
    }

}
