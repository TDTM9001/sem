package com.napier.sem;

import locations.*;

import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
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

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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

    public Country[] GetCountryData()
    {
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM country");
                rs.next();
                length = rs.getInt(1);
            }
            catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            Country[] countries = new Country[length];
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
            return null;
        }
        else {
            return null;
        }
    }

    public City[] GetCityData()
    {
        if (con != null)
        {
            Statement stmt;
            ResultSet rs;
            int length = 0;
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT COUNT(*) FROM city");
                rs.first();
                length = rs.getInt(1);
            }
            catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            System.out.println(length);
            City[] cities = new City[length];
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
            return null;
        }
        else {
            return null;
        }
    }

    public Language[] GetLanguageData()
    {
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
            Language[] languages = new Language[length];
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM city");
                int counter = 0;
                while(rs.next()) {
                    Language temp = new Language(rs.getString("Code"), rs.getString("Language"), rs.getBoolean("IsOfficial"), rs.getFloat("Percentage"));
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
            return null;
        }
        else {
            return null;
        }
    }



    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        City[] cities = a.GetCityData();
        Country[] countries = a.GetCountryData();
        Language[] languages = a.GetLanguageData();
        // Disconnect from database
        a.disconnect();
    }
}