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

    private void getCountry(final Path filePath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            final List<String> linesArr;
            linesArr = new ArrayList<>();
            bufferedReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .forEach(linesArr::add);

            for (int i = 0; i + 3 < linesArr.size(); i += 4) {
                makeCountry(new ArrayList<>(linesArr.subList(i, i + 4)));
            }
        } catch (IOException e) {
            System.out.println("Error \"" + e + "\" could not be located.");
        }
    }

    private void makeCountry(final ArrayList<String> countryData)
    {
        final String headerLine = countryData.get(0);

        final int colonPosition = headerLine.indexOf(':');
        if (colonPosition == -1)
        {
            return;
        }

        final String countryName = headerLine.substring(0, colonPosition).trim();
        final String capitalName = headerLine.substring(colonPosition + 1).trim();

        final String[] facts = new String[3];
        facts[0] = countryData.get(1).trim();
        facts[1] = countryData.get(2).trim();
        facts[2] = countryData.get(3).trim();

        countryHashMap.put(countryName, new Country(countryName, capitalName, facts));
    }


    public Map<String, Country> getCountryHashMap() {
        return countryHashMap;
    }

}
