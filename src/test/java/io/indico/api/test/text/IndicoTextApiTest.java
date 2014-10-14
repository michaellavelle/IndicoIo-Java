package io.indico.api.test.text;

import io.indico.api.Indico;
import io.indico.api.exception.IndicoException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class IndicoTextApiTest {

    @Test
    public void languageResponseContainsAssumptionsTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("English");
        shouldBe.add("Russian");
        shouldBe.add("Korean");
        shouldBe.add("Spanish");

        Map<String, Double> response = Indico.text().language("Just text");

        assertTrue(response.keySet().containsAll(shouldBe));
    }

    @Test
    public void languageResponseHasItemsTest() throws IndicoException {
        Map<String, Double> response = Indico.text().language("Just text");

        assertTrue(response.size() > 0);
    }

    @Test
    public void politicalResponseContainsAssumptionsTest() throws IndicoException {
        Set<String> shouldBe = new HashSet<String>();
        shouldBe.add("Libertarian");
        shouldBe.add("Liberal");
        shouldBe.add("Conservative");
        shouldBe.add("Green");

       Map<String, Double> response = Indico.text().politicalSentiment("Religion is the opium for people");

        assertTrue(response.keySet().containsAll(shouldBe));
    }

    @Test
    public void politicalResponseHasItemsTest() throws IndicoException {
        Map<String, Double> response = Indico.text().politicalSentiment("Religion is the opium for people");

        assertTrue(response.size() > 0);
    }

    @Test
    public void sentimentResponseLimitsTest() throws IndicoException {
        double value = Indico.text().sentiment("Bad news");
        System.out.println(value);

        assertTrue(value >= 0);
        assertTrue(value <= 1);
    }
}
