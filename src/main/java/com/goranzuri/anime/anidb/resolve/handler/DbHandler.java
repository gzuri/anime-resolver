package com.goranzuri.anime.anidb.resolve.handler;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gzuri on 27.11.2016..
 */
public class DbHandler {
    private static DataSource dataSource;

    static {
        try {
            MysqlDataSource d = new MysqlDataSource();
            d.setUser(PropertiesHandler.getProperty("com.goranzuri.anime.anidb.resolve.db.user"));
            d.setPassword(PropertiesHandler.getProperty("com.goranzuri.anime.anidb.resolve.db.password"));
            d.setServerName(PropertiesHandler.getProperty("com.goranzuri.anime.anidb.resolve.db.host"));
            d.setDatabaseName(PropertiesHandler.getProperty("com.goranzuri.anime.anidb.resolve.db.name"));
            d.setPort(Integer.getInteger(PropertiesHandler.getProperty("com.goranzuri.anime.anidb.resolve.db.port")));

            dataSource = d;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
