package io.indico.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import org.ini4j.Ini;

import io.indico.api.ConfigLogic;
import io.indico.api.exception.IndicoException;


public class Config{
    public final static String API_URL = "https://apiv2.indico.io";

    private static Ini correctIniFile = correctIni();
    private static ConfigLogic configLogic = new ConfigLogic();

    public static String CLOUD = getCloud();
    public static String API_KEY = getApiKey();

    private static Ini correctIni() {
        try {
            String currentDirectory = System.getProperty("user.dir");
            File config = new File(currentDirectory, ".indicorc");
            if(config.exists()) {
                return new Ini(config);
            }
            String home = System.getProperty("user.home");
            config = new File(home, ".indicorc");
            if(config.exists()) {
                return new Ini(config);
            }
            return null;
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static String getCloud() {
        return configLogic.getVariable("private_cloud", "cloud", "INDICO_CLOUD", correctIniFile);
    }

    private static String getApiKey() {
        return configLogic.getVariable("auth", "api_key", "INDICO_API_KEY", correctIniFile);
    }


    public static String findCorrectApiKey(String apiKey) throws IndicoException {
        if (apiKey == null) {
            apiKey = API_KEY;
        }
        if (apiKey == null) {
            throw new IndicoException("You must set an api key in either the config parameter, " +
                                      "config file, or as environment variables.");
        };
        return apiKey;
    }
}
