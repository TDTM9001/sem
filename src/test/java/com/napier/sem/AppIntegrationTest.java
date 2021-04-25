package com.napier.sem;

import locations.Country;
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
    void testGetEmployee()
    {
        String server_name = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
        Country[] countryList = app.GetCountryData(server_name);
        Country country = countryList[0];
        assertEquals(country.getName(), "Aruba");
    }
}
