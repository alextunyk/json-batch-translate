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


import ru.yandex.translate.YandexTranslateDefaultImpl;

/**
 * Take note, Yandex.Translate service is not API service.
 * Currently, the Yandex.Translate service is available as a public beta version
 * and is provided to the user for personal, non commercial use.
 * For more details see http://legal.yandex.ru/translate_termsofuse/ and http://legal.yandex.ru/rules/
 * <p/>
 * Java wrapper for Yandex.Translate service.
 * Supported methods according to http://translate.yandex.ru/tr
 *
 * @author Mykola Tunyk <m at tunyk.com>
 */
public interface YandexTranslate {
    /**
     * Default instance of the YandexTranslate API.
     */
    YandexTranslate DEFAULT = YandexTranslateDefaultImpl.getInstance();

    /**
     * Returns supported pairs of languages used for translating.
     *
     * @return the list of language codes in Yandex Translate format
     * @throws YandexAPIException on error
     */
    String[] getLangs() throws YandexAPIException;

    /**
     * Translates text from a given origin Language to given target Language.
     *
     * @param text     the String to translate
     * @param language the language code of translation direction
     * @return the translated string
     * @throws YandexAPIException on error or if missed parameters
     */
    String translate(final String text, final Language language) throws YandexAPIException;

    /**
     * Translates text set.
     *
     * @param text     the String array to translate
     * @param language the language code of translation direction
     * @return the translated list of strings
     * @throws YandexAPIException on error or if missed parameters
     */
    String[] translateArray(final String[] text, final Language language) throws YandexAPIException;

    /**
     * Translates a sentence.
     *
     * @param sentence the String to translate as a sentence
     * @param language the language code of translation direction
     * @return the translated sentence
     * @throws YandexAPIException on error or if missed parameters
     */
    String translateSentence(final String sentence, final Language language) throws YandexAPIException;
}
