package io.indico.api.model.image.request;

import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import java.io.*;

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
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            bos.flush();
            byte[] bytes = bos.toByteArray();
            bos.close();

            imageString = new String(Base64Coder.encode(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
