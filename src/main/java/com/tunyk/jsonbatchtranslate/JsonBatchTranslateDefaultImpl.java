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
package com.tunyk.jsonbatchtranslate;

import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.memetix.mst.translate.Translate;
import com.tunyk.jsonbatchtranslate.api.JsonBatchTranslate;
import com.tunyk.jsonbatchtranslate.api.JsonBatchTranslateException;
import com.tunyk.jsonbatchtranslate.api.Language;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.translate.api.YandexAPIException;
import ru.yandex.translate.api.YandexTranslate;
import ru.yandex.translate.api.YandexTranslateAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Alex Tunyk <alex at tunyk.com>
 */
public final class JsonBatchTranslateDefaultImpl implements JsonBatchTranslate {
    private static final Logger LOG = LoggerFactory.getLogger(JsonBatchTranslateDefaultImpl.class);

    private static JsonBatchTranslate instance = new JsonBatchTranslateDefaultImpl();
    private static Long uid;
    private static Long startUID;

    private Map<Long, String> stringMap = new HashMap<Long, String>();
    private JSONObject jsonRef;


    public static JsonBatchTranslate getInstance() {
        LOG.info("JsonBatchTranslate.DEFAULT");
        return instance;
    }

    private JsonBatchTranslateDefaultImpl() {
    }

    private static synchronized Long getUID() {
        return uid++;
    }

    @Override
    public JSONObject execute(@Nonnull API api, @Nonnull String key, @Nonnull JSONObject json,
                              @Nonnull Language sourceLang, @Nonnull Language targetLang, @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException {

        return execute(api, key, json, sourceLang, targetLang, null, referrer);
    }

    @Override
    public JSONObject execute(@Nonnull API api, @Nonnull String key, @Nonnull JSONObject json,
                              @Nonnull Language sourceLang, @Nonnull Language targetLang, @Nullable List<String> properties, @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException {
        String[] textToTranslate = convertJsonToStringArray(json, properties);
        String[] translatedText = translate(textToTranslate, api, key, sourceLang, targetLang, referrer);

        return integrateTranslationToJson(translatedText);
    }

    @Override
    public JSONArray execute(@Nonnull API api, @Nonnull String key, @Nonnull JSONObject json,
                             @Nonnull Language sourceLang, @Nonnull Language[] targetLangs, @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException {

        return execute(api, key, json, sourceLang, targetLangs, null, referrer);
    }

    @Override
    public JSONArray execute(@Nonnull API api, @Nonnull String key, @Nonnull JSONObject json,
                             @Nonnull Language sourceLang, @Nonnull Language[] targetLangs, @Nullable List<String> properties, @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException {
        return null;  //todo
    }


    /* private */

    private String[] convertJsonToStringArray(JSONObject json, List<String> properties) throws JSONException {
        uid = System.currentTimeMillis();
        startUID = uid;
        stringMap.clear();
        jsonRef = iterateJSONObject(json, null, properties);

        return convertStringMapToArray(stringMap);
    }

    private String[] translate(String[] textToTranslate, API api, String key, Language sourceLang, Language targetLang, String referrer) throws JsonBatchTranslateException {
        String[] translatedText;
        switch (api) {
            case GoogleTranslateAPI:
                LOG.debug("GoogleTranslateAPI");
                GoogleAPI.setHttpReferrer(referrer);
                GoogleAPI.setKey(key);
                try {
                    translatedText = com.google.api.translate.Translate.DEFAULT.execute(textToTranslate,
                            com.google.api.translate.Language.fromString(sourceLang.toString(api)),
                            com.google.api.translate.Language.fromString(targetLang.toString(api)));
                } catch (GoogleAPIException ge) {
                    throw new JsonBatchTranslateException(ge);
                }
                break;
            case MicrosoftTranslatorAPI:
                LOG.debug("MicrosoftTranslatorAPI");
                Translate.setKey(key);
                try {
                    translatedText = com.memetix.mst.translate.Translate.execute(textToTranslate,
                            com.memetix.mst.language.Language.fromString(sourceLang.toString(api)),
                            com.memetix.mst.language.Language.fromString(targetLang.toString(api)));
                } catch (Exception mte) {
                    throw new JsonBatchTranslateException(mte);
                }
                break;
            case YandexAPI:
                LOG.debug("YandexAPI");
                YandexTranslateAPI.setReferer(referrer);
                try {
                    translatedText = YandexTranslate.DEFAULT.translateArray(textToTranslate,
                            ru.yandex.translate.api.Language.fromString(sourceLang.toString(api), targetLang.toString(api)));
                } catch (YandexAPIException ye) {
                    throw new JsonBatchTranslateException(ye);
                }
                break;
            default:
                throw new JsonBatchTranslateException(String.format("API is not supported, %s", api.name()));
        }
        return translatedText;
    }

    private JSONObject integrateTranslationToJson(String[] translatedText) throws JSONException {
        for (int i = 0; i < translatedText.length; i++) {
            stringMap.put(startUID + i, translatedText[i]);
        }

        return reverseIterateJSONObject(jsonRef);
    }

    private JSONObject iterateJSONObject(JSONObject json, String namespace, List<String> namespacesToInclude) throws JSONException {
        JSONObject res = new JSONObject();
        Iterator itr = json.keys();

        if (namespace == null) {
            namespace = "";
        }

        while (itr.hasNext()) {
            String key = (String) itr.next();
            Object obj = json.get(key);
            String namespaceKey = namespace + "." + key;

            if (obj instanceof JSONObject) {
                JSONObject innerJson = iterateJSONObject((JSONObject) obj, namespace + "." + key, namespacesToInclude);
                res.put(key, innerJson);
            } else if (obj instanceof JSONArray) {
                if (isNamespaceIncluded(namespaceKey, namespacesToInclude)) {
                    JSONArray innerJson = iterateJSONArray((JSONArray) obj, namespace + "." + key, namespacesToInclude);
                    res.put(key, innerJson);
                } else {
                    res.put(key, obj);
                }
            } else if ((obj instanceof String) && isNamespaceIncluded(namespaceKey, namespacesToInclude)) {
                Long id = getUID();
                stringMap.put(id, obj.toString());
                res.put(key, id);
            } else {
                res.put(key, obj);
            }
        }

        return res;
    }

    private JSONArray iterateJSONArray(JSONArray json, String namespace, List<String> namespacesToInclude) throws JSONException {
        JSONArray res = new JSONArray();
        if (namespace == null) {
            namespace = "";
        }
        for (int i = 0; i < json.length(); i++) {
            Object obj = json.get(i);
            if (obj instanceof JSONObject) {
                JSONObject innerJson = iterateJSONObject((JSONObject) obj, namespace + "." + Integer.toString(i), namespacesToInclude);
                res.put(i, innerJson);
            } else if (obj instanceof JSONArray) {
                JSONArray innerJson = iterateJSONArray((JSONArray) obj, namespace + "." + Integer.toString(i), namespacesToInclude);
                res.put(i, innerJson);
            } else if (obj instanceof String) {
                Long id = getUID();
                stringMap.put(id, obj.toString());
                res.put(i, id);
            } else {
                res.put(i, obj);
            }
        }

        return res;
    }

    private JSONObject reverseIterateJSONObject(JSONObject json) throws JSONException {
        JSONObject res = new JSONObject();
        Iterator itr = json.keys();

        while (itr.hasNext()) {
            String key = (String) itr.next();
            Object obj = json.get(key);

            if (obj instanceof JSONObject) {
                JSONObject innerJson = reverseIterateJSONObject((JSONObject) obj);
                res.put(key, innerJson);
            } else if (obj instanceof JSONArray) {
                JSONArray innerJson = reverseIterateJSONArray((JSONArray) obj);
                res.put(key, innerJson);
            } else if ((obj instanceof Long) && stringMap.containsKey(obj)) {
                res.put(key, stringMap.get(obj));
            } else {
                res.put(key, obj);
            }
        }

        return res;
    }

    private JSONArray reverseIterateJSONArray(JSONArray json) throws JSONException {
        JSONArray res = new JSONArray();

        for (int i = 0; i < json.length(); i++) {
            Object obj = json.get(i);

            if (obj instanceof JSONObject) {
                JSONObject innerJson = reverseIterateJSONObject((JSONObject) obj);
                res.put(i, innerJson);
            } else if (obj instanceof JSONArray) {
                JSONArray innerJson = reverseIterateJSONArray((JSONArray) obj);
                res.put(i, innerJson);
            } else if ((obj instanceof Long) && stringMap.containsKey(obj)) {
                res.put(i, stringMap.get(obj));
            } else {
                res.put(i, obj);
            }
        }

        return res;
    }

    private String[] convertStringMapToArray(Map<Long, String> map) {
        String[] res = new String[map.size()];

        for (int i = 0; i < map.size(); i++) {
            res[i] = map.get(startUID + i);
        }

        return res;
    }

    private boolean isNamespaceIncluded(String namespace, List<String> namespacesToInclude) {
        if (namespacesToInclude == null || namespacesToInclude.isEmpty() || namespace == null || namespace.isEmpty()) {
            return true;
        }

        boolean isIncluded = false;
        for (String namespaceKey : namespacesToInclude) {
            namespaceKey = "." + namespaceKey;
            if (namespace.equals(namespaceKey) || namespace.startsWith(namespaceKey + ".")) {
                isIncluded = true;
            }
        }

        return isIncluded;
    }
}

