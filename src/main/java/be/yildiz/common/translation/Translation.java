//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.common.translation;

import be.yildiz.common.collections.Maps;
import be.yildiz.common.translation.Key.MultiKey;
import be.yildiz.common.util.StringUtil;
import lombok.NonNull;

import java.util.Map;
import java.util.Properties;

/**
 * Manage different languages, use properties to get the translation.
 *
 * @author Grégory Van den Borre
 */
public final class Translation {

    /**
     * Association between a language and its values.
     */
    private final Map<Language, Properties> languages = Maps.newMap();

    /**
     * Language currently active.
     */
    @NonNull
    private Language chosenLanguage = Language.EN;

    /**
     * Associate a language and its property file.
     *
     * @param language Language to add.
     * @param provider Object containing the language properties.
     * @return This object.
     */
    public Translation addLanguage(final Language language, final LanguageProvider provider) {
        this.languages.put(language, provider.get(language));
        assert this.invariant();
        return this;
    }

    private boolean invariant() {
        if (this.languages.containsValue(null)) {
            throw new AssertionError("Null value not allowed in languages.");
        }
        return true;
    }

    /**
     * Set the current language to use.
     *
     * @param language Language to use.
     * @return This object for method chaining.
     */
    public Translation chooseLanguage(final Language language) {
        if (!this.languages.containsKey(language)) {
            throw new IllegalArgumentException("Unexisting language:" + language);
        }
        this.chosenLanguage = language;
        return this;
    }

    /**
     * Retrieve a value in the properties.
     *
     * @param key Key property.
     * @return The translated value.
     * @throws IllegalArgumentException if the key does not exist.
     */
    private String get(final String key) {
        if (key == "") {
            return "";
        }
        String s = this.languages.get(this.chosenLanguage).getProperty(key);
        if (s == null) {
            throw new IllegalArgumentException(key + " translation does not exists");
        }
        return s;
    }

    public String translate(final MultiKey keys) {
        StringBuilder sb = new StringBuilder();
        for (Key k : keys.keys) {
            sb.append(this.translate(k));
        }
        return sb.toString();
    }

    /**
     * Get the translated value.
     *
     * @param key Key of the value to translate.
     * @return The translated value associated to the key.
     */
    public String translate(final Key key) {
        return key.args.isPresent() ? StringUtil.fillVariable(this.get(key.translationKey), key.args.get()) : this.get(key.translationKey);
    }
}
