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
package ru.yandex.translate.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Mykola Tunyk <m at tunyk.com>
 */
public abstract class YandexTranslateAPI {
    protected static final String ENCODING = "UTF-8";
    protected static final String HEADER_ACCEEPT_CHARSET = "Accept-Charset";
    protected static final String HEADER_CONTENT_TYPE = "Content-Type";
    protected static final String HEADER_REFERER = "Referer";
    protected static final String METHOD_GET = "GET";

    protected static final String PARAM_LANG = "lang";
    protected static final String PARAM_TEXT = "text";

    protected static String referer;

    public static void setReferer(final String urlReferer) {
        referer = urlReferer;
    }

    /**
     * Validates if {@code YandexTranslateAPI.setReferer()} was called properly.
     *
     * @throws YandexAPIException if validation fails
     */
    protected void validate() throws YandexAPIException {
        if (referer == null || referer.isEmpty()) {
            throw new YandexAPIException(String.format("HTTP request header '%s' is not set.", HEADER_REFERER));
        }
    }

    /**
     * Validates required request parameters.
     *
     * @param lang translation direction
     * @param text the text to be validated
     * @throws YandexAPIException if validation fails
     */
    protected void validate(final Language lang, final String text) throws YandexAPIException {
        validate();

        if (lang == null) {
            throw new YandexAPIException(String.format("Service: Empty parameter '%s'", PARAM_LANG));
        }

        if (text == null || text.isEmpty()) {
            throw new YandexAPIException(String.format("Service: Empty parameter '%s'", PARAM_TEXT));
        }
    }

    /**
     * Sends HTTP request using GET method and returns the response as a String.
     *
     * @param url the URL
     * @return String representation of response
     * @throws IOException on error or if response status code is not 200 (OK)
     */
    protected String getResponse(final URL url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(HEADER_REFERER, referer);
        connection.setRequestProperty(HEADER_CONTENT_TYPE, "text/plain; charset=" + ENCODING);
        connection.setRequestProperty(HEADER_ACCEEPT_CHARSET, ENCODING);
        connection.setRequestMethod(METHOD_GET);
        connection.setDoOutput(true);

        try {
            return convertInputStreamToString(connection.getInputStream());
        } finally {
            connection.disconnect();
        }
    }

    private static String convertInputStreamToString(final InputStream inputStream) throws IOException {
        final StringBuilder outputBuilder = new StringBuilder();

        if (inputStream != null) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line.replaceAll("\\\\n", ""));
            }
        }

        return outputBuilder.toString();
    }
}
