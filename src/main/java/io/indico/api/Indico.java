package io.indico.api;

import io.indico.api.image.IndicoImageApi;
import io.indico.api.text.IndicoTextApi;
import io.indico.api.Config;

public class Indico {

    public static IndicoTextApi text() {
        return IndicoTextApi.getInstance();
    }

    public static IndicoImageApi image() {
        return IndicoImageApi.getInstance();
    }

    public static void setPrivateCloud(String cloud) {
        Config.CLOUD = cloud;
    }

    public static void setApiKey(String api_key) {
        Config.API_KEY = api_key;
    }
}
