/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.common.client.translation;

import be.yildizgames.common.util.language.LanguageValue;

/**
 * Example language provider supporting french and english languages, to have additional support, simply create other implementations.
 *
 * @author Grégory Van den Borre.
 */
public class FrenchEnglishLanguageProvider extends LanguageProvider {

    public FrenchEnglishLanguageProvider() {
        super();
        this.registerLanguage(LanguageValue.EN);
        this.registerLanguage(LanguageValue.FR);
        Translation.getInstance().addLanguage(LanguageValue.EN, this);
        Translation.getInstance().addLanguage(LanguageValue.FR, this);
    }

    public void add(String key, String french, String english) {
        this.add(key, LanguageValue.FR, french);
        this.add(key, LanguageValue.EN, english);
    }

    public final String getFrench(String key) {
        return this.get(LanguageValue.FR).getProperty(key);
    }

    public final String getEnglish(String key) {
        return this.get(LanguageValue.EN).getProperty(key);
    }

}
