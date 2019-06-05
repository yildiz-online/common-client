/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.common.client.translation;

import be.yildizgames.common.util.StringUtil;
import be.yildizgames.common.util.language.Language;
import be.yildizgames.common.util.language.LanguageValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Manage different languages, use properties to get the translation.
 *
 * @author Grégory Van den Borre
 */
public final class Translation {

    private static final Translation INSTANCE = new Translation();

    /**
     * Association between a language and its values.
     */
    private final Map<Language, Properties> languages = new HashMap<>();

    /**
     * LanguageValue currently active.
     */
    private Language chosenLanguage = LanguageValue.EN;

    private Translation() {
        super();
    }

    public static Translation getInstance() {
        return INSTANCE;
    }

    /**
     * Associate a language and its property file.
     *
     * @param language LanguageValue to add.
     * @param provider Object containing the language properties.
     * @return This object.
     */
    public final Translation addLanguage(final Language language, final LanguageProvider provider) {
        Objects.requireNonNull(language);
        Objects.requireNonNull(provider);
        this.languages.put(language, provider.get(language));
        return this;
    }

    /**
     * Set the current language to use.
     *
     * @param language LanguageValue to use.
     * @return This object for method chaining.
     */
    public final Translation chooseLanguage(final Language language) {
        Objects.requireNonNull(language);
        if (!this.languages.containsKey(language)) {
            throw new IllegalArgumentException("Unexisting language:" + language);
        }
        this.chosenLanguage = language;
        return this;
    }

    /**
     * Retrieve a value in the properties.
     *
     * @param key TranslationKey property.
     * @return The translated value.
     * @throws IllegalArgumentException if the key does not exist.
     */
    private String get(final String key) {
        if (key.isEmpty()) {
            return "";
        }
        String s = this.languages.get(this.chosenLanguage).getProperty(key);
        Objects.requireNonNull(s);
        return s;
    }

    public final String translate(final TranslationKey.MultiKey keys) {
        StringBuilder sb = new StringBuilder();
        for (TranslationKey k : keys.keys) {
            sb.append(this.translate(k));
        }
        return sb.toString();
    }

    /**
     * Get the translated value.
     *
     * @param key TranslationKey of the value to translate.
     * @return The translated value associated to the key.
     */
    public final String translate(final TranslationKey key) {
        return StringUtil.fillVariable(this.get(key.key), key.args);
    }

    public final String translate(final TranslationKey key, String... args) {
        return StringUtil.fillVariable(this.get(key.key), args);
    }
}
