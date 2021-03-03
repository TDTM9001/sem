package locations;

import java.util.ArrayList;

public class Country extends Location{

    private String continent;
    private String region;
    private int capitalCode;

    public Country(String name, int population, String code, String continent, String region, int capitalCode){
        super(name, population, code);
        this.continent = continent;
        this.region = region;
        this.capitalCode = capitalCode;
    }

    public String getContinent(){
        return this.continent;
    }
    public String getRegion(){
        return this.region;
    }
    public int getCapitalCode(){
        return this.capitalCode;
    }

    /*
    Validates user input and adds only countries with continent to return
     */
    public static Country[] getAllByContinent(String continent, Country[] countries){

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
            if(c.getContinent().toLowerCase().equals((continent))){
                found_countries.add(c);
            }
        }
        // Convert back to array and return
        Country[] return_countries = new Country[found_countries.size()];
        return_countries = found_countries.toArray(return_countries);

        return return_countries;
    }
}