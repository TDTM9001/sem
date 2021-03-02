package locations;

public class Language {

    private String code;
    private String language;
    private boolean officialLang;
    private float percent;

    public Language(String code, String language, boolean officialLang, float percent) {
        this.code = code;
        this.language = language;
        this.officialLang = officialLang;
        this.percent = percent;
    }

    public String getCode(){
        return this.code;
    }

    public String getLanguage(){
        return this.language;
    }

    public boolean isOfficialLang() {
        return this.officialLang;
    }

    public float getPercentSpoken(){
        return this.percent;
    }
}