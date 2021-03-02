package locations;

public class City extends Location{

    private int id;
    private String district;

    public City(String name, int population, String code, int id, String district){
        super(name, population, code);
        this.id = id;
        this.district = district;
    }

    public int getId(){
        return this.id;
    }

    public String getDistrict(){
        return this.district;
    }
}