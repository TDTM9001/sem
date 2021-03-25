package locations;

import java.util.*;

public class Language{

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

    /**
     *
     * @param countries
     * @param languages
     * @param lang
     * @return
     */
    public static HashMap<String, Float> showAllLanguage(Country[] countries, Language[] languages, String lang) {
        HashMap<String, Float> map = new HashMap<String, Float>();
        if(lang.equals("All")) {
            for (Country c : countries) {
                for (Language l : languages) {
                    if(c.getCode().equals(l.getCode())){
                        String tempKey = l.getLanguage();
                        float pop = ((c.getPopulation() / 100f) * l.getPercentSpoken());
                        if(map.containsKey(tempKey)){
                            float current = map.get(tempKey);
                            map.put(tempKey, pop+current);
                        }else {
                            map.put(tempKey, pop);
                        }
                        break;
                    }
                }
            }
            return sortMap(map);
        }else if(lang.equals("Top")){
            String[] langs = {"English", "Chinese", "Hindi", "Spanish", "Arabic"};
            for(Country c : countries){
                for(Language l : languages){
                    if(c.getCode().equals(l.getCode())){
                        for(String la : langs){
                            if(la.equals(l.getLanguage())){
                                float pop = ((c.getPopulation() / 100f) * l.getPercentSpoken());
                                if(map.containsKey(la)){
                                    float current = map.get(la);
                                    map.put(la, pop+current);
                                }else{
                                    map.put(la, pop);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param passedMap
     * @return sortedMap
     */
    public static HashMap<String, Float> sortMap(
            HashMap<String, Float> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Float> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Float> sortedMap =
                new LinkedHashMap<>();

        Iterator<Float> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Float val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Float comp1 = passedMap.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

}