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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Mykola Tunyk <m at tunyk.com>
 */
public class LanguageTest {

    /**
     *
     */
    @Test
    public void testList() throws YandexAPIException {
        String[] supportedLangs = YandexTranslate.DEFAULT.getLangs();
        assertNotNull(supportedLangs);
        assertTrue(Language.list().length < supportedLangs.length);
    }

    /**
     * Tests Language.fromString(String, String).
     */
    @Test
    public void testFromString() {
        assertEquals(Language.ENGLISH_RUSSIAN, Language.fromString("en", "ru"));

        Language expected = null;
        Language actual = Language.fromString("en", "fr");
        assertEquals(expected, actual);
    }

    /**
     * Tests getSourceLanguage method, of class Language.
     */
    @Test
    public void testGetSourceLanguage() {
        assertEquals("de", Language.GERMAN_RUSSIAN.getSourceLanguage());
    }

    /**
     * Tests getTargetLanguage method, of class Language.
     */
    @Test
    public void testGetTargetLanguage() {
        assertEquals("uk", Language.RUSSIAN_UKRANIAN.getTargetLanguage());
    }
}
