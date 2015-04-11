package io.indico.api.image;

import io.indico.api.model.image.request.IndicoImageList;
import io.indico.api.model.image.request.IndicoImage;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Header;

import java.util.Map;
import java.util.List;

interface IndicoImageService {
    String PATH_EMOTIONAL_STATE = "/fer";
    String PATH_BATCH_EMOTIONAL_STATE = "/fer/batch";
    String PATH_IMAGE_FEATURES = "/imagefeatures";
    String PATH_BATCH_IMAGE_FEATURES = "/imagefeatures/batch";
    String PATH_FACIAL_FEATURES = "/facialfeatures";
    String PATH_BATCH_FACIAL_FEATURES = "/facialfeatures/batch";


    @POST(PATH_EMOTIONAL_STATE)
    public void emotionalState(@Body IndicoImage image, @Header("X-ApiKey") String apiKey, Callback<Map<String, Map<String, Double>>> callback);

    @POST(PATH_EMOTIONAL_STATE)
    public Map<String, Map<String, Double>> emotionalState(@Body IndicoImage image, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_EMOTIONAL_STATE)
    public void batchEmotionalState(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Map<String, Double>>>> callback);

    @POST(PATH_BATCH_EMOTIONAL_STATE)
    public Map<String, List<Map<String, Double>>> batchEmotionalState(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey);


    @POST(PATH_IMAGE_FEATURES)
    public void imageFeatures(@Body IndicoImage image, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Double>>> callback);

    @POST(PATH_IMAGE_FEATURES)
    public Map<String, List<Double>> imageFeatures(@Body IndicoImage image, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_IMAGE_FEATURES)
    public void batchImageFeatures(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<List<Double>>>> callback);

    @POST(PATH_BATCH_IMAGE_FEATURES)
    public Map<String, List<List<Double>>> batchImageFeatures(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey);


    @POST(PATH_FACIAL_FEATURES)
    public void facialFeatures(@Body IndicoImage image, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<Double>>> callback);

    @POST(PATH_FACIAL_FEATURES)
    public Map<String, List<Double>> facialFeatures(@Body IndicoImage image, @Header("X-ApiKey") String apiKey);

    @POST(PATH_BATCH_FACIAL_FEATURES)
    public void batchFacialFeatures(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey, Callback<Map<String, List<List<Double>>>> callback);

    @POST(PATH_BATCH_FACIAL_FEATURES)
    public Map<String, List<List<Double>>> batchFacialFeatures(@Body IndicoImageList images, @Header("X-ApiKey") String apiKey);
}
