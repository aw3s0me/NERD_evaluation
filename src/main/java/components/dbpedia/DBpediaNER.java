package components.dbpedia;

import components.NER;

import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class DBpediaNER implements NER {
    private String serviceUrl = "http://spotlight.sztaki.hu:2222/rest/spot?text=";

    public List<String> getEntities(String slide) {
        return null;
    }
}
