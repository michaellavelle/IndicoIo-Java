package io.indico.api.test.text;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import io.indico.api.Indico;
import io.indico.api.exception.IndicoException;
import io.indico.api.text.IndicoTextApi;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IndicoTextSetVariablesWhenInitializedTest {

    @Test
    public void languageResponseContainsAssumptionsTest() {
        String message = "You must set an api key in either the config parameter, config file, " +
                 "or as environment variables.";
        Indico.setApiKey(null);
        Indico.setPrivateCloud("not.a.real.server");
        sendARequest(message);
        sendARequest(message);
        Indico.setApiKey(System.getenv("INDICO_API_KEY"));
        sendARequest("Connection error");
    }

    public static void sendARequest(String message) {
        try{
            Set<String> shouldBe = new HashSet<String>();
            shouldBe.add("English");
            shouldBe.add("Russian");
            shouldBe.add("Korean");
            shouldBe.add("Spanish");

            List<String> textData = Arrays.asList("Just text","More Text");
            List<Map<String, Double>> response = Indico.text().batchLanguage(textData);

            assertTrue(response.get(0).keySet().containsAll(shouldBe));
            assertTrue(response.get(1).keySet().containsAll(shouldBe));
        } catch (IndicoException exception) {
            assertTrue(exception.getMessage().contains(message));
            return;
        }
        fail("The api key was not null");
    }
}
