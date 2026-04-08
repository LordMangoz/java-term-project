package bcit.java2522.term_project.WordGame;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
        final Path dir = Path.of("src","resources", "countries").toAbsolutePath();
//        countries = new HashMap<>();
        createCountries(dir);
    }

    private void createCountries(final Path dir)
    {
        System.out.println("full directory: " + dir.toAbsolutePath());
        System.out.println("exist status of directory" + Files.exists(dir));

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

            ArrayList<String> countryDataList;
//            countryDataList =  linesArr.stream().
//                                filter(x -> (x.contains(":") && !x.contains(".") &&
//                                !(linesArr.get(linesArr.indexOf(x)+1).isEmpty()) &&
//                                !(linesArr.get(linesArr.indexOf(x)+2).isEmpty()) &&
//                                !(linesArr.get(linesArr.indexOf(x)+3).isEmpty()))).
//                                collect(Collectors.toCollection(ArrayList::new));
//
//            countryDataList.forEach(System.out::println);

//            linesArr.stream().forEach(x -> {
//                if ((x.contains(":") && !x.contains(".")))
//                {
//                    makeCountry((ArrayList<String>) linesArr.subList(linesArr.indexOf(x), linesArr.indexOf(x+3)));
//                    System.out.println();
//                }
//            });
            String currentLine;
            ArrayList<String> currentCountryList;
            System.out.println("\n\n\n\n\ncurrentFilePath" + filePath.toString());
            for (int i = 0; i < linesArr.size(); i++)
            {
                currentLine = linesArr.get(i);
                if (currentLine.contains(":") && !(currentLine.contains(".")))
                {

                    currentCountryList = new ArrayList<>(linesArr.subList(i, i+3));
                    makeCountry(currentCountryList);
                    System.out.println("iteration Number:" + i);
                    System.out.println(currentCountryList.toString());
                }
            }

            //need to grab the block of country.
            //then run command to create country ( another method lol)
            // just checks if the assignment ends up with a null result, telling us we reached the end of the file.

        }
        catch(java.io.IOException e)
        {
            System.out.println("Error \"" + e + "\" could not be located." );
        }
    }

    private void makeCountry(final ArrayList<String> countryData)
    {

    }


}
