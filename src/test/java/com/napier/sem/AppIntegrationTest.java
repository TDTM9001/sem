package com.napier.sem;

import locations.City;
import locations.Country;
import locations.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void testGetCountry()
    {
        String server_name = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
        Country[] countryList = app.GetCountryData(server_name);
        Country country = countryList[0];
        assertEquals(country.getName(), "Aruba");
    }

    @Test
    void testGetCity()
    {
        String server_name = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
        City[] cityList = app.GetCityData(server_name);
        City city = cityList[0];
        assertEquals(city.getName(), "Kabul");
    }

    @Test
    void testGetLanguage()
    {
        String server_name = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
        Language[] languageList = app.GetLanguageData(server_name);
        Language language = languageList[0];
        assertEquals(language.getLanguage(), "Dutch");
    }
}
