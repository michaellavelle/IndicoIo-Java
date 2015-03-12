package io.indico.api.test.text;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import io.indico.api.Indico;
import io.indico.api.exception.IndicoException;

import static org.junit.Assert.assertTrue;

public class IndicoBatchTextApiPrivateCloudTest {
    public static final Map<String, String> auth;
    static
    {
        auth = new HashMap<String, String>();
        auth.put("username" , System.getenv("INDICO_USERNAME"));
        auth.put("password" , System.getenv("INDICO_PASSWORD"));
        auth.put("cloud" , "indico-test");
    }


    @Test
    public void languageResponseContainsAssumptionsTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("English");
        shouldBe.add("Russian");
        shouldBe.add("Korean");
        shouldBe.add("Spanish");

        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchLanguage(textData, auth);

        assertTrue(response.get(0).keySet().containsAll(shouldBe));
        assertTrue(response.get(1).keySet().containsAll(shouldBe));
    }

    @Test
    public void languageResponseHasItemsTest() throws IndicoException {
        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchLanguage(textData, auth);

        assertTrue(response.get(0).size() > 0);
        assertTrue(response.get(1).size() > 0);
    }

    @Test
    public void politicalResponseContainsAssumptionsTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("Libertarian");
        shouldBe.add("Liberal");
        shouldBe.add("Conservative");
        shouldBe.add("Green");

        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchPoliticalSentiment(textData, auth);

        assertTrue(response.get(0).keySet().containsAll(shouldBe));
        assertTrue(response.get(1).keySet().containsAll(shouldBe));
    }

    @Test
    public void politicalResponseHasItemsTest() throws IndicoException {
        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchPoliticalSentiment(textData, auth);

        assertTrue(response.get(0).size() > 0);
        assertTrue(response.get(1).size() > 0);
    }

    @Test
    public void textTagsResponseContainsAssumptionsTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("fashion");
        shouldBe.add("art");
        shouldBe.add("science");
        shouldBe.add("fitness");

        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchTextTags(textData, auth);

        assertTrue(response.get(0).keySet().containsAll(shouldBe));
        assertTrue(response.get(1).keySet().containsAll(shouldBe));
    }

    @Test
    public void textTagsResponseHasItemsTest() throws IndicoException {
        List<String> textData = Arrays.asList("Just text","More Text");
        List<Map<String, Double>> response = Indico.text().batchTextTags(textData, auth);

        assertTrue(response.get(0).size() > 0);
        assertTrue(response.get(1).size() > 0);
    }

    @Test
    public void sentimentResponseLimitsTest() throws IndicoException {
        List<String> textData = Arrays.asList("Bad news","Good news");
        List<Double> value = Indico.text().batchSentiment(textData, auth);

        assertTrue(value.get(0) >= 0);
        assertTrue(value.get(0) <= 0.5);

        assertTrue(value.get(1) >= 0.5);
        assertTrue(value.get(1) <= 1);
    }
}
