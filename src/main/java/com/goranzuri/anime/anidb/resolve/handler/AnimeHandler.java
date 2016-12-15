package com.goranzuri.anime.anidb.resolve.handler;

import com.goranzuri.anime.anidb.resolve.dao.AnimeDao;
import com.goranzuri.anime.anidb.resolve.entity.Anime;
import com.goranzuri.anime.anidb.resolve.exception.AnimeNotFoundException;
import com.goranzuri.anime.anidb.resolve.service.AnidbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by GZuri on 11/20/2016.
 */
public class AnimeHandler {
    private static final Logger logger = LogManager.getLogger(AnimeHandler.class);

    AnidbService anidbService;

    public AnimeHandler(AnidbService anidbService){
        this.anidbService = anidbService;
    }

    //TODO move to AnidbService
    public static String prepareStringForComparison(String animeTitle){
        animeTitle = animeTitle.replaceAll("[-+.^:,_ ]","");
        animeTitle = animeTitle.toLowerCase();

        return animeTitle;
    }

    //TODO move to AnidbService
    public static Map<String, Integer> prepareListOfTitlesForComparation(Map<String, Integer> animeList){
        Map<String, Integer> cleanedAnimeList = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> item : animeList.entrySet()){
            cleanedAnimeList.put(prepareStringForComparison(item.getKey()), item.getValue());
        }
        return  cleanedAnimeList;
    }
    //TODO move to AnidbService
    public Integer getAnidbCode(String animeName) throws AnimeNotFoundException {
        try {
            Map<String, Integer> animeTitlesWithId = prepareListOfTitlesForComparation(anidbService.getAnimeCandidates(animeName));

            animeName = prepareStringForComparison(animeName);

            if (animeTitlesWithId.containsKey(animeName))
                return animeTitlesWithId.get(animeName);

            //animeTitlesWithId.get(animeName);
        } catch (Exception ex){

        }
        throw  new AnimeNotFoundException();
    }

    //TODO move to AnidbService
    public Integer getAniDbCodeIfMatched(String animeName){
        try{
            return getAnidbCode(animeName);
        } catch (AnimeNotFoundException e) {

        }
        return null;
    }

    public void processAnimeWithoutAnidbCode() throws SQLException {
        AnimeDao animeDao = new AnimeDao();
        List<Anime> animeList = animeDao.getAnimeWithoutAnidb();

        for (Anime animeItem: animeList) {
            Integer anidb_code = getAniDbCodeIfMatched(animeItem.getName());
            if (anidb_code != null){
                logger.info("Matched anidbCode to anime name=" + animeItem.getName());
                animeItem.setAnidbCode(anidb_code.toString());
                animeDao.setAnidb(animeItem);
            }else {
                logger.info("Can't match anidbCode to anime name=" + animeItem.getName());
            }
        }
    }


    public static void main(String... args) throws SQLException {
        //logger.info("init");
        AnimeHandler animeHandler = new AnimeHandler(new AnidbService());
        animeHandler.processAnimeWithoutAnidbCode();
    }
}