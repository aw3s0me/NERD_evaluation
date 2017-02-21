package components.dbpedia;

import components.NER;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akorovin on 21.02.2017.
 */
public class DBpediaNER implements NER {
    private final String serviceUrl = "http://spotlight.sztaki.hu:2222/rest/spot?text=";

    public List<String> getEntities(String slide) {
        String urlToSend;
        try {
            urlToSend = serviceUrl + URLEncoder.encode(slide, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return sendUrl(urlToSend);
    }

    private List<String> sendUrl(String url) {
        List<String> retLst = new ArrayList<String>();
        try {

            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) urlConnection;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(connection.getInputStream());

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("surfaceForm");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String text = eElement.getAttribute("name");
                    String offset = eElement.getAttribute("offset");

                    String startEnd = Integer.parseInt(offset) + "," + (text.length() + Integer.parseInt(offset));
                    System.out.println(startEnd);
                    retLst.add(startEnd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retLst;
    }
}
