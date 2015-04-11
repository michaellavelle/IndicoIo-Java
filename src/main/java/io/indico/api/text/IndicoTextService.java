package io.indico.api.text;

import io.indico.api.model.text.request.Text;
import io.indico.api.model.text.request.TextList;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Header;

import java.util.Map;
import java.util.List;

interface IndicoTextService {

    String PATH_LANGUAGE = "/language";
    String PATH_BATCH_LANGUAGE = "/language/batch";
    String PATH_POLITICAL = "/political";
    String PATH_BATCH_POLITICAL = "/political/batch";
    String PATH_SENTIMENT = "/sentiment";
    String PATH_BATCH_SENTIMENT = "/sentiment/batch";
    String PATH_TEXT_TAGS = "/texttags";
    String PATH_BATCH_TEXT_TAGS = "/texttags/batch";


    @POST(PATH_LANGUAGE)
    void language(@Body Text text, @Header("X-ApiKey") String apiKey, Callback<Map<String, Map<String, Double>>> callback);

    @POST(PATH_LANGUAGE)
    Map<String, Map<String, Double>> language(@Body Text text, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_LANGUAGE)
    void batchLanguage(@Body TextList text, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Map<String, Double>>>> callback);

    @POST(PATH_BATCH_LANGUAGE)
    Map<String, List<Map<String, Double>>> batchLanguage(@Body TextList text, @Header("X-ApiKey") String apiKey);


    @POST(PATH_POLITICAL)
    void politicalSentiment(@Body Text text, @Header("X-ApiKey") String apiKey, Callback<Map<String, Map<String, Double>>> callback);

    @POST(PATH_POLITICAL)
    Map<String, Map<String, Double>> politicalSentiment(@Body Text text, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_POLITICAL)
    void batchPoliticalSentiment(@Body TextList text, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Map<String, Double>>>> callback);

    @POST(PATH_BATCH_POLITICAL)
    Map<String, List<Map<String, Double>>> batchPoliticalSentiment(@Body TextList text, @Header("X-ApiKey") String apiKey);


    @POST(PATH_SENTIMENT)
    void sentiment(@Body Text text, @Header("X-ApiKey") String apiKey, Callback<Map<String, Double>> callback);

    @POST(PATH_SENTIMENT)
    Map<String, Double> sentiment(@Body Text text, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_SENTIMENT)
    void batchSentiment(@Body TextList text, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Double>>> callback);

    @POST(PATH_BATCH_SENTIMENT)
    Map<String, List<Double>> batchSentiment(@Body TextList text, @Header("X-ApiKey") String apiKey);


    @POST(PATH_TEXT_TAGS)
    void textTags(@Body Text text, @Header("X-ApiKey") String apiKey, Callback<Map<String, Map<String, Double>>> callback);

    @POST(PATH_TEXT_TAGS)
    Map<String, Map<String, Double>> textTags(@Body Text text, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_TEXT_TAGS)
    void batchTextTags(@Body TextList text, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Map<String, Double>>>> callback);

    @POST(PATH_BATCH_TEXT_TAGS)
    Map<String, List<Map<String, Double>>> batchTextTags(@Body TextList text, @Header("X-ApiKey") String apiKey);
}
