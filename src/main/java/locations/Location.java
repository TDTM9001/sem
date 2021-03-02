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
    /*
    Bubble sort to sort by population ascending
     */
    public static void sort_population(Location[] locations){
        int n = locations.length;
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){
                if(locations[j].getPopulation() > locations[j+1].getPopulation()){
                    Location temp_location = locations[j];
                    locations[j] = locations[j+1];
                    locations[j+1] = temp_location;
                }
            }
        }
    }

}