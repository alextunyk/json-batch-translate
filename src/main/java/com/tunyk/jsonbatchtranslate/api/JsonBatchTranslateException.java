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
public class JsonBatchTranslateException extends Exception {
    private static final long serialVersionUID = -4837705767875076746L;

    public JsonBatchTranslateException() {
        super();
    }

    public JsonBatchTranslateException(final String message) {
        super(message);
    }

    public JsonBatchTranslateException(final String message, final Exception e) {
        super(message, e);
    }

    public JsonBatchTranslateException(final Exception e) {
        super(e);
    }
}
