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

import java.util.Map;
import java.util.Properties;

/**
 * Provide a translation contained in a properties for a given language.
 * Mutable class.
 *
 * @author Grégory Van den Borre
 *         specfield languages:Map of Language, Properties:Provide the property data for a given language.
 */
//@Invariant("language != null")
//@Invariant("!language.keys.contains(null)")
//@Invariant("!language.values.contains(null)")
public final class LanguageProvider {

    /**
     * Available languages.
     */
    private final Map<Language, Properties> languages = Maps.newMap();

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
        this.languages.put(Language.EN, this.en);
        this.languages.put(Language.FR, this.fr);
    }

    /**
     * Add a translation text.
     *
     * @param key     Key to call in the application.
     * @param french  French translation.
     * @param english English translation.
     * @throws NullPointerException     If a value is <code>null</code>.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public void add(final String key, final String french, final String english) {
        this.fr.put(key, french);
        this.en.put(key, english);
    }

    /**
     * Provide a properties containing the language translation.
     *
     * @param language Language to retrieve.
     * @return The properties matching the language.
     * @throws NullPointerException     If parameter is <code>null</code>.
     * @throws IllegalArgumentException if language is <code>null</code>.
     */
    public Properties get(final Language language) {
        return this.languages.get(language);
    }

}
