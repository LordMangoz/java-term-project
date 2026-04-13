package bcit.java2522.term_project.WordGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents all the country data used in the WordGame.
 * Loads countries from text files stored in the resources folder.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class World
{
    /** Directory containing country data files. */
    private static final Path COUNTRY_DIRECTORY =
            Path.of("src", "resources", "countries").toAbsolutePath();

    /** Index for the first element. */
    private static final int FIRST_INDEX = 0;

    /** Offset for the next index. */
    private static final int NEXT_INDEX_OFFSET = 1;

    /** Number of facts per country. */
    private static final int FACT_COUNT = 3;

    /** Total number of lines per country entry. */
    private static final int COUNTRY_ENTRY_LINES = 4;

    /** Header line index. */
    private static final int HEADER_INDEX = 0;

    /** Fact line 1 index. */
    private static final int FACT_ONE_INDEX = 1;

    /** Fact line 2 index. */
    private static final int FACT_TWO_INDEX = 2;

    /** Fact line 3 index. */
    private static final int FACT_THREE_INDEX = 3;

    /** Index for the first fact in the facts array. */
    private static final int FACT_ARRAY_FIRST_INDEX = 0;

    /** Index for the second fact in the facts array. */
    private static final int FACT_ARRAY_SECOND_INDEX = 1;

    /** Index for the third fact in the facts array. */
    private static final int FACT_ARRAY_THIRD_INDEX = 2;

    /** Stores all countries. Key is the country name. */
    private final Map<String, Country> countryHashMap;

    /**
     * Constructs a World and loads all countries into the HashMap.
     */
    public World()
    {
        countryHashMap = new HashMap<>();
        createCountries(COUNTRY_DIRECTORY);
    }

    /*
     * Reads all country files in the given directory and loads them into the HashMap.
     */
    private void createCountries(final Path dir)
    {
        try (Stream<Path> dirFileNames = Files.list(dir))
        {
            dirFileNames.forEach(this::getCountry);
        }
        catch (final IOException e)
        {
            System.out.println("Error: could not load country directory.");
        }
    }

    /*
     * Reads one file and extracts all country entries from it.
     */
    private void getCountry(final Path filePath)
    {
        final List<String> linesArr;

        linesArr = new ArrayList<>();

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath))
        {
            bufferedReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .forEach(linesArr::add);

            for (int i = FIRST_INDEX;
                 i + FACT_COUNT < linesArr.size();
                 i += COUNTRY_ENTRY_LINES)
            {
                makeCountry(new ArrayList<>(linesArr.subList(i, i + COUNTRY_ENTRY_LINES)));
            }
        }
        catch (final IOException e)
        {
            System.out.println("Error: could not read file " + filePath.getFileName());
        }
    }

    /*
     * Creates a Country object from a list of lines and stores it in the HashMap.
     *
     * Expected format:
     *  Line 1: CountryName: CapitalName
     *  Line 2-4: Facts
     */
    private void makeCountry(final ArrayList<String> countryData)
    {
        final String headerLine;
        final int colonPosition;

        headerLine = countryData.get(HEADER_INDEX);
        colonPosition = headerLine.indexOf(':');

        if (colonPosition == -1)
        {
            return;
        }

        final String countryName;
        final String capitalName;
        final String[] facts;

        countryName = headerLine.substring(FIRST_INDEX, colonPosition).trim();
        capitalName = headerLine.substring(colonPosition + NEXT_INDEX_OFFSET).trim();

        facts = new String[FACT_COUNT];
        facts[FACT_ARRAY_FIRST_INDEX] = countryData.get(FACT_ONE_INDEX).trim();
        facts[FACT_ARRAY_SECOND_INDEX] = countryData.get(FACT_TWO_INDEX).trim();
        facts[FACT_ARRAY_THIRD_INDEX] = countryData.get(FACT_THREE_INDEX).trim();

        countryHashMap.put(countryName, new Country(countryName, capitalName, facts));
    }

    /**
     * Gets the HashMap containing all countries.
     *
     * @return the HashMap of countries
     */
    public Map<String, Country> getCountryHashMap()
    {
        return countryHashMap;
    }
}