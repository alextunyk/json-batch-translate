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

import com.tunyk.jsonbatchtranslate.JsonBatchTranslateDefaultImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Alex Tunyk <alex at tunyk.com>
 */
public interface JsonBatchTranslate {
    enum API {
        GoogleTranslateAPI,
        MicrosoftTranslatorAPI,
        YandexAPI
    }

    /**
     * Default implementation of the JsonBatchTranslate API.
     */
    JsonBatchTranslate DEFAULT = JsonBatchTranslateDefaultImpl.getInstance();

    /**
     * Translates JSON object all properties from a source language to target language using corresponding API.
     *
     * @param api        desired API provider of translation service
     * @param key        a key for given API
     * @param json       JSON object to be translated
     * @param sourceLang source language
     * @param targetLang target language
     * @param referrer   referer to a client
     * @return JSONObject with translated values
     * @throws JsonBatchTranslateException
     * @throws JSONException
     */
    JSONObject execute(final @Nonnull API api,
                       final @Nonnull String key,
                       final @Nonnull JSONObject json,
                       final @Nonnull Language sourceLang,
                       final @Nonnull Language targetLang,
                       final @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException;


    /**
     * Translates JSON object given properties from a source language to target language using corresponding API.
     *
     * @param api        desired API provider of translation service
     * @param key        a key for given API
     * @param json       JSON object to be translated
     * @param sourceLang source language
     * @param targetLang target language
     * @param referrer   referer to a client
     * @param properties list of JSON object keys which values should be translated
     * @return JSONObject with translated values
     * @throws JsonBatchTranslateException
     * @throws JSONException
     */
    JSONObject execute(final @Nonnull API api,
                       final @Nonnull String key,
                       final @Nonnull JSONObject json,
                       final @Nonnull Language sourceLang,
                       final @Nonnull Language targetLang,
                       final @Nullable List<String> properties,
                       final @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException;

    /**
     * Translates JSON object all properties from a source language to given set of languages using corresponding API.
     *
     * @param api         desired API provider of translation service
     * @param key         a key for given API
     * @param json        JSON object to be translated
     * @param sourceLang  source language
     * @param targetLangs list of target languages
     * @param referrer    referer to a client
     * @return array of JSON objects with translated values
     * @throws JsonBatchTranslateException
     * @throws JSONException
     */
    JSONArray execute(final @Nonnull API api,
                      final @Nonnull String key,
                      final @Nonnull JSONObject json,
                      final @Nonnull Language sourceLang,
                      final @Nonnull Language[] targetLangs,
                      final @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException;

    /**
     * Translates JSON object given properties from a source language to given set of languages using corresponding API.
     *
     * @param api         desired API provider of translation service
     * @param key         a key for given API
     * @param json        JSON object to be translated
     * @param sourceLang  source language
     * @param targetLangs list of target languages
     * @param properties  list of JSON object keys which values should be translated
     * @param referrer    referer to a client
     * @return array of JSON objects with translated values
     * @throws JsonBatchTranslateException
     * @throws JSONException
     */
    JSONArray execute(final @Nonnull API api,
                      final @Nonnull String key,
                      final @Nonnull JSONObject json,
                      final @Nonnull Language sourceLang,
                      final @Nonnull Language[] targetLangs,
                      final @Nullable List<String> properties,
                      final @Nullable String referrer)
            throws JsonBatchTranslateException, JSONException;

}
