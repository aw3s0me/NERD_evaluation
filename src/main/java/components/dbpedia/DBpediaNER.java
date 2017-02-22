package components.dbpedia;

import components.NER;
import org.apache.http.client.utils.URIBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class DBpediaNER implements NER {
    private final static String SERVICE_URL = "http://spotlight.sztaki.hu:2222/rest/annotate";
    private final static Double CONFIDENCE = 0.2;
    private final static Integer SUPPORT = 20;

    public List<String> getEntities(String slide) {
        URL url;
        try {
            url = new URIBuilder(SERVICE_URL)
                    .addParameter("text", URLEncoder.encode(slide, "UTF-8"))
                    .addParameter("confidence", String.valueOf(CONFIDENCE))
                    .addParameter("support", String.valueOf(SUPPORT))
                    .build().toURL();
        } catch (UnsupportedEncodingException | MalformedURLException | URISyntaxException e) {
            System.out.println("Could not create a link");
            return null;
        }

        return sendUrl(url.toString());
    }

    private List<String> sendUrl(String url) {
        List<String> retLst = new ArrayList<String>();

        try {

            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            System.out.println(connection.getResponseCode());
            Document doc = dBuilder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Resource");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String text = eElement.getAttribute("URI");
                    System.out.println(text);
                    retLst.add(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retLst;
    }
}
