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

public class IndicoImageImageApiPrivateCloudTest {
    public static final Map<String, String> auth;
    static
    {
        auth = new HashMap<String, String>();
        auth.put("username" , System.getenv("INDICO_USERNAME"));
        auth.put("password" , System.getenv("INDICO_PASSWORD"));
        auth.put("cloud" , "indico-test");
    }

    private BufferedImage img;

    @Before
    public void loadImage() {
        img = null;

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
    }

    @Test
    public void emotionalStateArraySizeTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("Angry");
        shouldBe.add("Sad");
        shouldBe.add("Fear");
        shouldBe.add("Happy");

        Map<String, Double> response = Indico.image().emotionalState(img, auth);

        assertTrue(response.keySet().containsAll(shouldBe));
    }

    @Test
    public void facialFeaturesTest() throws IndicoException {
        List<Double> result = Indico.image().facialFeatures(img, auth);
        assertEquals(48, result.size());
    }

    @Test
    public void imageFeaturesResponseTest() throws IndicoException {
        List<Double> result = Indico.image().imageFeatures(img, auth);
        assertEquals(2048, result.size());
    }
}
