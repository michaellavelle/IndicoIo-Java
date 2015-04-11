package io.indico.api.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import io.indico.api.Config;

public abstract class TestPrivateCloud {
    public static final Map<String, String> auth;

    static
    {
        auth = new HashMap<String, String>();
        auth.put("api_key" , System.getenv("INDICO_API_KEY"));
        auth.put("cloud" , "indico-test");
    }

    @Before
    public void changeCloudProtocol() {
        Config.CLOUD_PROTOCOL = "http://";
    }

    @After
    public void fixCloudProtocol() {
        Config.CLOUD_PROTOCOL = "https://";
    }
}
