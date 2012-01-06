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

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
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

    private static String in; // content will be loaded from input.json file

    @BeforeClass
    public static void setUp() throws IOException {
        Properties properties = new Properties();
        URL url = ClassLoader.getSystemResource("config.properties");
        properties.load(url.openStream());
        googleTranslateApiKey = properties.getProperty("google.translate.api.key");
        microsoftTranslatorApiKey = properties.getProperty("microsoft.translator.api.key");
        yandexApiKey = null;
        in = FileUtils.readFileToString(new File(ClassLoader.getSystemResource("input.json").getPath()));
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
                json, Language.ENGLISH, Language.CZECH, Arrays.asList("menu.value", "menu.popup.menuitem[]",
                "menu.popup.menuitem2[].value", "menu.popup.menuitem3[][]", "menu.popup.menuitem4[]",
                "menu.popup.menuitem5[][][]", "menu.popup.menuitem6"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONObject popup = menu.getJSONObject("popup");
        final JSONArray menuItem = popup.getJSONArray("menuitem");
        final JSONArray menuItem2 = popup.getJSONArray("menuitem2");
        final JSONArray menuItem3 = popup.getJSONArray("menuitem3");
        final JSONArray menuItem4 = popup.getJSONArray("menuitem4");
        final JSONArray menuItem5 = popup.getJSONArray("menuitem5");
        final JSONObject menuItem6 = popup.getJSONObject("menuitem6");

        assertEquals("file", menu.getString("id"));
        assertEquals("Soubor", menu.getString("value"));
        assertEquals("Nový", menuItem.getJSONObject(0).getString("value"));
//        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Otevřeno", menuItem.getJSONObject(1).getString("value"));
//        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Zavřít", menuItem.getJSONObject(2).getString("value"));
//        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));

        assertEquals("Nový", menuItem2.getJSONObject(0).getString("value"));
        assertEquals("New File", menuItem2.getJSONObject(0).getString("onclick"));
        assertEquals("Otevřeno", menuItem2.getJSONObject(1).getString("value"));
        assertEquals("Open File", menuItem2.getJSONObject(1).getString("onclick"));
        assertEquals("Zavřít", menuItem2.getJSONObject(2).getString("value"));
        assertEquals("Close File", menuItem2.getJSONObject(2).getString("onclick"));

        assertEquals("Nový", menuItem3.getJSONArray(0).getString(0));
        assertEquals("Nový Soubor", menuItem3.getJSONArray(0).getString(1));
        assertEquals("Otevřeno", menuItem3.getJSONArray(1).getString(0));
        assertEquals("Otevřít Soubor", menuItem3.getJSONArray(1).getString(1));
        assertEquals("Zavřít", menuItem3.getJSONArray(2).getString(0));
        assertEquals("Zavřít Soubor", menuItem3.getJSONArray(2).getString(1));

        assertEquals("Nový", menuItem4.getJSONArray(0).getString(0));
        assertEquals("Nový Soubor", menuItem4.getJSONArray(0).getString(1));
        assertEquals("Otevřeno", menuItem4.getJSONArray(1).getString(0));
        assertEquals("Otevřít Soubor", menuItem4.getJSONArray(1).getString(1));
        assertEquals("Zavřít", menuItem4.getJSONArray(2).getString(0));
        assertEquals("Zavřít Soubor", menuItem4.getJSONArray(2).getString(1));

        assertEquals("New", menuItem5.getJSONArray(0).getString(0));
        assertEquals("New File", menuItem5.getJSONArray(0).getString(1));
        assertEquals("Open", menuItem5.getJSONArray(1).getString(0));
        assertEquals("Open File", menuItem5.getJSONArray(1).getString(1));
        assertEquals("Zavřít", menuItem5.getJSONArray(2).getJSONArray(0).getString(0));
        assertEquals("Zavřít Soubor", menuItem5.getJSONArray(2).getJSONArray(0).getString(1));
        assertEquals("Zavřete ho", menuItem5.getJSONArray(2).getJSONArray(1).getString(0));
        assertEquals("Zavřít tento Soubor", menuItem5.getJSONArray(2).getJSONArray(1).getString(1));

        assertEquals("Smazat Soubor", menuItem6.getJSONObject("submenuitem").getString("value"));
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
                json, Language.ENGLISH, Language.DUTCH, Arrays.asList("menu.value", "menu.popup.menuitem[]",
                "menu.popup.menuitem2[].value", "menu.popup.menuitem3[][]", "menu.popup.menuitem4[]",
                "menu.popup.menuitem5[][][]", "menu.popup.menuitem6"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONObject popup = menu.getJSONObject("popup");
        final JSONArray menuItem = popup.getJSONArray("menuitem");
        final JSONArray menuItem2 = popup.getJSONArray("menuitem2");
        final JSONArray menuItem3 = popup.getJSONArray("menuitem3");
        final JSONArray menuItem4 = popup.getJSONArray("menuitem4");
        final JSONArray menuItem5 = popup.getJSONArray("menuitem5");
        final JSONObject menuItem6 = popup.getJSONObject("menuitem6");

        assertEquals("file", menu.getString("id"));
        assertEquals("Bestand", menu.getString("value"));
        assertEquals("Nieuw", menuItem.getJSONObject(0).getString("value"));
        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Open", menuItem.getJSONObject(1).getString("value"));
        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Sluiten", menuItem.getJSONObject(2).getString("value"));
        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));

        assertEquals("Nieuw", menuItem2.getJSONObject(0).getString("value"));
        assertEquals("New File", menuItem2.getJSONObject(0).getString("onclick"));
        assertEquals("Open", menuItem2.getJSONObject(1).getString("value"));
        assertEquals("Open File", menuItem2.getJSONObject(1).getString("onclick"));
        assertEquals("Sluiten", menuItem2.getJSONObject(2).getString("value"));
        assertEquals("Close File", menuItem2.getJSONObject(2).getString("onclick"));

        assertEquals("Nieuw", menuItem3.getJSONArray(0).getString(0));
        assertEquals("Nieuw bestand", menuItem3.getJSONArray(0).getString(1));
        assertEquals("Open", menuItem3.getJSONArray(1).getString(0));
        assertEquals("Bestand openen", menuItem3.getJSONArray(1).getString(1));
        assertEquals("Sluiten", menuItem3.getJSONArray(2).getString(0));
        assertEquals("Bestand sluiten", menuItem3.getJSONArray(2).getString(1));

        assertEquals("Nieuw", menuItem4.getJSONArray(0).getString(0));
        assertEquals("Nieuw bestand", menuItem4.getJSONArray(0).getString(1));
        assertEquals("Open", menuItem4.getJSONArray(1).getString(0));
        assertEquals("Bestand openen", menuItem4.getJSONArray(1).getString(1));
        assertEquals("Sluiten", menuItem4.getJSONArray(2).getString(0));
        assertEquals("Bestand sluiten", menuItem4.getJSONArray(2).getString(1));

        assertEquals("New", menuItem5.getJSONArray(0).getString(0));
        assertEquals("New File", menuItem5.getJSONArray(0).getString(1));
        assertEquals("Open", menuItem5.getJSONArray(1).getString(0));
        assertEquals("Open File", menuItem5.getJSONArray(1).getString(1));
        assertEquals("Sluiten", menuItem5.getJSONArray(2).getJSONArray(0).getString(0));
        assertEquals("Bestand sluiten", menuItem5.getJSONArray(2).getJSONArray(0).getString(1));
        assertEquals("Sluit het", menuItem5.getJSONArray(2).getJSONArray(1).getString(0));
        assertEquals("Sluit dit bestand", menuItem5.getJSONArray(2).getJSONArray(1).getString(1));

        assertEquals("Bestand verwijderen", menuItem6.getJSONObject("submenuitem").getString("value"));
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
                json, Language.ENGLISH, Language.RUSSIAN, Arrays.asList("menu.value", "menu.popup.menuitem[]",
                "menu.popup.menuitem2[].value", "menu.popup.menuitem3[][]", "menu.popup.menuitem4[]",
                "menu.popup.menuitem5[][][]", "menu.popup.menuitem6"), referrer);
        final JSONObject menu = out.getJSONObject("menu");
        final JSONObject popup = menu.getJSONObject("popup");
        final JSONArray menuItem = popup.getJSONArray("menuitem");
        final JSONArray menuItem2 = popup.getJSONArray("menuitem2");
        final JSONArray menuItem3 = popup.getJSONArray("menuitem3");
        final JSONArray menuItem4 = popup.getJSONArray("menuitem4");
        final JSONArray menuItem5 = popup.getJSONArray("menuitem5");
        final JSONObject menuItem6 = popup.getJSONObject("menuitem6");

        assertEquals("file", menu.getString("id"));
        assertEquals("Файл", menu.getString("value"));
        assertEquals("Новый", menuItem.getJSONObject(0).getString("value"));
//        assertEquals("createNewDoc", menuItem.getJSONObject(0).getString("onclick"));
        assertEquals("Откройте", menuItem.getJSONObject(1).getString("value"));
//        assertEquals("openDoc", menuItem.getJSONObject(1).getString("onclick"));
        assertEquals("Закрыть", menuItem.getJSONObject(2).getString("value"));
//        assertEquals("closeDoc", menuItem.getJSONObject(2).getString("onclick"));

        assertEquals("Новый", menuItem2.getJSONObject(0).getString("value"));
        assertEquals("New File", menuItem2.getJSONObject(0).getString("onclick"));
        assertEquals("Откройте", menuItem2.getJSONObject(1).getString("value"));
        assertEquals("Open File", menuItem2.getJSONObject(1).getString("onclick"));
        assertEquals("Закрыть", menuItem2.getJSONObject(2).getString("value"));
        assertEquals("Close File", menuItem2.getJSONObject(2).getString("onclick"));

        assertEquals("Новый", menuItem3.getJSONArray(0).getString(0));
        assertEquals("Новый файл", menuItem3.getJSONArray(0).getString(1));
        assertEquals("Открыть", menuItem3.getJSONArray(1).getString(0));
        assertEquals("Открыть файл", menuItem3.getJSONArray(1).getString(1));
        assertEquals("Закрыть", menuItem3.getJSONArray(2).getString(0));
        assertEquals("Закрыть файл", menuItem3.getJSONArray(2).getString(1));

        assertEquals("Новый", menuItem4.getJSONArray(0).getString(0));
        assertEquals("Новый файл", menuItem4.getJSONArray(0).getString(1));
        assertEquals("Открыть", menuItem4.getJSONArray(1).getString(0));
        assertEquals("Открыть файл", menuItem4.getJSONArray(1).getString(1));
        assertEquals("Закрыть", menuItem4.getJSONArray(2).getString(0));
        assertEquals("Закрыть файл", menuItem4.getJSONArray(2).getString(1));

        assertEquals("New", menuItem5.getJSONArray(0).getString(0));
        assertEquals("New File", menuItem5.getJSONArray(0).getString(1));
        assertEquals("Open", menuItem5.getJSONArray(1).getString(0));
        assertEquals("Open File", menuItem5.getJSONArray(1).getString(1));
        assertEquals("Закрыть", menuItem5.getJSONArray(2).getJSONArray(0).getString(0));
        assertEquals("Закрыть файл", menuItem5.getJSONArray(2).getJSONArray(0).getString(1));
        assertEquals("Закрыть его", menuItem5.getJSONArray(2).getJSONArray(1).getString(0));
        assertEquals("Закрыть файл", menuItem5.getJSONArray(2).getJSONArray(1).getString(1));

        assertEquals("Удалить файл", menuItem6.getJSONObject("submenuitem").getString("value"));
    }
}
