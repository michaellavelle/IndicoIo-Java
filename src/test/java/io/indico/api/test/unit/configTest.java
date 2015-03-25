package io.indico.api.test.unit;

import java.util.Map;
import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.ini4j.Ini;

import io.indico.api.ConfigLogic;

import static org.junit.Assert.assertTrue;

public class configTest {
    private static final String username = System.getenv("INDICO_USERNAME");
    private static final String password = System.getenv("INDICO_PASSWORD");
    private static final String cloud = System.getenv("INDICO_CLOUD");
    private static String[] iniFiles = {"data/testIniBlank", "data/testIniComplete",
                                       "data/testIniNoCloud", "data/testIniNoApiKey"};
    // A MockConfigLogic object that assumes all environment variables are not set
    private MockConfigLogic mockConfigLogic = new MockConfigLogic(null);

    // A version of the Config class where all environment variables are whatever
    // you send to the constructor
    private static class MockConfigLogic extends ConfigLogic {
        private String environVariable;

        public MockConfigLogic(String envVars) {
            environVariable = envVars;
        }

        /* Overrides the helper method getEnvVariable in ConfigLogic
           so that the tets can mock enviroment variables to be whatever
           string was sent to the constructor */
        @Override
        public String getEnvVariable(String envVarName) {
            return environVariable;
        }
    }

    @Test
    public void testCloud() throws Throwable {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL testIni = null;
            File config;

            // A MockConfigLogic object that assumes all environment variables have value
            // "indico-test-env"
            MockConfigLogic configLogic = new MockConfigLogic("indico-test-env");

            for (String iniFile: iniFiles) {
                testIni = classLoader.getResource(iniFile);
                config = new File(testIni.getFile());
                assertTrue("indico-test-env".equals(configLogic.getVariable("private_cloud", "cloud",
                                                                        "INDICO_CLOUD", new Ini(config))));
            }

            String[] someIniFiles = {"data/testIniComplete", "data/testIniNoApiKey"};

            for (String iniFile: someIniFiles) {
                testIni = classLoader.getResource(iniFile);
                config = new File(testIni.getFile());
                assertTrue("indico-test".equals(mockConfigLogic.getVariable("private_cloud", "cloud",
                                                                            "INDICO_CLOUD", new Ini(config))));
            }

            assertTrue(null == mockConfigLogic.getVariable("private_cloud", "cloud",
                                                          "INDICO_CLOUD", null));

        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testApiKey() throws Throwable {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL testIni = null;
            File config;

            MockConfigLogic configLogic = new MockConfigLogic("env-api-key");

            for (String iniFile: iniFiles) {
                testIni = classLoader.getResource(iniFile);
                config = new File(testIni.getFile());
                assertTrue("env-api-key".equals(configLogic.getVariable("auth", "api_key",
                                                                      "INDICO_API_KEY", new Ini(config))));
            }

            String[] someIniFiles = {"data/testIniComplete", "data/testIniNoCloud"};

            for (String iniFile: someIniFiles) {
                testIni = classLoader.getResource(iniFile);
                config = new File(testIni.getFile());
                assertTrue("11111111111111111111111111111111".equals(mockConfigLogic.getVariable("auth",
                                                                                                 "api_key",
                                                                                                 "INDICO_API_KEY",
                                                                                                 new Ini(config))));
            }

            assertTrue(null == mockConfigLogic.getVariable("auth", "api_key",
                                                          "INDICO_API_KEY", null));

        } catch (IOException e) {
            throw new Error(e);
        }
    }

}
