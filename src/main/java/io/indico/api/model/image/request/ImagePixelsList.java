package io.indico.api.model.image.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model is used for creating json object that contains pixels of the image.
 */
public class ImagePixelsList {
    @SerializedName("data")
    private List<List<List<Double>>> pixels;

    public ImagePixelsList(List<List<List<Double>>> pixels) {
        this.pixels = pixels;
    }
}
