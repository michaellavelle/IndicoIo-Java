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
import static org.junit.Assert.fail;

public class IndicoAuthorizationTest {
    @Test
    public void testJustPassingACloudInConfig() throws IndicoException {
        try {
            List<String> textData = Arrays.asList("Just text","More Text");
            Map<String, String> auth = new HashMap<String, String>();
            auth.put("cloud", "this.cloud.is.fake");
            List<Map<String, Double>> response = Indico.text().batchLanguage(textData, auth);
        } catch (IndicoException error) {
            return;
        }
        fail("an indico exception was not raised");
    }

    @Test
    public void testJustPassingAnApiKeyInConfig() throws IndicoException {
        try {
            List<String> textData = Arrays.asList("Just text","More Text");
            Map<String, String> auth = new HashMap<String, String>();
            auth.put("api_key", "this.api.key.is.fake");
            List<Map<String, Double>> response = Indico.text().batchLanguage(textData, auth);
        } catch (IndicoException error) {
            return;
        }
        fail("an indico exception was not raised");
    }


}
