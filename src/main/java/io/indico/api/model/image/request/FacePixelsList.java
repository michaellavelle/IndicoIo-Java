package io.indico.api.model.image.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model is used for creating json object that contains pixels of the face image.
 */
public class FacePixelsList {
    @SerializedName("data")
    private List<List<List<Double>>> pixels;

    public FacePixelsList(List<List<List<Double>>> pixels) {
        this.pixels = pixels;
    }
}
