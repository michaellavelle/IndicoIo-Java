package io.indico.api.model.text.response;

import com.google.gson.annotations.SerializedName;

/**
 * Model is used for parsing json object which represents sentiment for requested text.
 */
public class Sentiment {

    @SerializedName("results")
    private double sentiment;

    public double getValue() {
        return sentiment;
    }
}
