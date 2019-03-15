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

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.util.language.LanguageValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
public final class TranslationTest {

    @Test
    public void testAddLanguageNullArg() {
        assertThrows(ImplementationException.class, () -> Translation.getInstance().addLanguage(null, new LanguageProvider()));
    }

    @Test
    public void testAddLanguageArgNull() {
        assertThrows(ImplementationException.class, () -> Translation.getInstance().addLanguage(LanguageValue.EN, null));
    }

    @Test
    public void testAddLanguageArgArg() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
    }

    @Test
    public void testChooseLanguageNotExisting() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        assertThrows(IllegalArgumentException.class, () -> Translation.getInstance().chooseLanguage(LanguageValue.FR));
    }

    @Test
    public void testChooseLanguageNull() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        assertThrows(ImplementationException.class, () -> Translation.getInstance().chooseLanguage(null));
    }

    @Test
    public void testGet() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        p.add("test", LanguageValue.EN, "test-en");
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        Translation.getInstance().chooseLanguage(LanguageValue.EN);
        assertEquals("test-en", Translation.getInstance().translate(TranslationKey.get("test")));
    }

    @Test
    public void testGetNotExisting() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        p.add("test", LanguageValue.EN, "test-en");
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        Translation.getInstance().chooseLanguage(LanguageValue.EN);
        assertThrows(ImplementationException.class, () -> Translation.getInstance().translate(TranslationKey.get("test:)")));
    }

    @Test
    public void testGetEmpty() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        Translation.getInstance().chooseLanguage(LanguageValue.EN);
        Assertions.assertEquals("", Translation.getInstance().translate(TranslationKey.get("")));
    }

    @Test
    public void translateWithArgs() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        p.add("test", LanguageValue.EN, "value${0}");
        Translation.getInstance().chooseLanguage(LanguageValue.EN);
        Assertions.assertEquals("value-replace", Translation.getInstance().translate(TranslationKey.get("test"), "-replace"));
    }

    @Test
    public void translateMultiKeys() {
        LanguageProvider p = new LanguageProvider();
        p.registerLanguage(LanguageValue.EN);
        Translation.getInstance().addLanguage(LanguageValue.EN, p);
        p.add("test1", LanguageValue.EN, "value1-");
        p.add("test2", LanguageValue.EN, "value2");
        Translation.getInstance().chooseLanguage(LanguageValue.EN);
        TranslationKey.MultiKey keys = TranslationKey.get(TranslationKey.get("test1"), TranslationKey.get("test2"));
        Assertions.assertEquals("value1-value2", Translation.getInstance().translate(keys));
    }
}
