package com.napier.sem;

import locations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param server_name
     */
    public void connect(String server_name)
    {
        try
        {
            // Load Database driver, crashes program if not found.
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 50;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(3000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:" + server_name, "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Get Countries from database and put into array.
     * @param server_name
     * @return [countries] Array of countries.
     */

    public Country[] GetCountryData(String server_name)
    {
        connect(server_name);   //Connects to the server
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM country"); //Counts how many countries there are, stores the number in length.
                while(rs.next()){
                    length = rs.getInt(1);
                }
            }
            catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            Country[] countries = new Country[length];  //Makes an array of the exact length to fit all countries.
            disconnect();
            connect(server_name);   //Reconnects to do new query.
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM country");    //Selects all countries
                int counter = 0;
                while(rs.next()) {  //Iterates through each country, and creating a new country object for each of them.
                    Country temp = new Country(rs.getString("Name"), rs.getInt("Population"), rs.getString("Code"), rs.getString("Continent"), rs.getString("Region"), rs.getInt("Capital"));
                    countries[counter] = temp;  //Stores the country object into array
                    counter++;
                }
            }
            catch (SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            disconnect();
            return countries;
        }
        else {
            return null;
        }
    }

    /**
     * Get Cities from database and put into array.
     * @param server_name
     * @return [cities] Array of cities.
     */

    public City[] GetCityData(String server_name)
    {
        connect(server_name);   //Connects to server.
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM city");    //Counts how many cities there are, stores the number in length.
                while(rs.next()){
                    length = rs.getInt(1);
                }
            }
            catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            City[] cities = new City[length];   //Makes an array of the exact length to fit all cities
            disconnect();
            connect(server_name);   //Reconnects to do new query.
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM city");   //Selects all cities
                int counter = 0;
                while(rs.next()) {  //Iterates through each city, and creating a new city object for each of them.
                    City temp = new City(rs.getString("Name"), rs.getInt("Population"), rs.getString("CountryCode"), rs.getInt("ID"), rs.getString("District"));
                    cities[counter] = temp; //Stores the city object into array
                    counter++;
                }
            }
            catch (SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            disconnect();
            return cities;
        }
        else {
            return null;
        }
    }

    /**
     * Get Languages from database and put into array.
     * @param server_name
     * @return [languages] Array of languages.
     */

    public Language[] GetLanguageData(String server_name)
    {
        connect(server_name);   //Connects to server.
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM countrylanguage"); //Counts how many languages there are, stores the number in length.
                rs.next();
                length = rs.getInt(1);
            }
            catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            disconnect();
            connect(server_name);   //Reconnects to do new query.
            Language[] languages = new Language[length];    //Makes an array of the exact length to fit all languages
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM countrylanguage");    //Selects all languages
                int counter = 0;
                while(rs.next()) {  //Iterates through each language, and creating a new language object for each of them.
                    Language temp = new Language(rs.getString("CountryCode"), rs.getString("Language"), rs.getString("IsOfficial").equals("T"), rs.getFloat("Percentage"));
                    languages[counter] = temp;  //Stores the language object into array
                    counter++;
                }
            }
            catch (SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            disconnect();
            return languages;
        }
        else {
            return null;
        }
    }

    public static void menu(City[] cities, Country[] countries, Language[] languages) throws IOException {
        System.out.println("Welcome to the Population Research App");  //Welcome message
        System.out.println("Please select a menu option:");
        System.out.println("1. Show Population from Largest to Smallest");
        System.out.println("2. Population of a given Category");
        System.out.println("3. Language Report");

        int switchMain = 0;
        int i, j;
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");  //Scanner delimiter is changed as default is " " (space)
        String inputString = "";
        Boolean inputCheck = false;
        while(inputCheck == false) {  //Will keep looping until input validation check has passed
            int wait_time = 30;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < wait_time * 1000 && !in.ready()) {
            }

            if (in.ready()) {
                try {
                    switchMain = Integer.parseInt(in.readLine());
                } catch (NumberFormatException e) {  //Try/Catch block added, skips input if user types string instead of an int
                }
            } else {
                System.out.println("You did not enter an option in time.");
                System.exit(0);
            }
            switch (switchMain) {
                case 1:  //Show Population from Largest to Smallest
                    int switchCategoryOne = 0;
                    int switchSearchOne = 0;
                    int switchLimit = 0;
                    int switchContinentOne = 0;
                    int inputLimit = 0;
                    System.out.println("Please select Category to Sort by:");
                    System.out.println("1. Sort Countries");
                    System.out.println("2. Sort Cities");
                    System.out.println("3. Sort Capital Cities");

                    while (inputCheck == false) {
                        try {
                            switchCategoryOne = scan.nextInt();
                        } catch (InputMismatchException e) {
                            scan.next();
                        }
                        switch (switchCategoryOne) {
                            case 1:
                                inputCheck = true;
                                break;

                            case 2:
                                inputCheck = true;
                                break;

                            case 3:
                                inputCheck = true;
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                    }

                    inputCheck = false;
                    System.out.println("Please select where to search:");
                    System.out.println("1. In the World");
                    System.out.println("2. In a Continent");
                    System.out.println("3. In a Region");
                    if (switchCategoryOne == 2) {
                        System.out.println("4. In a Country");  //Options are only shown if Sorting Cities
                        System.out.println("5. In a District");
                    }

                    while (inputCheck == false) {
                        try {
                            switchSearchOne = scan.nextInt();
                        } catch (InputMismatchException e) {
                            scan.next();
                        }
                        switch (switchSearchOne) {
                            case 1:
                                inputCheck = true;
                                break;

                            case 2:
                                System.out.println("Please select which Continent:");
                                System.out.println("1. Africa");
                                System.out.println("2. Asia");
                                System.out.println("3. Europe");
                                System.out.println("4. North America");
                                System.out.println("5. Oceania");
                                System.out.println("6. South America");
                                while (inputCheck == false) {
                                    try {
                                        switchContinentOne = scan.nextInt();
                                    } catch (InputMismatchException e) {
                                        scan.next();
                                    }
                                    switch (switchContinentOne) {
                                        case 1:
                                            inputString = "Africa";
                                            inputCheck = true;
                                            break;

                                        case 2:
                                            inputString = "Asia";
                                            inputCheck = true;
                                            break;

                                        case 3:
                                            inputString = "Europe";
                                            inputCheck = true;
                                            break;

                                        case 4:
                                            inputString = "North America";
                                            inputCheck = true;
                                            break;

                                        case 5:
                                            inputString = "Oceania";
                                            inputCheck = true;
                                            break;

                                        case 6:
                                            inputString = "South America";
                                            inputCheck = true;
                                            break;

                                        default:
                                            System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 3:
                                while (inputCheck == false) {
                                    System.out.println("Please enter which Region:");
                                    inputString = scan.next();
                                    for (i = 0; i < countries.length; i++) {
                                        if (countries[i].getRegion().equalsIgnoreCase(inputString)) {
                                            inputCheck = true;
                                        }
                                    }
                                    if (inputCheck == false) {
                                        System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 4:

                                    if (switchCategoryOne != 2) {
                                        System.out.println("Error: Invalid Input");
                                    } else {
                                        while (inputCheck == false) {
                                            System.out.println("Please enter which Country:");
                                            inputString = scan.next();
                                            for (i = 0; i < countries.length; i++) {
                                                if (countries[i].getName().equalsIgnoreCase(inputString)) {
                                                    inputCheck = true;
                                                }
                                            }
                                            if (inputCheck == false) {
                                                System.out.println("Error: Invalid Input");
                                            }
                                    }
                                }
                                break;

                            case 5:
                                if (switchCategoryOne != 2) {
                                    System.out.println("Error: Invalid Input");
                                } else {
                                    while (inputCheck == false) {
                                        System.out.println("Please enter which District:");
                                        inputString = scan.next();
                                        for (i = 0; i < cities.length; i++) {
                                            if (cities[i].getDistrict().equalsIgnoreCase(inputString)) {
                                                inputCheck = true;
                                            }
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                    }

                    inputCheck = false;
                    System.out.println("Please select Option:");
                    System.out.println("1. Show all Results");
                    System.out.println("2. Limit to the top 'N' results");

                    while (inputCheck == false) {
                        try {
                            switchLimit = scan.nextInt();
                        } catch (InputMismatchException e) {
                            scan.next();
                        }
                        switch (switchLimit) {
                            case 1:
                                inputCheck = true;
                                break;

                            case 2:
                                System.out.println("Please enter amount to Limit by:");
                                inputLimit = scan.nextInt();
                                inputCheck = true;
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                    }

                    if (switchCategoryOne == 1) {
                        if (switchSearchOne == 1) {  //Show countries in the world from largest to smallest
                            Country[] countries1 = Country.sortByPopulation(countries);
                            if (switchLimit == 1) {  //Checks if to show all results or to limit by 'N'
                                for (i = 0; i < countries1.length; i++) {
                                    for (j = 0; j < cities.length; j++) {
                                        if (countries1[i].getCapitalCode() == cities[j].getId()) {
                                            System.out.println(countries1[i].getCode() + " " + countries1[i].getName() + " " + countries1[i].getContinent() + " " + countries1[i].getRegion() + " " + countries1[i].getPopulation() + " " + cities[j].getName());
                                        }
                                    }
                                }
                            } else {
                                for (i = 0; i < inputLimit; i++) {
                                    for (j = 0; j < cities.length; j++) {
                                        if (countries1[i].getCapitalCode() == cities[j].getId()) {
                                            System.out.println(countries1[i].getCode() + " " + countries1[i].getName() + " " + countries1[i].getContinent() + " " + countries1[i].getRegion() + " " + countries1[i].getPopulation() + " " + cities[j].getName());
                                        }
                                    }
                                }
                            }
                        } else if (switchSearchOne == 2) {  //Show countries in a continent from largest to smallest
                            Country[] countries1 = Country.getAllByContinent(inputLimit, inputString, countries);

                            for (i = 0; i < countries1.length; i++) {
                                for (j = 0; j < cities.length; j++) {
                                    if (countries1[i].getCapitalCode() == cities[j].getId()) {
                                        System.out.println(countries1[i].getCode() + " " + countries1[i].getName() + " " + countries1[i].getContinent() + " " + countries1[i].getRegion() + " " + countries1[i].getPopulation() + " " + cities[j].getName());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 3) {  //Show countries in a Region from largest to smallest
                            Country[] countries1 = Country.getAllByRegion(inputLimit, inputString, countries);

                            for (i = 0; i < countries1.length; i++) {
                                for (j = 0; j < cities.length; j++) {
                                    if (countries1[i].getCapitalCode() == cities[j].getId()) {
                                        System.out.println(countries1[i].getCode() + " " + countries1[i].getName() + " " + countries1[i].getContinent() + " " + countries1[i].getRegion() + " " + countries1[i].getPopulation() + " " + cities[j].getName());
                                    }
                                }
                            }
                        }
                    } else if (switchCategoryOne == 2) {
                        if (switchSearchOne == 1) {  //Show cities in the world from largest to smallest
                            City[] cities1 = City.sortByPopulation(cities);
                            if (switchLimit == 1) {  //Checks if to show all results or to limit by 'N'
                                for (i = 0; i < cities1.length; i++) {
                                    for (j = 0; j < countries.length; j++) {
                                        if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                            System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                        }
                                    }
                                }
                            } else {
                                for (i = 0; i < inputLimit; i++) {
                                    for (j = 0; j < countries.length; j++) {
                                        if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                            System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                        }
                                    }
                                }
                            }
                        } else if (switchSearchOne == 2) {  //Show cities in a continent from largest to smallest
                            City[] cities1 = City.getAllByContinent(inputLimit, cities, inputString, countries);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 3) {  //Show cities in a Region from largest to smallest
                            City[] cities1 = City.getAllByRegion(inputLimit, cities, inputString, countries);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 4) {  //Show cities in a Country from largest to smallest
                            City[] cities1 = City.getAllByCountry(inputLimit, cities, inputString, countries);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 5) {  //Show cities in a District from largest to smallest
                            City[] cities1 = City.getAllByDistrict(inputLimit, cities, inputString);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        }
                    } else if (switchCategoryOne == 3) {
                        if (switchSearchOne == 1) {  //Show capital cities in the world from largest to smallest
                            City[] cities1 = City.getAllCapitals(inputLimit, countries, cities);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 2) {  //Show capital cities in a Continent from largest to smallest
                            City[] cities1 = City.getAllCapitalsByContinent(inputLimit, countries, cities, inputString);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        } else if (switchSearchOne == 3) {  //Show capital cities in a Region from largest to smallest
                            City[] cities1 = City.getAllCapitalsByRegion(inputLimit, countries, cities, inputString);

                            for (i = 0; i < cities1.length; i++) {
                                for (j = 0; j < countries.length; j++) {
                                    if (cities1[i].getCountryCode().contentEquals(countries[j].getCode()) == true) {
                                        System.out.println(cities1[i].getName() + " " + countries[j].getName() + " " + cities1[i].getDistrict() + " " + cities1[i].getPopulation());
                                    }
                                }
                            }
                        }
                    }
                    break;

                case 2:  //Population of a given Category
                    int switchCategoryTwo = 0;
                    int switchContinentTwo = 0;
                    long population = 0;
                    long cityPopulation = 0;
                    double percentInCities, percentNotInCities;
                    System.out.println("Please select which Category:");
                    System.out.println("1. Show Population of the World");
                    System.out.println("2. Show Population of a Continent");
                    System.out.println("3. Show Population of a Region");
                    System.out.println("4. Show Population of a Country");
                    System.out.println("5. Show Population of a District");
                    System.out.println("6. Show Population of a City");

                    while (inputCheck == false) {
                        try {
                            switchCategoryTwo = scan.nextInt();
                        } catch (InputMismatchException e) {
                            scan.next();
                        }
                        switch (switchCategoryTwo) {
                            case 1:
                                inputCheck = true;
                                break;

                            case 2:
                                System.out.println("Please select which Continent:");
                                System.out.println("1. Africa");
                                System.out.println("2. Asia");
                                System.out.println("3. Europe");
                                System.out.println("4. North America");
                                System.out.println("5. Oceania");
                                System.out.println("6. South America");

                                while (inputCheck == false) {
                                    try {
                                        switchContinentTwo = scan.nextInt();
                                    } catch (InputMismatchException e) {
                                        scan.next();
                                    }
                                    switch (switchContinentTwo) {
                                        case 1:
                                            inputString = "Africa";
                                            inputCheck = true;
                                            break;

                                        case 2:
                                            inputString = "Asia";
                                            inputCheck = true;
                                            break;

                                        case 3:
                                            inputString = "Europe";
                                            inputCheck = true;
                                            break;

                                        case 4:
                                            inputString = "North America";
                                            inputCheck = true;
                                            break;

                                        case 5:
                                            inputString = "Oceania";
                                            inputCheck = true;
                                            break;

                                        case 6:
                                            inputString = "South America";
                                            inputCheck = true;
                                            break;

                                        default:
                                            System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 3:
                                while (inputCheck == false) {
                                    System.out.println("Please enter which Region:");
                                    inputString = scan.next();
                                    for (i = 0; i < countries.length; i++) {
                                        if (countries[i].getRegion().equalsIgnoreCase(inputString)) {
                                            inputCheck = true;
                                        }
                                    }
                                    if (inputCheck == false) {
                                        System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 4:
                                while (inputCheck == false) {
                                    System.out.println("Please enter which Country:");
                                    inputString = scan.next();
                                    for (i = 0; i < countries.length; i++) {
                                        if (countries[i].getName().equalsIgnoreCase(inputString)) {
                                            inputCheck = true;
                                        }
                                    }
                                    if (inputCheck == false) {
                                        System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 5:
                                while (inputCheck == false) {
                                    System.out.println("Please enter which District:");
                                    inputString = scan.next();
                                    for (i = 0; i < cities.length; i++) {
                                        if (cities[i].getDistrict().equalsIgnoreCase(inputString)) {
                                            inputCheck = true;
                                        }
                                    }
                                    if (inputCheck == false) {
                                        System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            case 6:
                                while (inputCheck == false) {
                                    System.out.println("Please enter which City:");
                                    inputString = scan.next();
                                    for (i = 0; i < cities.length; i++) {
                                        if (cities[i].getName().equalsIgnoreCase(inputString)) {
                                            inputCheck = true;
                                        }
                                    }
                                    if (inputCheck == false) {
                                        System.out.println("Error: Invalid Input");
                                    }
                                }
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                    }

                    if (switchCategoryTwo == 1) {  //Show Population of the World
                        for (i = 0; i < countries.length; i++) {
                            population += countries[i].getPopulation();
                        }
                        for (j = 0; j < cities.length; j++) {
                            cityPopulation += cities[j].getPopulation();
                        }
                        percentInCities = (((float) cityPopulation / population) * 100);  //Calculates percentage of people living in cities and not living in cities
                        percentNotInCities = (((float) (population - cityPopulation) / population) * 100);
                        DecimalFormat df = new DecimalFormat("##.#");  //Rounds percentage to one decimal point
                        System.out.println("World " + population + " " + df.format(percentInCities) + "% " + df.format(percentNotInCities) + "%");

                    } else if (switchCategoryTwo == 2) {  //Show Population of a Continent
                        for (i = 0; i < countries.length; i++) {
                            if (countries[i].getContinent().contentEquals(inputString)) {
                                population += countries[i].getPopulation();
                            }
                        }
                        for (i = 0; i < cities.length; i++) {
                            for (j = 0; j < countries.length; j++) {
                                if (countries[j].getCode().contentEquals(cities[i].getCountryCode()) && countries[j].getContinent().contentEquals(inputString)) {
                                    cityPopulation += cities[i].getPopulation();
                                }
                            }
                        }
                        percentInCities = (((float) cityPopulation / population) * 100);
                        percentNotInCities = (((float) (population - cityPopulation) / population) * 100);
                        DecimalFormat df = new DecimalFormat("##.#");
                        System.out.println(inputString + " " + population + " " + df.format(percentInCities) + "% " + df.format(percentNotInCities) + "%");

                    } else if (switchCategoryTwo == 3) {  //Show Population of a Region
                        for (i = 0; i < countries.length; i++) {
                            if (countries[i].getRegion().equalsIgnoreCase(inputString)) {
                                population += countries[i].getPopulation();
                            }
                        }
                        for (i = 0; i < cities.length; i++) {
                            for (j = 0; j < countries.length; j++) {
                                if (countries[j].getCode().contentEquals(cities[i].getCountryCode()) && countries[j].getRegion().equalsIgnoreCase(inputString)) {
                                    cityPopulation += cities[i].getPopulation();
                                }
                            }
                        }
                        percentInCities = (((float) cityPopulation / population) * 100);
                        percentNotInCities = (((float) (population - cityPopulation) / population) * 100);
                        DecimalFormat df = new DecimalFormat("##.#");
                        System.out.println(inputString + " " + population + " " + df.format(percentInCities) + "% " + df.format(percentNotInCities) + "%");

                    } else if (switchCategoryTwo == 4) {  //Show Population of a Country
                        for (i = 0; i < countries.length; i++) {
                            if (countries[i].getName().equalsIgnoreCase(inputString)) {
                                population = countries[i].getPopulation();
                            }
                        }
                        for (i = 0; i < cities.length; i++) {
                            for (j = 0; j < countries.length; j++) {
                                if (countries[j].getCode().contentEquals(cities[i].getCountryCode()) && countries[j].getName().equalsIgnoreCase(inputString)) {
                                    cityPopulation += cities[i].getPopulation();
                                }
                            }
                        }
                        percentInCities = (((float) cityPopulation / population) * 100);
                        percentNotInCities = (((float) (population - cityPopulation) / population) * 100);
                        DecimalFormat df = new DecimalFormat("##.#");
                        System.out.println(inputString + " " + population + " " + df.format(percentInCities) + "% " + df.format(percentNotInCities) + "%");

                    } else if (switchCategoryTwo == 5) {  //Show Population of a District
                        for (i = 0; i < cities.length; i++) {
                            if (cities[i].getDistrict().equalsIgnoreCase(inputString)) {
                                population += cities[i].getPopulation();
                            }
                        }
                        System.out.println(inputString + " " + population);

                    } else if (switchCategoryTwo == 6) {  //Show Population of a City
                        for (i = 0; i < cities.length; i++) {
                            if (cities[i].getName().equalsIgnoreCase(inputString)) {
                                population = cities[i].getPopulation();
                            }
                        }
                        System.out.println(inputString + " " + population);

                    }
                    break;

                case 3:  //Language Report
                    long population2 = 0;
                    double percentSpeaking;
                    HashMap<String, Float> languages1 = Language.showAllLanguage(countries, languages, "Top");  //"Top" argument returns the five required languages
                    for (i = 0; i < countries.length; i++) {
                        population2 += countries[i].getPopulation();
                    }
                    DecimalFormat df = new DecimalFormat("##.#");  //Rounds to one decimal place
                    for (String k : languages1.keySet()) {
                        percentSpeaking = ((languages1.get(k) / population2) * 100);  //Calculates percentage of world who speaks language
                        System.out.println(k + " " + Math.round(languages1.get(k)) + " " + df.format(percentSpeaking) + "%");  //Math.round is used to display so number of speakers isn't in scientific notation
                    }
                    break;

                default:
                    System.out.println("Error: Invalid input");
            }
        }
    }



    public static void main(String[] args) throws IOException {
        // Create new Application
        App a = new App();
        String server_test = "mysql://127.0.0.1:3306/world?allowPublicKeyRetrieval=true&useSSL=false";  //Test url
        String server_normal = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";  //Normal url
        String server_name = server_test;

        City[] cities = a.GetCityData(server_name);  //Add city data
        System.out.println("Cities Added");
        Country[] countries = a.GetCountryData(server_name);  //Add country data
        System.out.println("Countries Added");
        Language[] languages = a.GetLanguageData(server_name);  //Add language data
        System.out.println("Languages Added");

        menu(cities,countries,languages);
    }
}