/*
 * Copyright (c) 2011 Alex Tunyk <alex at tunyk.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 */
package com.tunyk.jsonbatchtranslate.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Alex Tunyk <alex at tunyk.com>
 */
public class JsonBatchTranslateTest {
    private static final String referrer = "https://github.com/tunyk/json-batch-translate";
    private static String googleTranslateApiKey;
    private static String microsoftTranslatorApiKey;
    private static String yandexApiKey;

    private final String in = "{\"menu\": {\n" +
            "    \"id\": \"file\",\n" +
            "    \"value\": \"File\",\n" +
            "    \"popup\": {\n" +
            "        \"menuitem\": [\n" +
            "            {\"value\": \"New\", \"onclick\": \"createNewDoc\"},\n" +
            "            {\"value\": \"Open\", \"onclick\": \"openDoc\"},\n" +
            "            {\"value\": \"Close\", \"onclick\": \"closeDoc\"}\n" +
            "        ]\n" +
            "    }\n" +
            "}}";

    @BeforeClass
    public static void setUp() throws IOException {
        Properties properties = new Properties();
        URL url = ClassLoader.getSystemResource("config.properties");
        properties.load(url.openStream());
        googleTranslateApiKey = properties.getProperty("google.translate.api.key");
        microsoftTranslatorApiKey = properties.getProperty("microsoft.translator.api.key");
        yandexApiKey = null;
    }

    @Test
    public void testGoogleTranslateAPI() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.GoogleTranslateAPI, googleTranslateApiKey,
                json, Language.ENGLISH, Language.CZECH, referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("soubor", menu.getString("id"));
        assertEquals("Soubor", menu.getString("value"));
        assertEquals("Nový", menuItem.getJSONObject(0).getString("value"));
        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Otevřeno", menuItem.getJSONObject(1).getString("value"));
        assertEquals("OpenDoc", menuItem.getJSONObject(1).getString("onclick")); // weird google
        assertEquals("Zavřít", menuItem.getJSONObject(2).getString("value"));
        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));
    }

    @Test
    public void testGoogleTranslateAPIPropeties() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.GoogleTranslateAPI, googleTranslateApiKey,
                json, Language.ENGLISH, Language.CZECH, Arrays.asList("menu.value", "menu.popup.menuitem"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("file", menu.getString("id"));
        assertEquals("Soubor", menu.getString("value"));
        assertEquals("Nový", menuItem.getJSONObject(0).getString("value"));
//        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Otevřeno", menuItem.getJSONObject(1).getString("value"));
//        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Zavřít", menuItem.getJSONObject(2).getString("value"));
//        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));
    }

    @Test
    public void testMicrosoftTranslatorAPI() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.MicrosoftTranslatorAPI, microsoftTranslatorApiKey,
                json, Language.ENGLISH, Language.DUTCH, referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("bestand", menu.getString("id"));
        assertEquals("Bestand", menu.getString("value"));
        assertEquals("Nieuw", menuItem.getJSONObject(0).getString("value"));
        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Open", menuItem.getJSONObject(1).getString("value"));
        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Sluiten", menuItem.getJSONObject(2).getString("value"));
        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));
    }

    @Test
    public void testMicrosoftTranslatorAPIPropeties() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.MicrosoftTranslatorAPI, microsoftTranslatorApiKey,
                json, Language.ENGLISH, Language.DUTCH, Arrays.asList("menu.value", "menu.popup.menuitem"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("file", menu.getString("id"));
        assertEquals("Bestand", menu.getString("value"));
        assertEquals("Nieuw", menuItem.getJSONObject(0).getString("value"));
        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Open", menuItem.getJSONObject(1).getString("value"));
        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Sluiten", menuItem.getJSONObject(2).getString("value"));
        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));
    }

    @Test
    public void testYandexAPI() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.YandexAPI, yandexApiKey,
                json, Language.ENGLISH, Language.RUSSIAN, referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("файл", menu.getString("id"));
        assertEquals("Файл", menu.getString("value"));
        assertEquals("Новый", menuItem.getJSONObject(0).getString("value"));
        assertEquals("CreateNewDoc", menuItem.getJSONObject(0).getString("onclick")); // weird yandex
        assertEquals("Откройте", menuItem.getJSONObject(1).getString("value"));
        assertEquals("OpenDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Закрыть", menuItem.getJSONObject(2).getString("value"));
        assertEquals("CloseDoc", menuItem.getJSONObject(2).getString("onclick"));
    }

    @Test
    public void testYandexAPIPropeties() throws JsonBatchTranslateException, JSONException {
        JSONObject json = new JSONObject(in);
        JSONObject out = JsonBatchTranslate.DEFAULT.execute(JsonBatchTranslate.API.YandexAPI, yandexApiKey,
                json, Language.ENGLISH, Language.RUSSIAN, Arrays.asList("menu.value", "menu.popup.menuitem"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONArray menuItem = menu.getJSONObject("popup").getJSONArray("menuitem");

        assertEquals("file", menu.getString("id"));
        assertEquals("Файл", menu.getString("value"));
        assertEquals("Новый", menuItem.getJSONObject(0).getString("value"));
//        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Откройте", menuItem.getJSONObject(1).getString("value"));
//        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Закрыть", menuItem.getJSONObject(2).getString("value"));
//        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));
    }
}
