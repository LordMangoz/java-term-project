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

        validateCapitalCityName(capitalCityName);
        validateName(name);
        validateFacts(facts);

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
    private void validateFacts(final String[] facts)
    {
        if (facts == null)
        {
            throw new IllegalArgumentException("Cannot be Null");
        }
    }

    /*
    * Validate the names.
    *
    * Constraints
    *  - Cannot be null.
    *  - Cannot empty.
    */
    private void validateName(final String name)
    {
        if( name == null ||
            name.isEmpty())
        {
            throw new IllegalArgumentException("Invalid name.");
        }
    }

    /*
    * Validates the capital city name
    *
    * Constraints
    *  - Cannot be null
    *  - Cannot be empty.
    */
    private void validateCapitalCityName(final String capitalCityName)
    {
        if( capitalCityName == null ||
            capitalCityName.isEmpty())
        {
            throw new IllegalArgumentException("Invalid name.");

        }
    }

    public String getCountryName()
    {
        return name;
    }


    /**
     * Gets for the capital city name.
     *
     * @return capitalCityName of the country.
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }



    /**
     * Getter for country facts.
     *
     * @return facts about country.
     */
    public String[] getFacts() {
        return facts;
    }
    public String getFact(final int index) {
        return facts[index];
    }
}
