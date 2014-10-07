package io.indico.api.text;

import io.indico.api.model.text.request.Text;
import io.indico.api.model.text.response.Sentiment;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import java.util.Map;

interface IndicoTextService {

    String PATH_LANGUAGE = "/language";
    String PATH_POLITICAL = "/political";
    String PATH_SENTIMENT = "/sentiment";

    @POST(PATH_LANGUAGE)
    void language(@Body Text text, Callback<Map<String, Double>> callback);

    @POST(PATH_LANGUAGE)
    Map<String, Double> language(@Body Text text);

    @POST(PATH_POLITICAL)
    void politicalSentiment(@Body Text text, Callback<Map<String, Double>> callback);

    @POST(PATH_POLITICAL)
    Map<String, Double> politicalSentiment(@Body Text text);

    @POST(PATH_SENTIMENT)
    void sentiment(@Body Text text, Callback<Sentiment> callback);

    @POST(PATH_SENTIMENT)
    Sentiment sentiment(@Body Text text);
}
