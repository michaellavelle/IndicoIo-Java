package io.indico.api.model.image.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Model used for parsing json response that represents feature vector explaining image.
 */
public class ImageFeatures {

    @SerializedName("results")
    private List<Double> results;

    public List<Double> getFeatures() {
        return results;
    }
}
