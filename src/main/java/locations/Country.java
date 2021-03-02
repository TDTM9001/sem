package locations;

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
}