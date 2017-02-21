package components.alchemy;

import components.NED;
import components.NER;
import org.apache.http.client.utils.URIBuilder;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class AlchemyNERD implements NER, NED {
    private final String alchemyKey = "7fdef5a245edb49cfc711e80217667be512869b9";
    private final String alchemyService = "http://access.alchemyapi.com/calls/text/TextGetRankedNamedEntities";

    /**
     * call Alchemy web service and retrieve data in XML format
     */
    private String getDataFromAlchemyService(String slide) throws URISyntaxException, IOException, DocumentException {
        // call the Alchemy service, see also
        // http://www.alchemyapi.com/api/entity/proc.html
        URL url = new URIBuilder(this.alchemyService)
                .addParameter("apikey", alchemyKey)
                .addParameter("text", slide)
                .build().toURL();
        StringBuilder strBuilder = new StringBuilder();
        String aux;
        List<String> result = new ArrayList<>();

        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) urlConnection;

        BufferedReader reader;
        if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        while ((aux = reader.readLine()) != null) {
            strBuilder.append(aux);
        }

        return strBuilder.toString();
    }

    private List<String> parseXML(String respBody) throws DocumentException {
        List<String> result = new ArrayList<String>();
        List<Node> xmlNodes = DocumentHelper.parseText(respBody).selectNodes("/results/entities/entity");
        for (Node node : xmlNodes) {
            String annotatedTex = node.selectSingleNode("text").getText();
            // DBpedia URI disambiguating the marked text
            String resourceUri = node.selectSingleNode("disambiguated").selectSingleNode("dbpedia").getText();
            System.out.println("Annotated text: " + annotatedTex);
            System.out.println("Disambiguated resource: " + resourceUri);
            result.add(resourceUri);
        }
        return result;
    }

    public List<String> disambiguate(List<String> entities) {
        return null;
    }

    public List<String> getEntities(String slide) {
        String response;
        try {
            response = this.getDataFromAlchemyService(slide);
            return parseXML(response);
        } catch (URISyntaxException|IOException|DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
