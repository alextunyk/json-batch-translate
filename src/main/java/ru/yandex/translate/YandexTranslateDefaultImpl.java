/*
 * Copyright (c) 2011 Mykola Tunyk <m at tunyk.com>.
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
package ru.yandex.translate;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import ru.yandex.translate.api.Language;
import ru.yandex.translate.api.YandexAPIException;
import ru.yandex.translate.api.YandexTranslate;
import ru.yandex.translate.api.YandexTranslateAPI;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * @author Mykola Tunyk <m at tunyk.com>
 */
public final class YandexTranslateDefaultImpl extends YandexTranslateAPI implements YandexTranslate {
    private static final String SERVICE_METHOD_URL = "http://translate.yandex.ru/tr.json/%s";

    private static YandexTranslate instance = new YandexTranslateDefaultImpl();

    public static YandexTranslate getInstance() {
        return instance;
    }

    private YandexTranslateDefaultImpl() {
    }

    @Override
    public String[] getLangs() throws YandexAPIException {
        setReferer(referer);
        validate();

        try {
            URL url = new URL(String.format(SERVICE_METHOD_URL, "getLangs"));
            String response = getResponse(url);
            JSONArray jsonArray = (JSONArray) JSONValue.parse(response);

            return (String[]) jsonArray.toArray(new String[jsonArray.size()]);
        } catch (IOException e) {
            throw new YandexAPIException(e);
        }
    }

    @Override
    public String translate(String text, Language language) throws YandexAPIException {
        setReferer(referer);
        validate(language, text);

        try {
            String params = PARAM_LANG + "=" + URLEncoder.encode(language.toString(), ENCODING);
            params += "&" + PARAM_TEXT + "=" + URLEncoder.encode(text, ENCODING);

            URL url = new URL(String.format(SERVICE_METHOD_URL, "translate?") + params);
            String response = getResponse(url);

            return ((String) JSONValue.parse(response)).trim();
        } catch (IOException e) {
            throw new YandexAPIException(e);
        }
    }

    @Override
    public String[] translateArray(String[] text, Language language) throws YandexAPIException {
        setReferer(referer);
        validate(language, Arrays.toString(text));

        try {
            StringBuilder params = new StringBuilder();
            params.append(PARAM_LANG).append("=").append(URLEncoder.encode(language.toString(), ENCODING));

            for (String str : text) {
                params.append("&").append(PARAM_TEXT).append("=").append(URLEncoder.encode(str, ENCODING));
            }

            URL url = new URL(String.format(SERVICE_METHOD_URL, "translateArray?") + params);
            String response = getResponse(url);
            JSONArray jsonArray = (JSONArray) JSONValue.parse(response);

            return (String[]) jsonArray.toArray(new String[jsonArray.size()]);
        } catch (IOException e) {
            throw new YandexAPIException(e);
        }
    }

    @Override
    public String translateSentence(String text, Language language) throws YandexAPIException {
        setReferer(referer);
        validate(language, text);

        try {
            String params = PARAM_LANG + "=" + URLEncoder.encode(language.toString(), ENCODING) +
                    "&" + PARAM_TEXT + "=" + URLEncoder.encode(text, ENCODING);
            URL url = new URL(String.format(SERVICE_METHOD_URL, "translateSentence?") + params);
            String response = getResponse(url);

            return response; // returns origin response due to lack of documentation
        } catch (IOException e) {
            throw new YandexAPIException(e);
        }
    }
}
