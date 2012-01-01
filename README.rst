====================
JSON Batch Translate
====================
:Version: 1.0
:Keywords: Google Translate, Microsoft Translator, Yandex Translate, Java, JSON, bulk translation
:Copyright: Alex Tunyk <alex at tunyk.com>
:License: Apache License version 2.0

\
JSON Batch Translate library allows you to perform multiple translations within a single call through using JSON object. Exactly the same JSON object will be returned back but with translated values (keys are kept original, only the one request per API invocation is sent for translation). Translation can be based on the one of following services:
    - Google Translate
    - Microsoft Translator
    - Yandex Translate


Example Usage:
""""""""""""""
Let's say you have this JSON object:
::
    {
        "key": "text to be translated",
        "complex": {
            "obj": "some other text to be translated"
        }
    }


... and you need it to be translated in Spanish like this:
::
    {
        "key": "texto a traducir",
        "complex": {
            "obj": "otro texto a traducir"
        }
    }
... and you don't want multiple requests to be sent, but just one request as well as to have the possibility to use
translation service provided by Google, Microsoft or Yandex.


So here is a code that will do this:
::
    // define JSON string you'd like to translate
    String in = "{\"key\":\"text to be translated\",\"complex\":{\"obj\":\"some other text to be translated\"}}";

    // convert string to JSONObject
    JSONObject jsonObject = new JSONObject(in);

    // translate it from English to Spanish
    JsonBatchTranslate.DEFAULT.execute(API.BingAPI,
                                       /*INSERT-YOUR-KEY*/,
                                       jsonObject,
                                       Language.ENGLISH,
                                       Language.SPANISH,
                                       /*INSERT-REFERRER-TO-YOUR-WEBSITE*/);

    // make sure you have it translated
    out.getString("key"); // return translated string: "texto a traducir"
    out.getJSONObject("complex").getString("obj"); // return translated string: "otro texto a traducir"


And if you need the JSONObject with translated particular properties only:
::
    // translate the value of property 'key' only
    JsonBatchTranslate.DEFAULT.execute(API.BingAPI,
                                       /*INSERT-YOUR-KEY*/,
                                       jsonObject,
                                       Language.ENGLISH,
                                       Language.SPANISH,
                                       Arrays.asList("key")
                                       /*INSERT-REFERRER-TO-YOUR-WEBSITE*/);

    // make sure that only the individual properties of JSONObject are translated
    out.getString("key"); // return translated string: "texto a traducir"
    out.getJSONObject("complex").getString("obj"); // return source string: "some other text to be translated"



You have to get your API key from corresponding service provider to be able to use translation API:
    - `Google Translate API Key <http://code.google.com/apis/language/translate/v2/getting_started.html>`_
    - `Bing Developer API Key <http://www.bing.com/developers/createapp.aspx>`_
    - Yandex.Translate service is not API service. Currently, the Yandex.Translate service is available as a public beta version and provided to the user for personal, non commercial use. More details are `here <http://legal.yandex.ru/translate_termsofuse/>`_ and `here <http://legal.yandex.ru/rules/>`_


JSON Batch Translate is available as Maven artifact and distributed via the `Maven Central repository <http://search.maven.org/#browse%7C-94393276>`_:
::
    <dependency>
        <groupId>com.tunyk.jsonbatchtranslate</groupId>
        <artifactId>json-batch-translate</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>


Source
""""""
The source code is available on GitHub at https://github.com/TUNYK/json-batch-translate
::
    git clone https://github.com/TUNYK/json-batch-translate.git
NOTE: Since up-to-date version of google-api-translate-java is not in Maven Central, you need to fetch it out from GitHub and install it to your local repository in order to be able to compile json-batch-translate.
More details about google-api-translate-java at `google-api-translate-java <https://github.com/richmidwinter/google-api-translate-java>`_


Issues tracking
"""""""""""""""
Issues tracking is available on GitHub at https://github.com/TUNYK/json-batch-translate/issues.
Bug reports, feature requests, and general inquiries welcome.
