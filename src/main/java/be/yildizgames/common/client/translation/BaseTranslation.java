/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *  Copyright (c) 2024 Grégory Van den Borre
 *  More infos available: https://engine.yildiz-games.be
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright
 *  notice and this permission notice shall be included in all copies or substantial portions of the  Software.
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package be.yildizgames.common.client.translation;

import be.yildizgames.common.configuration.LanguageConfiguration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Grégory Van den Borre
 */
public class BaseTranslation implements Translation {

    private final Map<Locale, ResourceBundle> bundles = new HashMap<>();

    private final LanguageConfiguration configuration;

    protected BaseTranslation(LanguageConfiguration configuration, Class<?> c) {
        super();
        this.configuration = Objects.requireNonNull(configuration);
        for(var l : configuration.getSupportedLocale()) {
            this.bundles.put(l, ResourceBundle.getBundle(c.getName(), l));
        }
    }

    @Override
    public final String get(String key) {
        try {
            return this.bundles.get(this.configuration.getLocale()).getString(key);
        } catch (Exception e) {
            System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, "Missing translation for " + this.configuration.getLocale() + " : " + key, e);
            return key;
        }
    }
}
