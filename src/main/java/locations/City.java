package locations;

import java.util.ArrayList;
import java.util.Arrays;

public class City{

    private int id;
    private String district;
    private String countryCode;
    private String name;
    private int population;

    public City(String name, int population, String code, int id, String district){
        this.name = name;
        this.population = population;
        this.countryCode = code;
        this.id = id;
        this.district = district;
    }
    /*
    getters
     */
    public String getName() { return this.name; }
    public int getPopulation(){ return this.population; }
    public String getCountryCode(){ return this.countryCode; }
    public int getId(){
        return this.id;
    }
    public String getDistrict(){
        return this.district;
    }

    /*
    find capital code of each country, linear search until city code = capital code
    return top n cities
     */
    public static City[] getAllCapitals(int howMany, Country[] countries, City[] cities) {
        City[] capitals = new City[countries.length];
        int index = 0;

        for(Country c : countries){
            for(City ci : cities){
                if(c.getCapitalCode() == ci.getId()){
                    capitals[index] = ci;
                    break;
                }
            }
            index++;
        }
        if(howMany == 0) {
            return sortByPopulation(capitals);
        }else{
            return topNCities(howMany, capitals);
        }
    }
    /*
    find capital code of each country in region, linear search until city code = capital code
    return top n cities
     */
    public static City[] getAllCapitalsByRegion(int howMany, Country[] countries, City[] cities, String region){
        Country[] inRegion = Country.getAllByRegion(0,region, countries);

        if (inRegion != null) {
            City[] capitals = new City[inRegion.length];
            int index = 0;
            for(Country c : inRegion){
                for(City ci : cities){
                    if(c.getCapitalCode() == ci.getId()){
                        capitals[index] = ci;
                    }
                }
                index++;
            }
            if(howMany == 0){
                return sortByPopulation(capitals);
            }else{
                return topNCities(howMany, capitals);
            }
        }else{
            return null;
        }
    }
    /*
    find capital code of each country in continent, linear search until city code = capital code
    return top n cities
     */
    public static City[] getAllCapitalsByContinent(int howMany, Country[] countries, City[] cities, String continent) {
        Country[] contCountries = Country.getAllByContinent(0, continent, countries);
        City[] contCities = getAllByContinent(0,cities, continent, countries);

        if (contCountries != null) {
            City[] capitals = new City[contCountries.length];
            int index = 0;
            for(Country c : contCountries){
                for(City ci : contCities){
                    if(c.getCapitalCode() == ci.getId()){
                        capitals[index] = ci;
                    }
                }
                index++;
            }
            if(howMany == 0){
                return sortByPopulation(capitals);
            }else{
                return topNCities(howMany,capitals);
            }
        }

        return null;
    }

    /*
    find each city in country, in continent and return top n results
     */
    public static City[] getAllByContinent(int howMany, City[] cities, String continent, Country[] countries) {
        ArrayList<City> inContinent = new ArrayList<City>();
        final Country[] tempCountry = new Country[1];

        // Concurrent threads used as multiple O(n) linear search used
        // on large data sets, at the same time
        Thread findCities = new Thread(){
            @Override
            public void run(){
                for(City c : cities){
                    if(c.getCountryCode().equals(tempCountry[0].getCode())){
                        inContinent.add(c);
                    }
                }
                notifyAll();
            }
        };

        Thread loopCountries = new Thread(){
            @Override
            public void run(){
                for(Country c : countries){
                    if(c.getContinent().equals(continent)){
                        tempCountry[0] = c;
                        if(Thread.activeCount() >= 4){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        findCities.run();
                    }
                }
            }
        };

        loopCountries.start();

        City[] contCities = new City[inContinent.size()];
        contCities = inContinent.toArray(contCities);

        if(howMany == 0){
            return sortByPopulation(contCities);
        }else{
            return topNCities(howMany, contCities);
        }
    }

    /*
    find each city in country, in region and return top n results
     */
    public static City[] getAllByRegion(int howMany, City[] cities, String region, Country[] countries) {
        ArrayList<City> inRegion = new ArrayList<City>();
        final Country[] tempCountry = new Country[1];

        // Concurrent threads used as multiple O(n) linear search used
        // on large data sets, at the same time
        Thread findCities = new Thread(){
            @Override
            public void run(){
                for(City c : cities){
                    if(c.getCountryCode().equals(tempCountry[0].getCode())){
                        inRegion.add(c);
                    }
                }
                notifyAll();
            }
        };

        Thread loopCountries = new Thread(){
            @Override
            public void run(){
                for(Country c : countries){
                    if(c.getRegion().equals(region)){
                        tempCountry[0] = c;
                        if(Thread.activeCount() >= 4){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        findCities.run();
                    }
                }
            }
        };

        loopCountries.start();

        City[] regCities = new City[inRegion.size()];
        regCities = inRegion.toArray(regCities);

        if(howMany == 0){
            return sortByPopulation(regCities);
        }else{
            return topNCities(howMany, regCities);
        }
    }
    /*
    Find country with name, and linear search through all cities matching country code
    return n cities
     */
    public static City[] getAllByCountry(int howMany, City[] cities, String countryName, Country[] countries) {
        Country foundCountry = null;
        ArrayList<City> allCities = new ArrayList<City>();
        for (Country c : countries) {
            if (countryName.toLowerCase().equals(c.getName().toLowerCase())) {
                foundCountry = c;
                break;
            }
        }
        if(foundCountry != null) {
            for (City c : cities) {
                if (c.getCountryCode().equals(foundCountry.getCode())) {
                    allCities.add(c);
                }
            }
        }

        City[] arrCities = new City[allCities.size()];
        arrCities = allCities.toArray(arrCities);

        if(howMany == 0){
            return sortByPopulation(arrCities);
        }else{
            return topNCities(howMany, arrCities);
        }
    }

    /*
    find all cities with district, return n results
     */
    public static City[] getAllByDistrict(int howMany, City[] cities, String district){
        ArrayList<City> foundCities = new ArrayList<City>();
        for(City c : cities){
            if(c.getDistrict().toLowerCase().equals(district.toLowerCase())){
                foundCities.add(c);
            }
        }
        City[] arrCities = new City[foundCities.size()];
        arrCities = foundCities.toArray(arrCities);
        if(howMany == 0){
            return sortByPopulation(arrCities);
        }else{
            return topNCities(howMany, arrCities);
        }
    }

    /*
    Sorts array and returns top n elements
     */
    public static City[] topNCities(int n, City[] cities){
        City[] new_cities = sortByPopulation(cities);
        int size = new_cities.length;
        return Arrays.copyOfRange(new_cities,0, n);
    }
    /*
   Bubble sort in ascending order
    */
    public static City[] sortByPopulation(City[] cities){
        int n = cities.length;
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){
                if(cities[j].getPopulation() < cities[j+1].getPopulation()){
                    City temp_city = cities[j];
                    cities[j] = cities[j+1];
                    cities[j+1] = temp_city;
                }
            }
        }
        return cities;
    }
}