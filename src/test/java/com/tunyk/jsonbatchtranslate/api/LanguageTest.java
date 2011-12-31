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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alex Tunyk <alex at tunyk.com>
 */
public class LanguageTest {

    /**
     * Tests Language.toString(JsonBatchTranslate.API.GoogleTranslateAPI).
     * No exception should be thrown.
     */
    @Test
    public void testToStringWithGoogleTranslateAPI() throws JsonBatchTranslateException {
        assertEquals(com.google.api.translate.Language.ENGLISH.toString(),
                Language.ENGLISH.toString(JsonBatchTranslate.API.GoogleTranslateAPI));
        assertEquals(com.google.api.translate.Language.CHINESE_SIMPLIFIED.toString(),
                Language.CHINESE_SIMPLIFIED.toString(JsonBatchTranslate.API.GoogleTranslateAPI));
        assertEquals(com.google.api.translate.Language.CHINESE_TRADITIONAL.toString(),
                Language.CHINESE_TRADITIONAL.toString(JsonBatchTranslate.API.GoogleTranslateAPI));
        assertEquals(com.google.api.translate.Language.HEBREW.toString(),
                Language.HEBREW.toString(JsonBatchTranslate.API.GoogleTranslateAPI));
    }

    /**
     * Tests Language.toString(JsonBatchTranslate.API.GoogleTranslateAPI) with the case when unsupported language are passed.
     *
     * @throws JsonBatchTranslateException expected exception
     */
    @Test(expected = JsonBatchTranslateException.class)
    public void testToStringWithGoogleTranslateAPIException() throws JsonBatchTranslateException {
        // HATIAN_CREOLE is unsupported by GoogleTranslateAPI
        Language.HATIAN_CREOLE.toString(JsonBatchTranslate.API.GoogleTranslateAPI);
    }

    /**
     * Tests Language.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI).
     * No exception should be thrown.
     */
    @Test
    public void testToStringWithMicrosoftTranslatorAPI() throws JsonBatchTranslateException {
        assertEquals(com.memetix.mst.language.Language.ENGLISH.toString(),
                Language.ENGLISH.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI));
        assertEquals(com.memetix.mst.language.Language.CHINESE_SIMPLIFIED.toString(),
                Language.CHINESE_SIMPLIFIED.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI));
        assertEquals(com.memetix.mst.language.Language.CHINESE_TRADITIONAL.toString(),
                Language.CHINESE_TRADITIONAL.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI));
        assertEquals(com.memetix.mst.language.Language.HEBREW.toString(),
                Language.HEBREW.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI));
        assertEquals(com.memetix.mst.language.Language.HATIAN_CREOLE.toString(),
                Language.HATIAN_CREOLE.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI));
    }

    /**
     * Tests Language.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI) with the case when unsupported language are passed.
     *
     * @throws JsonBatchTranslateException expected exception
     */
    @Test(expected = JsonBatchTranslateException.class)
    public void testToStringWithMicrosoftTranslatorAPIException() throws JsonBatchTranslateException {
        // CHINESE is unsupported by MicrosoftTranslatorAPI, CHINESE_SIMPLIFIED or CHINESE_TRADITIONAL should be used
        Language.CHINESE.toString(JsonBatchTranslate.API.MicrosoftTranslatorAPI);
    }

    /**
     * Tests Language.toString(JsonBatchTranslate.API.YandexAPI).
     * No exception should be thrown.
     */
    @Test
    public void testToStringWithYandexAPI() throws JsonBatchTranslateException {
        String sourceLang = ru.yandex.translate.api.Language.ENGLISH_RUSSIAN.getSourceLanguage();
        String targetLang = ru.yandex.translate.api.Language.ENGLISH_RUSSIAN.getTargetLanguage();
        assertEquals(sourceLang, Language.ENGLISH.toString(JsonBatchTranslate.API.YandexAPI));
        assertEquals(targetLang, Language.RUSSIAN.toString(JsonBatchTranslate.API.YandexAPI));
    }

    /**
     * Tests Language.toString(JsonBatchTranslate.API.YandexAPI) with the case when unsupported language are passed.
     *
     * @throws JsonBatchTranslateException expected exception
     */
    @Test(expected = JsonBatchTranslateException.class)
    public void testToStringWithYandexAPIException() throws JsonBatchTranslateException {
        Language.CHINESE_SIMPLIFIED.toString(JsonBatchTranslate.API.YandexAPI);
    }
}
