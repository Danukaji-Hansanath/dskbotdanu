package com.dskbot.utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class Config {
    //this class is used to read the config.properties file
    private static Properties properties = new Properties();
    static {
        try {
            //load the properties file
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //get the value of the key from the properties file
    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    //get the value of the key from the properties file and split it by comma
    public List <String> getArrayProperty(String key) {
        List <String> list = new ArrayList<>();
         String [] values = properties.getProperty(key).split(",");
         for (String value : values) {
             list.add(value);
         }
        return list;
    }
}
