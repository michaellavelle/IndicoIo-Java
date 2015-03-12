package io.indico.api.model.image.request;

import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;

import com.google.gson.annotations.SerializedName;
import biz.source_code.base64Coder.Base64Coder;


/**
 * Model is used for creating json object that contains binary image data
 */
public class IndicoImage {

    @SerializedName("data")
    public String imageData;

    public IndicoImage(BufferedImage image) {
        this.imageData = this.serialize(image);
    }

    private String serialize(BufferedImage image) {
        WritableRaster raster = image.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        byte[] bytes = data.getData();
        return new String(Base64Coder.encode(bytes));
    }
}
