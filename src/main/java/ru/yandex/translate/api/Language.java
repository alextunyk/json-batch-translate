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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mykola Tunyk <m at tunyk.com>
 */
public enum Language {
    ENGLISH_RUSSIAN("en-ru"),
    RUSSIAN_ENGLISH("ru-en"),
    RUSSIAN_UKRANIAN("ru-uk"),
    UKRANIAN_RUSSIAN("uk-ru"),
    POLISH_RUSSIAN("pl-ru"),
    RUSSIAN_POLISH("ru-pl"),
    TURKISH_RUSSIAN("tr-ru"),
    RUSSIAN_TURKISH("ru-tr"),
    GERMAN_RUSSIAN("de-ru"),
    RUSSIAN_GERMAN("ru-de"),
    FRENCH_RUSSIAN("fr-ru"),
    RUSSIAN_FRENCH("ru-fr"),
    ITALIAN_RUSSIAN("it-ru"),
    SPANISH_RUSSIAN("es-ru"),
    RUSSIAN_SPANISH("ru-es");

    private final String languagePair;

    private Language(final String languagePair) {
        this.languagePair = languagePair;
    }

    /**
     * Returns list of language codes supported by Yandex Translate.
     *
     * @return list of language codes
     */
    public static String[] list() {
        final Set<String> set = new HashSet<String>();

        for (final Language lang : values()) {
            set.add(lang.getSourceLanguage());
            set.add(lang.getTargetLanguage());
        }

        return set.toArray(new String[set.size()]);
    }

    /**
     * Returns Language representation of translation direction supported by Yandex Translation.
     * Returns {@code Null} if unknown pair of language.
     *
     * @param sourceLanguage source language
     * @param targetLanguage target language
     * @return translation direction in format used by Yandex Translation
     */
    public static Language fromString(final String sourceLanguage, final String targetLanguage) {
        final String langPair = (sourceLanguage + "-" + targetLanguage);

        for (final Language lang : values()) {
            if (lang.toString().equals(langPair)) {
                return lang;
            }
        }

        return null;
    }

    /**
     * Returns String representation of source language.
     *
     * @return source language
     */
    public String getSourceLanguage() {
        return languagePair.split("\\-")[0];
    }

    /**
     * Returns String representation of target language.
     *
     * @return target language
     */
    public String getTargetLanguage() {
        return languagePair.split("\\-")[1];
    }

    /**
     * Returns String representation of pair of languages supported by Yandex Translation.
     *
     * @return translate direction
     */
    @Override
    public String toString() {
        return languagePair;
    }
}
