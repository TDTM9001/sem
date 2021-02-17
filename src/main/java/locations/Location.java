package locations;

public class Location {

    private String name;
    private int population;
    private String countryCode;

    public Location(String name, int population, String code) {
        this.name = name;
        this.population = population;
        this.countryCode = code;
    }

    public String getName(){
        return this.name;
    }

    public int getPopulation(){
        return this.population;
    }

    public String getCountryCode(){
        return this.countryCode;
    }

}
