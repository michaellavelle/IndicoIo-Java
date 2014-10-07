package io.indico.api;

import io.indico.api.image.IndicoImageApi;
import io.indico.api.text.IndicoTextApi;

public class Indico {

    public static IndicoTextApi text() {
        return IndicoTextApi.getInstance();
    }

    public static IndicoImageApi image() {
        return IndicoImageApi.getInstance();
    }
}
