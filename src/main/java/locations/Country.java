package locations;

import java.util.ArrayList;
import java.util.Arrays;

public class Country{
    private String name;
    private int population;
    private String code;
    private String continent;
    private String region;
    private int capitalCode;

    public Country(String name, int population, String code, String continent, String region, int capitalCode){
        this.name = name;
        this.population = population;
        this.code = code;
        this.continent = continent;
        this.region = region;
        this.capitalCode = capitalCode;
    }
    /*
    getters
     */
    public String getContinent(){ return this.continent; }
    public String getRegion(){ return this.region; }
    public int getCapitalCode(){ return this.capitalCode; }
    public String getName(){ return this.name; }
    public int getPopulation(){ return this.population; }
    public String getCode(){ return this.code; }

    /**
     * Get sum pop of all cities, and total pop in country
     * @param c
     * @param cities
     * @param countries
     * @return [city pop, country pop]
     */
    public static long[] popByCountry(Country c, City[] cities, Country[] countries){
        City[] allInCountry = City.getAllByCountry(0, cities, c.getName(), countries);
        long sumCities = 0;
        for(City ci : allInCountry){
            sumCities += ci.getPopulation();
        }
        return new long[]{sumCities, c.getPopulation()-sumCities};
    }

    /**
     * Get sum pop of all cities, and total pop in continent
     * @param continent
     * @param countries
     * @param cities
     * @return [city pop, continent pop]
     */
    public static long[] popByContinent(String continent, Country[] countries, City[] cities){
        City[] citiesInContinent = City.getAllByContinent(0, cities, continent, countries);
        Country[] countriesInContinent = getAllByContinent(0, continent, countries);

        long sumCities = 0;
        long sumCountries = 0;

        for(City c : citiesInContinent){
            sumCities += c.getPopulation();
        }
        if (countriesInContinent != null) {
            for(Country c : countriesInContinent){
                sumCountries += c.getPopulation();
            }
        }
        return new long[]{sumCities, (sumCountries-sumCities)};
    }

    /**
     * Get sum of all cities, countries in region
     * @param region
     * @param countries
     * @param cities
     * @return [city pop, region pop]
     */
    public static long[] popByRegion(String region, Country[] countries, City[] cities){
        City[] citiesInRegion = City.getAllByRegion(0, cities, region, countries);
        Country[] countriesInRegion = getAllByRegion(0, region, countries);

        long sumCities = 0;
        long sumCountries = 0;

        for(City c : citiesInRegion){
            sumCities += c.getPopulation();
        }
        if (countriesInRegion != null) {
            for(Country c : countriesInRegion){
                sumCountries += c.getPopulation();
            }
        }
        return new long[]{sumCities, (sumCountries-sumCities)};
    }

    /**
     * get all countries in a continent
     * @param howMany
     * @param continent
     * @param countries
     * @return countries[]
     */
    public static Country[] getAllByContinent(int howMany, String continent, Country[] countries){

        String[] allowed_continents = {"asia","europe","north america","africa","oceania","antarctica","south america"};
        boolean found = false;
        ArrayList<Country> found_countries = new ArrayList<Country>();

        // Validate correct input
        for(String cont : allowed_continents){
            if(continent.toLowerCase().equals(cont)){
                found = true;
                break;
            }
        }
        if(!found){
            return null;
        }
        // If c.continent = continent then add to arrlist
        for(Country c : countries){
            if(c.getContinent().equals((continent))){
                found_countries.add(c);
            }
        }
        // Convert back to array and return
        Country[] return_countries = new Country[found_countries.size()];
        return_countries = found_countries.toArray(return_countries);

        if(howMany == 0){
            return sortByPopulation(return_countries);
        }else{
            return topNCountries(howMany, return_countries);
        }
    }

    /**
     * get all countries in a region
     * @param howMany
     * @param region
     * @param countries
     * @return countries[]
     */
    public static Country[] getAllByRegion(int howMany, String region, Country[] countries) {
        ArrayList<Country> found_countries = new ArrayList<Country>();
        region = region.toLowerCase();
        boolean found = false;

        for(Country c : countries) {
            if(c.getRegion().toLowerCase().equals(region)){
                found_countries.add(c);
                found = true;
            }
        }
        if(found) {
            Country[] return_countries = new Country[found_countries.size()];
            return_countries = found_countries.toArray(return_countries);
            if(howMany == 0){
                return sortByPopulation(return_countries);
            }else{
                return topNCountries(howMany, return_countries);
            }
        }else{
            return null;
        }
    }
    /*
    Sorts array and returns top n elements
     */
    public static Country[] topNCountries(int n, Country[] countries){
        Country[] new_countries = sortByPopulation(countries);
        int size = new_countries.length;
        return Arrays.copyOfRange(new_countries, 0, n);
    }
    /*
    Bubble sort in ascending order
     */
    public static Country[] sortByPopulation(Country[] countries){
        int n = countries.length;
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){
                if(countries[j].getPopulation() < countries[j+1].getPopulation()){
                    Country temp_country = countries[j];
                    countries[j] = countries[j+1];
                    countries[j+1] = temp_country;
                }
            }
        }
        return countries;
    }

}