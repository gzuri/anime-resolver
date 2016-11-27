package com.goranzuri.anime.anidb.resolve.dao;

import com.goranzuri.anime.anidb.resolve.entity.Anime;
import com.goranzuri.anime.anidb.resolve.handler.DbHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzuri on 27.11.2016..
 */
public class AnimeDao {
    public List<Anime> getAnimeWithoutAnidb() throws SQLException {
        List<Anime> animeList = new ArrayList<>();
        Connection conn = DbHandler.getConnection();
        String querySelectAnime = "select * from anime";

        try(PreparedStatement preparedStatement = conn.prepareStatement(querySelectAnime))
        {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                animeList.add(new Anime(){{
                    setAnimeId(resultSet.getInt(1));
                    setAnidbCode(resultSet.getString(2));
                    setName(resultSet.getString(3));
                }});
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return animeList;
    }
}
