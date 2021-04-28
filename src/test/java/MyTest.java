import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import locations.*;

class MyTest
{
    @Test
    void cityTestSort()
    {
        City testCity = new City("TestCity",1000, "ABC", 123,"Hampshire" );
        City newTestCity = new City("NewTestCity", 500, "ZYX", 456, "Berkshire");
        City finalTestCity = new City("FinalTestCity", 7250, "LMN", 789, "Devon");

        City[] arr = {testCity, finalTestCity, newTestCity};
        City[] sorted = City.sortByPopulation(arr);
        assertEquals(7250, sorted[0].getPopulation());
    }

    @Test
    void cityTestDistrict()
    {
        City testCity = new City("TestCity",1000, "ABC", 123,"Hampshire" );
        City newTestCity = new City("NewTestCity", 500, "ZYX", 456, "Berkshire");
        City finalTestCity = new City("FinalTestCity", 7250, "LMN", 789, "Devon");
        City anotherTestCity = new City("AnotherTestCity", 22500, "HAB", 157, "Hampshire");

        City[] arr = {testCity, newTestCity, finalTestCity, anotherTestCity};
        int counter = City.getAllByDistrict(0,arr,"Hampshire").length;
        assertEquals(2, counter);

    }

    @Test
    void countryTestSort()
    {
        Country testCountry = new Country("TestCountry",1000, "ABC", "europe","South", 123);
        Country newTestCountry = new Country("NewTestCountry", 500, "ZYX", "europe", "South", 123);
        Country finalTestCountry = new Country("FinalTestCountry", 7250, "LMN", "america", "Britain", 123);

        Country[] arr = {testCountry, newTestCountry, finalTestCountry};
        Country[] sorted = Country.sortByPopulation(arr);

        assertEquals(7250, sorted[0].getPopulation());
    }

    @Test
    void countryTestContinent()
    {
        Country testCountry = new Country("TestCountry",1000, "ABC", "europe","South", 123);
        Country newTestCountry = new Country("NewTestCountry", 500, "ZYX", "europe", "South", 123);
        Country finalTestCountry = new Country("FinalTestCountry", 7250, "LMN", "america", "Britain", 123);

        Country[] arr = {testCountry, newTestCountry, finalTestCountry};

        int counter = Country.getAllByContinent(0, "europe", arr).length;
        assertEquals(2, counter);
    }

    @Test
    void unitTest10()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }
}