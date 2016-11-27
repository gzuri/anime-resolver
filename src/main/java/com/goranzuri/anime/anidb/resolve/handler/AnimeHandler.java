package com.goranzuri.anime.anidb.resolve.handler;

import com.goranzuri.anime.anidb.resolve.dao.AnimeDao;
import com.goranzuri.anime.anidb.resolve.entity.Anime;
import com.goranzuri.anime.anidb.resolve.exception.AnimeNotFoundException;
import com.goranzuri.anime.anidb.resolve.service.AnidbService;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by GZuri on 11/20/2016.
 */
public class AnimeHandler {
    AnidbService anidbService;

    public AnimeHandler(AnidbService anidbService){
        this.anidbService = anidbService;
    }


    public static String prepareTitleForComparation(String animeTitle){
        animeTitle = animeTitle.replaceAll("[-+.^:,_ ]","");
        animeTitle = animeTitle.toLowerCase();

        return animeTitle;
    }

    public static Map<String, Integer> prepareListOfTitlesForComparation(Map<String, Integer> animeList){
        Map<String, Integer> cleanedAnimeList = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> item : animeList.entrySet()){
            cleanedAnimeList.put(prepareTitleForComparation(item.getKey()), item.getValue());
        }

        return  cleanedAnimeList;
    }

    public Integer getAnidbCode(String animeName) throws AnimeNotFoundException {
        try {
            Map<String, Integer> animeTitlesWithId = prepareListOfTitlesForComparation(anidbService.getAnimeCandidates(animeName));

            animeName = prepareTitleForComparation(animeName);

            if (animeTitlesWithId.containsKey(animeName))
                return animeTitlesWithId.get(animeName);

            //animeTitlesWithId.get(animeName);
        } catch (Exception ex){

        }
        throw  new AnimeNotFoundException();
    }

    public void processAnimeWithoutAbideCode() throws SQLException {
        AnimeDao animeDao = new AnimeDao();
        List<Anime> animeList = animeDao.getAnimeWithoutAnidb();

    }


    public static void main(String... args) throws SQLException {
        AnimeHandler animeHandler = new AnimeHandler(new AnidbService());
        animeHandler.processAnimeWithoutAbideCode();
    }
}