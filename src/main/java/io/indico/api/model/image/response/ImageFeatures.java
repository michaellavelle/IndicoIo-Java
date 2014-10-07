package io.indico.api.model.image.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model used for parsing json response that represents feature vector explaining image.
 */
public class ImageFeatures {

    @SerializedName("Features")
    private List<Double> features;

    public List<Double> getFeatures() {
        return features;
    }
}
