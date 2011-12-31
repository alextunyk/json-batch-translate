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

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Mykola Tunyk <m at tunyk.com>
 */
public class YandexTranslateTest {
    private static final String referrer = "https://github.com/tunyk/json-batch-translate";

    /*
     * Tests YandexTranslate.DEFAULT.getLangs().
     * No exception should be thrown.
     */
    @Test
    public void testGetLangs() throws YandexAPIException {
        YandexTranslateAPI.setReferer(referrer);
        String[] langs = YandexTranslate.DEFAULT.getLangs();

        assertNotNull(langs);
        assertTrue(langs.length > 0);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translate(String, Language).
     * No exception should be thrown.
     */
    @Test
    public void testTranslate() throws YandexAPIException {
        final String russian = "Съешь еще этих мягких французских булок";
        final String english = "Eat more of these soft French rolls";
        final String french = "Mange encore ces petits-pains mous français";
        String result;

        YandexTranslateAPI.setReferer(referrer);
        result = YandexTranslate.DEFAULT.translate(russian, Language.RUSSIAN_ENGLISH);
        assertNotNull(result);
        assertEquals(english, result);

        YandexTranslateAPI.setReferer(referrer);
        result = YandexTranslate.DEFAULT.translate(russian, Language.RUSSIAN_FRENCH);
        assertNotNull(result);
        assertEquals(french, result);

        YandexTranslateAPI.setReferer(referrer);
        result = YandexTranslate.DEFAULT.translate(french, Language.FRENCH_RUSSIAN);
        assertNotNull(result);
        assertEquals("Еще съешь эти французские мягкие малышей-разновидности хлеба", result);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translate(String, Language).
     *
     * @throws YandexAPIException if no referer
     */
    @Test(expected = YandexAPIException.class)
    public void testTranslateNoRefererException() throws YandexAPIException {
        final String russian = "Съешь еще этих мягких французских булок";
        YandexTranslateAPI.setReferer(null);
        YandexTranslate.DEFAULT.translate(russian, Language.RUSSIAN_ENGLISH);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translate(String, Language).
     *
     * @throws YandexAPIException if unsupported translation
     */
    @Test(expected = YandexAPIException.class)
    public void testTranslateUnsupportedTranslationException() throws YandexAPIException {
        final String russian = "Съешь еще этих мягких французских булок";
        YandexTranslateAPI.setReferer(referrer);
        YandexTranslate.DEFAULT.translate(russian, null);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translateArray(String[], Language).
     * No exception should be thrown.
     */
    @Test
    public void testTranslateArray() throws YandexAPIException {
        final String[] origin = {"Съешь еще этих мягких французских булок.",
                "Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.",
                "Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф."};
        final String[] expectedRussianPolish = {"Zjedz jeszcze tych miękkich francuskich bułek.",
                "Szeroka gęślą jaźń daje potężny impuls rozwoju rolnictwa.",
                "Hej, жлоб! Gdzie asa? Wiesz młodych съемщиц w szafie."};
        final String[] expectedReverseTranslation = {"Съешь еще этих мягких французских булок.",
                "Широкая электрификация южных губерний дает мощный толчок развитию сельского хозяйства.",
                "Эй, жлоб! Где туз? Вы знаете молодых съемщиц в шкафу."};

        YandexTranslateAPI.setReferer(referrer);
        String[] result = YandexTranslate.DEFAULT.translateArray(origin, Language.RUSSIAN_POLISH);
        for (int i = 0; i < origin.length; i++) {
            assertEquals(expectedRussianPolish[i], result[i]);
        }

        YandexTranslateAPI.setReferer(referrer);
        result = YandexTranslate.DEFAULT.translateArray(expectedRussianPolish, Language.POLISH_RUSSIAN);
        for (int i = 0; i < origin.length; i++) {
            assertEquals(expectedReverseTranslation[i], result[i]);
        }
    }

    /**
     * Tests YandexTranslate.DEFAULT.translateArray(String[], Language).
     *
     * @throws YandexAPIException if no referer
     */
    @Test(expected = YandexAPIException.class)
    public void testTranslateArrayNoRefererException() throws YandexAPIException {
        final String[] origin = {"Съешь еще этих мягких французских булок.",
                "Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.",
                "Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф."};
        YandexTranslateAPI.setReferer(null);
        YandexTranslate.DEFAULT.translateArray(origin, Language.RUSSIAN_ENGLISH);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translateArray(String[], Language).
     *
     * @throws YandexAPIException if unsupported translation
     */
    @Test(expected = YandexAPIException.class)
    public void testTranslateArrayUnsupportedTranslationException() throws YandexAPIException {
        final String[] origin = {"Съешь еще этих мягких французских булок.",
                "Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства.",
                "Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф."};
        YandexTranslateAPI.setReferer(referrer);
        YandexTranslate.DEFAULT.translateArray(origin, null);
    }

    /**
     * Tests YandexTranslate.DEFAULT.translateSentence(String, Language).
     */
    @Ignore
    @Test
    public void testTranslateSentence() {
        // skipped
    }
}
