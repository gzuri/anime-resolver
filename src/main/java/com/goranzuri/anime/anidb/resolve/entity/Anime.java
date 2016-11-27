package com.goranzuri.anime.anidb.resolve.entity;

/**
 * Created by gzuri on 27.11.2016..
 */
public class Anime {
    private Integer animeId;
    private String anidbCode;
    private String name;

    public Integer getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public String getAnidbCode() {
        return anidbCode;
    }

    public void setAnidbCode(String anidbCode) {
        this.anidbCode = anidbCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
