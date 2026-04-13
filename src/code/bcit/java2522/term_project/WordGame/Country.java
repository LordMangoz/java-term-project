package bcit.java2522.term_project.WordGame;

/**
 * Represents a Country used in the WordGame.
 * Stores the country name, its capital city name, and facts.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public class Country
{
    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    /**
     * Constructs a Country.
     *
     * @param name the name of the country
     * @param capitalCityName the capital city name of the country
     * @param facts the facts about the country
     */
    public Country(final String name,
                   final String capitalCityName,
                   final String[] facts)
    {
        validateName(name);
        validateCapitalCityName(capitalCityName);
        validateFacts(facts);

        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    /*
     * Validates the facts array.
     *
     * Constraints:
     *  - Cannot be null.
     */
    private void validateFacts(final String[] facts)
    {
        if (facts == null)
        {
            throw new IllegalArgumentException("Facts cannot be null.");
        }
    }

    /*
     * Validates the country name.
     *
     * Constraints:
     *  - Cannot be null.
     *  - Cannot be empty.
     */
    private void validateName(final String name)
    {
        if (name == null ||
            name.isEmpty())
        {
            throw new IllegalArgumentException("Invalid country name.");
        }
    }

    /*
     * Validates the capital city name.
     *
     * Constraints:
     *  - Cannot be null.
     *  - Cannot be empty.
     */
    private void validateCapitalCityName(final String capitalCityName)
    {
        if (capitalCityName == null ||
            capitalCityName.isEmpty())
        {
            throw new IllegalArgumentException("Invalid capital city name.");
        }
    }

    /**
     * Gets the country name.
     *
     * @return the country name
     */
    public String getCountryName()
    {
        return name;
    }

    /**
     * Gets the capital city name.
     *
     * @return the capital city name
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Gets the facts about the country.
     *
     * @return the facts array
     */
    public String[] getFacts()
    {
        return facts;
    }

    /**
     * Gets a specific fact about the country.
     *
     * @param index the fact index
     * @return the fact at the given index
     */
    public String getFact(final int index)
    {
        return facts[index];
    }
}