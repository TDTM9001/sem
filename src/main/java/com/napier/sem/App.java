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
    }
}