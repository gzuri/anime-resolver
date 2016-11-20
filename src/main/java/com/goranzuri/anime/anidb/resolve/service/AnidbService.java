package com.goranzuri.anime.anidb.resolve.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GZuri on 11/20/2016.
 */
public class AnidbService {
    private InputStream getResultsFromApi(String animeName) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://anisearch.outrance.pl/index.php?task=search&langs=en&query=" + URLEncoder.encode(animeName, "UTF-8"));

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }

    private Map<String, Integer> parseXmlResults(InputStream xmlResult) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Integer> animeList = new HashMap<String, Integer>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document = builder.parse(xmlResult);

        //Iterating through the nodes and extracting the data.
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {

                NodeList childNodes = node.getChildNodes();
                for(int j = 0; j < childNodes.getLength(); j++){
                    Node childNode = childNodes.item(j);
                    if (childNode.getAttributes().getNamedItem("lang").getNodeValue().equals("en")
                            && (childNode.getAttributes().getNamedItem("type").getNodeValue().equals("official")
                            || childNode.getAttributes().getNamedItem("type").getNodeValue().equals("main"))){

                        Integer aid = Integer.parseInt(node.getAttributes().getNamedItem("aid").getNodeValue());

                        if (!animeList.containsKey(childNode.getTextContent()))
                            animeList.put(childNode.getTextContent(), aid);
                    }
                }
            }

        }

        return animeList;
    }

    public Map<String, Integer> getAnimeCandidates(String animeName) throws IOException, ParserConfigurationException, SAXException {
        InputStream serviceResult = getResultsFromApi(animeName);
        return parseXmlResults(serviceResult);
    }

}
