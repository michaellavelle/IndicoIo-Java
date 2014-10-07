package io.indico.api.model.image.response;

import io.indico.api.model.error.Error;

import java.util.List;

/**
 * Model used for parsing json response that represents feature vector explaining face.
 */
public class FacialFeatures extends Error {
    private List<Double> response;

    public List<Double> getValue() {
        return response;
    }
}
