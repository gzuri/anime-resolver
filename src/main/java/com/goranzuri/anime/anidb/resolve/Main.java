package com.goranzuri.anime.anidb.resolve;

import com.goranzuri.anime.anidb.resolve.exception.AnimeNotFoundException;
import com.goranzuri.anime.anidb.resolve.handler.AnimeHandler;
import com.goranzuri.anime.anidb.resolve.service.AnidbService;

/**
 * Created by GZuri on 11/20/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        AnimeHandler animeHandler = new AnimeHandler(new AnidbService());
        try{
            System.out.println( animeHandler.getAnidbCode(args[0]));
        }catch (AnimeNotFoundException ex){
            System.out.println("Anime not found");
        }catch (Exception ex){
            System.out.println("Uuuuups something went wrong!");
        }

    }
}
