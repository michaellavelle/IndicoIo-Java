package io.indico.api.model.image.response;

import io.indico.api.model.error.Error;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
/**
 * Model used for parsing json response that represents feature vector explaining face.
 */
public class FacialFeatures extends Error{

    private Map<String, List<Double>> response;

    public Map<String, List<Double>> getValue() {
        System.out.println(response);
        return response;
    }
}
