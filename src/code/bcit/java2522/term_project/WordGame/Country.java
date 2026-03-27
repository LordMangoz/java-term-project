package bcit.java2522.term_project.WordGame;

/**
 * Country
 */
public class Country
{
    private final String name;
    private final String capitalCityName;
    private final String[] facts;


    Country(final String name, final String capitalCityName, final String[] facts)
    {

        validateCapitalCityName();
        validateName();
        validateFacts();

        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    /*
    * Validates the Facts
    *
    * Constraints
    *   - Cannot be null.
    */
    private void validateFacts()
    {

    }

    /*
    * Validate the names.
    *
    * Constraints
    *  -
    *  -
    */
    private void validateName()
    {

    }


    /*
    * Validates the capital city name
    *
    * Constraints
    *  - Cannot be null
    *  - Cannot be Empty.
    */
    private void validateCapitalCityName()
    {

    }


    /**
     * Getter for the capital city name.
     * @return capitalCityName.
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Getter the name.
     * @return name
     */
    public String getName() {
        return name;
    }

    public String[] getFacts() {
        return facts;
    }
}
