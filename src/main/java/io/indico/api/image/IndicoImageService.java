package io.indico.api.image;

import io.indico.api.model.image.request.FacePixels;
import io.indico.api.model.image.response.FacialFeatures;
import io.indico.api.model.image.response.ImageFeatures;
import io.indico.api.model.image.request.ImagePixels;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

import java.util.Map;

interface IndicoImageService {
    String PATH_EMOTIONAL_STATE = "/fer";
    String PATH_IMAGE_FEATURES = "/imagefeatures";
    String PATH_FACIAL_FEATURES = "/facialfeatures";

    @POST(PATH_EMOTIONAL_STATE)
    public void emotionalState(@Body FacePixels facePixels, Callback<Map<String, Double>> callback);

    @POST(PATH_EMOTIONAL_STATE)
    public Map<String, Double> emotionalState(@Body FacePixels facePixels);

    @POST(PATH_IMAGE_FEATURES)
    public void imageFeatures(@Body ImagePixels imagePixels, Callback<ImageFeatures> callback);

    @POST(PATH_IMAGE_FEATURES)
    public ImageFeatures imageFeatures(@Body ImagePixels imagePixels);

    @POST(PATH_FACIAL_FEATURES)
    public void facialFeatures(@Body FacePixels facePixels, Callback<FacialFeatures> callback);

    @POST(PATH_FACIAL_FEATURES)
    public FacialFeatures facialFeatures(@Body FacePixels facePixels);
}