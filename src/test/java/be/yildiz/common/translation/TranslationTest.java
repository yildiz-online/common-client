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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public final class TranslationTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test(expected = NullPointerException.class)
    public void testAddLanguageNullArg() {
        Translation t = new Translation();
        t.addLanguage(null, new LanguageProvider());
    }

    @Test(expected = NullPointerException.class)
    public void testAddLanguageArgNull() {
        Translation t = new Translation();
        t.addLanguage(Language.EN, null);
    }

    @Test
    public void testAddLanguageArgArg() {
        Translation t = new Translation();
        t.addLanguage(Language.EN, new LanguageProvider());
    }

    @Test
    public void testChooseLanguageNotExisting() {
        Translation t = new Translation();
        t.addLanguage(Language.EN, new LanguageProvider());
        rule.expect(IllegalArgumentException.class);
        t.chooseLanguage(Language.FR);
        rule.expect(IllegalArgumentException.class);
        t.chooseLanguage(null);
    }

    @Test
    public void testGet() {
        Translation t = new Translation();
        LanguageProvider p = new LanguageProvider();
        p.add("test", "testfr", "testen");
        t.addLanguage(Language.EN, p);
        t.chooseLanguage(Language.EN);
        Assert.assertEquals("testen", t.translate(Key.get("test")));
    }

    @Test
    public void testGetNotExisting() {
        Translation t = new Translation();
        LanguageProvider p = new LanguageProvider();
        p.add("test", "testfr", "testen");
        t.addLanguage(Language.EN, p);
        t.chooseLanguage(Language.EN);
        rule.expect(IllegalArgumentException.class);
        t.translate(Key.get("test:)"));
    }
}
