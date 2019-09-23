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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
final class TranslationTest {

    @Test
    void testAddLanguageNullArg() {
        assertThrows(NullPointerException.class, () -> Translation.getInstance().addLanguage(null, new LanguageProvider()));
    }

    @Test
    void testAddLanguageArgNull() {
        assertThrows(NullPointerException.class, () -> Translation.getInstance().addLanguage(Locale.ENGLISH, null));
    }

    @Test
    void testAddLanguageArgArg() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
    }

    @Disabled
    @Test
    void testChooseLanguageNotExisting() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        assertThrows(IllegalArgumentException.class, () -> Translation.getInstance().chooseLanguage(Locale.FRENCH));
    }

    @Test
    void testChooseLanguageNull() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        assertThrows(NullPointerException.class, () -> Translation.getInstance().chooseLanguage(null));
    }

    @Test
    void testGet() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        p.add("test", Locale.ENGLISH, "test-en");
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        Translation.getInstance().chooseLanguage(Locale.ENGLISH);
        assertEquals("test-en", Translation.getInstance().translate(TranslationKey.get("test")));
    }

    @Test
    void testGetNotExisting() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        p.add("test", Locale.ENGLISH, "test-en");
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        Translation.getInstance().chooseLanguage(Locale.ENGLISH);
        assertThrows(NullPointerException.class, () -> Translation.getInstance().translate(TranslationKey.get("test:)")));
    }

    @Test
    void testGetEmpty() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        Translation.getInstance().chooseLanguage(Locale.ENGLISH);
        Assertions.assertEquals("", Translation.getInstance().translate(TranslationKey.get("")));
    }

    @Test
    void translateWithArgs() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        p.add("test", Locale.ENGLISH, "value{0}");
        Translation.getInstance().chooseLanguage(Locale.ENGLISH);
        Assertions.assertEquals("value-replace", Translation.getInstance().translate(TranslationKey.get("test"), "-replace"));
    }

    @Test
    void translateMultiKeys() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(Locale.ENGLISH);
        Translation.getInstance().addLanguage(Locale.ENGLISH, p);
        p.add("test1", Locale.ENGLISH, "value1-");
        p.add("test2", Locale.ENGLISH, "value2");
        Translation.getInstance().chooseLanguage(Locale.ENGLISH);
        TranslationKey.MultiKey keys = TranslationKey.get(TranslationKey.get("test1"), TranslationKey.get("test2"));
        Assertions.assertEquals("value1-value2", Translation.getInstance().translate(keys));
    }
}
