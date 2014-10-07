package io.indico.api.test.image;

import io.indico.api.Indico;
import io.indico.api.exception.IndicoException;
import io.indico.api.exception.ParameterException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndicoImageImageApiTest {
    @Test
    public void emotionalStateArraySizeTest() throws IndicoException {
        List<List<Double>> pixels = buildMatrix(48);

        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("Angry");
        shouldBe.add("Sad");
        shouldBe.add("Fear");
        shouldBe.add("Happy");

        Map<String, Double> response = Indico.image().emotionalState(pixels);

        assertTrue(response.keySet().containsAll(shouldBe));
    }

    @Test
    public void facialFeaturesTest() throws IndicoException {
        List<List<Double>> matrix = buildMatrix(48); // strict 48

        List<Double> result = Indico.image().facialFeatures(matrix);
        assertEquals(48, result.size());
    }

    @Test(expected = ParameterException.class)
    public void facialFeatureWrongMatrixSizeTest() throws IndicoException {
        List<List<Double>> pixels = buildMatrix(30);

        Indico.image().facialFeatures(pixels);
    }

    @Test
    public void imageFeaturesResponseTest() throws IndicoException {
        List<List<Double>> matrix = buildMatrix(48);

        List<Double> result = Indico.image().imageFeatures(matrix);
        assertEquals(2048, result.size());
    }

    private List<List<Double>> getPixels(String image) {
        return null;
    }


    private List<List<Double>> buildMatrix(int size) {
        List<List<Double>> pixels = new ArrayList<List<Double>>();

        for (int i = 0; i < size; i++) {

            ArrayList<Double> row = new ArrayList<Double>();

            for (int j = 0; j < size; j++) {
                row.add((double) j);
            }

            pixels.add(row);
        }

        return pixels;
    }
}
