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

import java.util.*;

/**
 * Provide a translation contained in a properties for a given language.
 * Mutable class.
 *
 * @author Grégory Van den Borre
 *
 * specfield languages:Map of LanguageValue, Properties:Provide the property data for a given language.
 */
public class LanguageProvider {

    /**
     * Available languages.
     */
    private final Map<Locale, Properties> languages = new HashMap<>();

    /**
     * Build a new language provider, it will hold the property files for EN and FR.
     */
    public LanguageProvider() {
        super();
    }

    /**
     * Add a supported language.
     * @param l Language to support.
     */
    public final void registerLanguage(final Locale l) {
        Objects.requireNonNull(l);
        this.languages.put(l, new Properties());
    }

    public final void add(final String key, final Locale language, final String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(language);
        Objects.requireNonNull(value);
        this.get(language).put(key, value);
    }

    public final void add(TranslatedValueProvider provider) {
        this.add(provider.getTranslatedValue());
    }

    /**
     * Add a translation text.
     *
     * @param value   Translation to add.
     */
    public final void add(final TranslatedValue value) {
        this.add(value.getKey(), value.getLanguage(), value.getValue());
    }

    /**
     * Add several translation texts.
     *
     * @param provider   Provide the translations.
     */
    public final void add(TranslatedValuesProvider provider) {
        for(TranslatedValueProvider t : provider) {
            this.add(t.getTranslatedValue());
        }
    }

    /**
     * Provide a properties containing the language translation.
     *
     * @param language LanguageValue to retrieve.
     * @return The properties matching the language.
     */
    public final Properties get(final Locale language) {
        Objects.requireNonNull(language);
        Properties p = this.languages.get(language);
        Objects.requireNonNull(p);
        return p;
    }
}

