package io.indico.api.model.image.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model is used for creating json object that contains pixels of the image.
 */
public class ImagePixels {
    @SerializedName("image")
    private List<List<Double>> pixels;

    public ImagePixels(List<List<Double>> pixels) {
        this.pixels = pixels;
    }
}
