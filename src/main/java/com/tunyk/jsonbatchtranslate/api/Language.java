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

/**
 * @author Alex Tunyk <alex at tunyk.com>
 */
public enum Language {
    AUTO_DETECT(""),
    AFRIKAANS("af"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    AZERBAIJANI("az"),
    BASQUE("eu"),
    BELARUSIAN("be"),
    BENGALI("bn"),
    BIHARI("bh"),
    BULGARIAN("bg"),
    BURMESE("my"),
    CATALAN("ca"),
    CHEROKEE("chr"),
    CHINESE("zh"),
    CHINESE_SIMPLIFIED("zh-CN|zh-CHS"), // "zh-CHS" is Bing API;
    CHINESE_TRADITIONAL("zh-TW|zh-CHT"), // "zh-CHT" is Bing API;
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DHIVEHI("dv"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FILIPINO("tl"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    GEORGIAN("ka"),
    GERMAN("de"),
    GREEK("el"),
    GUARANI("gn"),
    GUJARATI("gu"),
    HATIAN_CREOLE("ht"), // Bing API
    HEBREW("iw|he"), // "he" is Bing API
    HINDI("hi"),
    HUNGARIAN("hu"),
    ICELANDIC("is"),
    INDONESIAN("id"),
    INUKTITUT("iu"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KANNADA("kn"),
    KAZAKH("kk"),
    KHMER("km"),
    KOREAN("ko"),
    KURDISH("ku"),
    KYRGYZ("ky"),
    LAOTHIAN("lo"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MALTESE("mt"),
    MARATHI("mr"),
    MONGOLIAN("mn"),
    NEPALI("ne"),
    NORWEGIAN("no"),
    ORIYA("or"),
    PASHTO("ps"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PUNJABI("pa"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SANSKRIT("sa"),
    SERBIAN("sr"),
    SINDHI("sd"),
    SINHALESE("si"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAJIK("tg"),
    TAMIL("ta"),
    TAGALOG("tl"),
    TELUGU("te"),
    THAI("th"),
    TIBETAN("bo"),
    TURKISH("tr"),
    UKRANIAN("uk"),
    URDU("ur"),
    UZBEK("uz"),
    UIGHUR("ug"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    YIDDISH("yi");

    private final String language;

    private Language(final String pLanguage) {
        language = pLanguage;
    }

    /**
     * Returns String representation of the language according to the given API.
     *
     * @param api API
     * @return String representation of the language.
     * @throws JsonBatchTranslateException if the language is not supported by given API
     */
    public String toString(final JsonBatchTranslate.API api) throws JsonBatchTranslateException {
        switch (api) {
            case GoogleTranslateAPI:
                for (final com.google.api.translate.Language gl : com.google.api.translate.Language.values()) {
                    final String gLang = gl.toString();

                    for (final String jbtLang : language.split("\\|")) {
                        if (jbtLang.equalsIgnoreCase(gLang)) {
                            return gLang;
                        }
                    }
                }
                throw new JsonBatchTranslateException(String.format("Language %s is not supported by %s", name(), api.name()));

            case MicrosoftTranslatorAPI:
                for (final com.memetix.mst.language.Language mtl : com.memetix.mst.language.Language.values()) {
                    final String mtLang = mtl.toString();

                    for (final String jbtLang : language.split("\\|")) {
                        if (jbtLang.equalsIgnoreCase(mtLang)) {
                            return mtLang;
                        }
                    }
                }
                throw new JsonBatchTranslateException(String.format("Language %s is not supported by %s", name(), api.name()));

            case YandexAPI:
                for (final String yLang : ru.yandex.translate.api.Language.list()) {
                    for (final String jbtLang : language.split("\\|")) {
                        if (jbtLang.equalsIgnoreCase(yLang)) {
                            return yLang;
                        }
                    }
                }
                throw new JsonBatchTranslateException(String.format("Language %s is not supported by %s", name(), api.name()));

            default:
                throw new JsonBatchTranslateException(String.format("API is not supported, %s", api.name()));
        }
    }

    @Override
    public String toString() {
        return language;
    }
}
