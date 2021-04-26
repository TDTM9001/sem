package com.napier.sem;

import locations.*;

import java.sql.*;
import java.util.Scanner;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String server_name)
    {
        try
        {
            // Load Database driver
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

    public Country[] GetCountryData(String server_name)
    {
        connect(server_name);
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM country");
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
            Country[] countries = new Country[length];
            disconnect();
            connect(server_name);
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM country");
                int counter = 0;
                while(rs.next()) {
                    Country temp = new Country(rs.getString("Name"), rs.getInt("Population"), rs.getString("Code"), rs.getString("Continent"), rs.getString("Region"), rs.getInt("Capital"));
                    countries[counter] = temp;
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

    public City[] GetCityData(String server_name)
    {
        connect(server_name);
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM city");
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
            City[] cities = new City[length];
            disconnect();
            connect(server_name);
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM city");
                int counter = 0;
                while(rs.next()) {
                    City temp = new City(rs.getString("Name"), rs.getInt("Population"), rs.getString("CountryCode"), rs.getInt("ID"), rs.getString("District"));
                    cities[counter] = temp;
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

    public Language[] GetLanguageData(String server_name)
    {
        connect(server_name);
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM countrylanguage");
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
            connect(server_name);
            Language[] languages = new Language[length];
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM countrylanguage");
                int counter = 0;
                while(rs.next()) {
                    Language temp = new Language(rs.getString("CountryCode"), rs.getString("Language"), rs.getString("IsOfficial").equals("T"), rs.getFloat("Percentage"));
                    languages[counter] = temp;
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



    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();
        String server_name = "mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";

        City[] cities = a.GetCityData(server_name);
        Country[] countries = a.GetCountryData(server_name);
        Language[] languages = a.GetLanguageData(server_name);

        System.out.println("Welcome to the Population Research App");  //Welcome message
        System.out.println("Please select a menu option:");
        System.out.println("1. Show Population from Largest to Smallest");
        System.out.println("2. Population of a given Category");
        System.out.println("3. Language Report");

        int switchMain = 0;
        String inputString;
        switch(switchMain) {
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

                switch(switchCategoryOne) {
                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;

                    default:
                        System.out.println("Error: Invalid Input");
                }

                System.out.println("Please select where to search:");
                System.out.println("1. In the World");
                System.out.println("2. In a Continent");
                System.out.println("3. In a Region");
                if(switchCategoryOne == 2) {
                    System.out.println("4. In a Country");  //Options are only shown if Sorting Cities
                    System.out.println("5. In a District");
                }
                switch(switchSearchOne) {
                    case 1:
                        break;

                    case 2:
                        System.out.println("Please select which Continent:");
                        System.out.println("1. Africa");
                        System.out.println("2. Asia");
                        System.out.println("3. Europe");
                        System.out.println("4. North America");
                        System.out.println("5. Oceania");
                        System.out.println("6. South America");
                        switch(switchContinentOne) {
                            case 1:
                                inputString = "Africa";
                                break;

                            case 2:
                                inputString = "Asia";
                                break;

                            case 3:
                                inputString = "Europe";
                                break;

                            case 4:
                                inputString = "North America";
                                break;

                            case 5:
                                inputString = "Oceania";
                                break;

                            case 6:
                                inputString = "South America";
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                        break;

                    case 3:
                        System.out.println("Please enter which Region:");
                        Scanner scan = new Scanner(System.in);
                        inputString = scan.next();
                        break;

                    case 4:
                        if(switchCategoryOne != 2) {
                            System.out.println("Error: Invalid Input");
                        } else {
                            System.out.println("Please enter which Country:");
                            Scanner scan2 = new Scanner(System.in);
                            inputString = scan2.next();
                        }
                        break;

                    case 5:
                        if(switchCategoryOne != 2) {
                            System.out.println("Error: Invalid Input");
                        } else {
                            System.out.println("Please enter which District:");
                            Scanner scan3 = new Scanner(System.in);
                            inputString = scan3.next();
                        }
                        break;

                    default:
                        System.out.println("Error: Invalid Input");
                }

                System.out.println("Please select Category to Sort by:");
                System.out.println("1. Show all Results");
                System.out.println("2. Limit to the top 'N' results");

                switch(switchLimit) {
                    case 1:
                        break;

                    case 2:
                        System.out.println("Please enter amount to Limit by:");
                        Scanner scan4 = new Scanner(System.in);
                        inputLimit = scan4.nextInt();
                        break;

                    default:
                        System.out.println("Error: Invalid Input");
                }


                break;

            case 2:  //Population of a given Category
                int switchCategoryTwo = 0;
                int switchContinentTwo = 0;
                System.out.println("Please select which Category:");
                System.out.println("1. Show Population of the World");
                System.out.println("2. Show Population of a Continent");
                System.out.println("3. Show Population of a Region");
                System.out.println("4. Show Population of a Country");
                System.out.println("5. Show Population of a District");
                System.out.println("6. Show Population of a City");
                switch(switchCategoryTwo) {
                    case 1:
                        break;

                    case 2:
                        System.out.println("Please select which Continent:");
                        System.out.println("1. Africa");
                        System.out.println("2. Asia");
                        System.out.println("3. Europe");
                        System.out.println("4. North America");
                        System.out.println("5. Oceania");
                        System.out.println("6. South America");
                        switch(switchContinentTwo) {
                            case 1:
                                inputString = "Africa";
                                break;

                            case 2:
                                inputString = "Asia";
                                break;

                            case 3:
                                inputString = "Europe";
                                break;

                            case 4:
                                inputString = "North America";
                                break;

                            case 5:
                                inputString = "Oceania";
                                break;

                            case 6:
                                inputString = "South America";
                                break;

                            default:
                                System.out.println("Error: Invalid Input");
                        }
                        break;

                    case 3:
                        System.out.println("Please enter which Region:");
                        Scanner scan5 = new Scanner(System.in);
                        inputString = scan5.next();
                        break;

                    case 4:
                        System.out.println("Please enter which Country:");
                        Scanner scan6 = new Scanner(System.in);
                        inputString = scan6.next();
                        break;

                    case 5:
                        System.out.println("Please enter which District:");
                        Scanner scan7 = new Scanner(System.in);
                        inputString = scan7.next();
                        break;

                    case 6:
                        System.out.println("Please enter which City:");
                        Scanner scan8 = new Scanner(System.in);
                        inputString = scan8.next();
                        break;

                    default:
                        System.out.println("Error: Invalid Input");
                }
                break;

            case 3:  //Language Report
                break;

            default:
                System.out.println("Error: Invalid input");
        }
    }
}