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

import be.yildizgames.common.util.language.Language;
import be.yildizgames.common.util.language.LanguageValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Provide a translation contained in a properties for a given language.
 * Mutable class.
 *
 * @author Grégory Van den Borre
 *         specfield languages:Map of LanguageValue, Properties:Provide the property data for a given language.
 */
//@Invariant("language != null")
//@Invariant("!language.keys.contains(null)")
//@Invariant("!language.values.contains(null)")
public final class LanguageProvider {

    /**
     * Available languages.
     */
    private final Map<Language, Properties> languages = new HashMap<>();

    /**
     * Property holding the French translation.
     */
    private final Properties fr = new Properties();

    /**
     * Property holding the English translation.
     */
    private final Properties en = new Properties();

    /**
     * Build a new language provider, it will hold the property files for EN and FR.
     */
    public LanguageProvider() {
        super();
        this.languages.put(LanguageValue.EN, this.en);
        this.languages.put(LanguageValue.FR, this.fr);
    }

    public void registerLanguage(Language l) {
        this.languages.put(l, new Properties());
    }

    /**
     * Add a translation text.
     *
     * @param key     TranslationKey to call in the application.
     * @param french  French translation.
     * @param english English translation.
     * @throws NullPointerException     If any parameter is <code>null</code>.
     */
    public void add(final String key, final String french, final String english) {
        this.fr.put(key, french);
        this.en.put(key, english);
    }

    public void add(final TranslationKey key, final String french, final String english) {
        this.fr.put(key.translationKey, french);
        this.en.put(key.translationKey, english);
    }

    public void add(final String key, final Language language, final String value) {
        this.languages.get(language).put(key, value);
    }

    public void add(TranslatedValueProvider provider) {
        this.add(provider.getTranslatedValue());
    }

    /**
     * Add a translation text.
     *
     * @param value   Translation to add.
     * @throws NullPointerException     If value is <code>null</code>.
     */
    public void add(final TranslatedValue value) {
        this.add(
                value.getKey(),
                value.getFrench(),
                value.getEnglish());
    }

    /**
     * Add several translation texts.
     *
     * @param provider   Provide the translations.
     * @throws NullPointerException     If provider is <code>null</code>.
     */
    public void add(TranslatedValuesProvider provider) {
        for(TranslatedValueProvider t : provider) {
            this.add(t.getTranslatedValue());
        }
    }

    /**
     * Provide a properties containing the language translation.
     *
     * @param language LanguageValue to retrieve.
     * @return The properties matching the language.
     * @throws NullPointerException     If language is <code>null</code>.
     */
    public Properties get(final Language language) {
        assert language != null;
        return this.languages.get(language);
    }

}
