package components.alchemy;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concept;
import components.NED;
import components.NER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by akorovin on 21.02.2017.
 */
public class AlchemyNERD implements NER, NED {
    private final String alchemyKey = "7fdef5a245edb49cfc711e80217667be512869b9";

    private List<String> getAlchemyData(String slide) {
        AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey(alchemyKey);

        Map<String, Object> params = new HashMap<>();
        params.put(AlchemyLanguage.TEXT, slide);
        List<Concept> concepts = service.getConcepts(params).execute().getConcepts();
        return concepts.stream().map(Concept::getDbpedia).collect(Collectors.toList());
    }

    public List<String> disambiguate(List<String> entities) {
        return null;
    }

    public List<String> getEntities(String slide) {
        return this.getAlchemyData(slide);
//        String response;
//        try {
//            response = this.getDataFromAlchemyService(slide);
//            return parseXML(response);
//        } catch (URISyntaxException|IOException|DocumentException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
