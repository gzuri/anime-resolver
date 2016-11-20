package com.goranzuri.anime.anidb.resolve.handler;

import com.goranzuri.anime.anidb.resolve.exception.AnimeNotFoundException;
import com.goranzuri.anime.anidb.resolve.service.AnidbService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by GZuri on 11/20/2016.
 */
public class AnimeHandler {
    AnidbService anidbService;

    public AnimeHandler(AnidbService anidbService){
        this.anidbService = anidbService;
    }


    public static String cleanTitle(String animeTitle){
        animeTitle = animeTitle.replaceAll("[-+.^:,_ ]","");
        animeTitle = animeTitle.toLowerCase();

        return animeTitle;
    }

    public static Map<String, Integer> cleanAnimeList(Map<String, Integer> animeList){
        Map<String, Integer> cleanedAnimeList = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> item : animeList.entrySet()){
            cleanedAnimeList.put(cleanTitle(item.getKey()), item.getValue());
        }

        return  cleanedAnimeList;
    }

    public Integer getAnimeId(String animeName) throws AnimeNotFoundException {
        try {
            Map<String, Integer> animeTitlesWithId = cleanAnimeList(anidbService.getAnimeCandidates(animeName));

            animeName = cleanTitle(animeName);

            if (animeTitlesWithId.containsKey(animeName))
                return animeTitlesWithId.get(animeName);
            animeTitlesWithId.get(animeName);
        } catch (Exception ex){

        }
        throw  new AnimeNotFoundException();
    }
}