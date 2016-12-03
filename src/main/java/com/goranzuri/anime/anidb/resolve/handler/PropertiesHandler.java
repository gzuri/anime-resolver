package com.goranzuri.anime.anidb.resolve.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by gzuri on 27.11.2016..
 */
public class PropertiesHandler {

    private static Properties properties;
    private static final String PROPERTIES_FILE = "config.properties";
    private PropertiesHandler(){
    }

    public static String getProperty(String propertyName){
        if (properties == null){
            try(InputStream resourceStream = PropertiesHandler.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)){
                properties = new Properties();
                properties.load(resourceStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  properties.getProperty(propertyName);
    }

    public static Integer getPropertyInt(String propertyName){
        return Integer.parseInt(getProperty(propertyName));
    }
}