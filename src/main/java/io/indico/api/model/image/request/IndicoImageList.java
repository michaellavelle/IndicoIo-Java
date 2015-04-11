package io.indico.api.model.image.request;

import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import com.google.gson.annotations.SerializedName;

import io.indico.api.model.image.request.IndicoImage;

/**
 * Model is used for creating json object that contains pixels of the image.
 */
public class IndicoImageList {
    @SerializedName("data")
    public List<String> imageData;

    public IndicoImageList(List<BufferedImage> images) {
        this.imageData = new ArrayList<String>();
        for (BufferedImage image : images) {
            IndicoImage indicoImage = new IndicoImage(image);
            this.imageData.add(indicoImage.imageData);
        }
    }
}
