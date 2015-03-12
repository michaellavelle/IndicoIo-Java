package io.indico.api.test.image;

import java.util.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.Before;
import javax.imageio.ImageIO;

import io.indico.api.Indico;
import io.indico.api.exception.IndicoException;
import io.indico.api.exception.ParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndicoBatchImageApiPrivateCloudTest {
    public static final Map<String, String> auth;
    static
    {
        auth = new HashMap<String, String>();
        auth.put("username" , System.getenv("INDICO_USERNAME"));
        auth.put("password" , System.getenv("INDICO_PASSWORD"));
        auth.put("cloud" , "indico-test");
    }

    private List<BufferedImage> imageData;

    @Before
    public void loadImage() {
        BufferedImage img = null;

        ClassLoader classLoader = getClass().getClassLoader();
        URL defaultImage = classLoader.getResource("data/test.png");
        File imageFile = null;

        try {
            img = ImageIO.read(defaultImage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        imageData = Arrays.asList(img, img);

    }

    @Test
    public void emotionalStateArraySizeTest() throws IndicoException {

        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("Angry");
        shouldBe.add("Sad");
        shouldBe.add("Fear");
        shouldBe.add("Happy");

        List<Map<String, Double>> response = Indico.image().batchEmotionalState(imageData, auth);

        assertTrue(response.get(0).keySet().containsAll(shouldBe));
        assertTrue(response.get(1).keySet().containsAll(shouldBe));
    }

    @Test
    public void facialFeaturesTest() throws IndicoException {
        List<List<Double>> result = Indico.image().batchFacialFeatures(imageData, auth);
        assertEquals(48, result.get(0).size());
        assertEquals(48, result.get(1).size());
    }

    @Test
    public void imageFeaturesResponseTest() throws IndicoException {
        List<List<Double>> result = Indico.image().batchImageFeatures(imageData, auth);
        assertEquals(2048, result.get(0).size());
        assertEquals(2048, result.get(1).size());
    }
}
