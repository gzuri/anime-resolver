package com.goranzuri.anime.anidb.resolve.handler;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by GZuri on 11/20/2016.
 */
public class TestAnidbHandler {

    @Test
    public void testCleanTitleRemoveWhitespaceAndMinus(){
        String animeTitle = "Ai Yori Aoshi - Enishi";

        animeTitle = AnimeHandler.prepareStringForComparison(animeTitle);

        Assert.assertTrue(!animeTitle.contains(" "));
        Assert.assertTrue(!animeTitle.contains("-"));
    }
}
